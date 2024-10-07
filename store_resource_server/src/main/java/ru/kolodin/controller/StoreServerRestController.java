package ru.kolodin.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kolodin.aspect.TrackUserAction;
import ru.kolodin.model.basket.Basket;
import ru.kolodin.model.characters.Characters;
import ru.kolodin.model.exceptions.ExcessAmountException;
import ru.kolodin.model.messeges.Message;
import ru.kolodin.model.storage.Cards;
import ru.kolodin.model.users.Buyer;
import ru.kolodin.model.users.StorageUser;
import ru.kolodin.service.api.CharacterApiService;
import ru.kolodin.service.db.ServerDbService;
import ru.kolodin.service.db.UserDbService;

import java.util.NoSuchElementException;


/**
 * REST Контроллер сервера ресурсов магазина.
 * Проверка свагером http://localhost:8084/swagger-ui/index.html
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/store_server")
@Log
public class StoreServerRestController {

    /**
     *
     *  Сервис склада магазина.
     *
     */
    private final ServerDbService serverDbService;

    /**
     * Сервис пользователей.
     */
    private final UserDbService userDbService;

    /**
     * Сервис взаимодействия с API ресурса Rick and Morty
     */
    private final CharacterApiService characterApiService;

    /**
     * Метрика: счетчик товаров добавленных на склад.
     */
    private final Counter addCardToStorageCounter = Metrics.counter("add_card_to_storage_ count");

    /**
     * Метрика: счетчик товаров добавленных в корзины покупателей.
     */
    private final Counter addCardToBasketCounter = Metrics.counter("add_card_to_basket_count");

    /**
     * Запрос списка товаров с рессурса поставщика - Rick and Morty.
     * @param page номер страницы в списке товаров.
     * @return Страница списка товаров с рессурса поставщика - Rick and Morty.
     */
    @GetMapping("/characters/page/{page}")
    public ResponseEntity<Characters> getCharactersPage (@PathVariable("page") String page) {
        Characters allCharacters = characterApiService.getPageCharacters(page);
        return new ResponseEntity<>(allCharacters, HttpStatus.OK);
    }

    /**
     * Добавить единицу товара на склад - закупить у поставщика.
     * @param id уникальный номер товара.
     * @return статус ответа.
     */
    @GetMapping("/characters/add_to_storage/{id}")
    public ResponseEntity<Void> addToStorage(@PathVariable("id") Integer id) {
        characterApiService.saveOneCharacterById(id);
        addCardToStorageCounter.increment();
        return ResponseEntity.ok().build();
    }

    /**
     * Удалить единицу товара со склада.
     * @param id уникальный номер товара.
     * @return статус ответа.
     */
    @TrackUserAction
    @GetMapping("/characters/delete_from_storage/{id}")
    public ResponseEntity<Message> deleteFromStorageById(@PathVariable("id") Integer id) {
        Message message = serverDbService.deleteFromStorageById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Получить страницу из списка товаров, хранящихся на складе.
     * @param page номер страницы.
     * @return страница списка товаров на складе и статус ответа.
     */
    @GetMapping("/storage/page/{page}")
    public ResponseEntity<Characters> getPageCharactersFromStorage(@PathVariable("page") String page) {
        Characters characters = serverDbService.getPageCharactersFromStorage(Integer.valueOf(page));
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }

    /**
     * Переместить единицу товара со склада на полку продаж.
     * @param id уникальный номер товара.
     * @return статус ответа.
     */
    @GetMapping("/storage/add_to_sale/{id}")
    public ResponseEntity<Void> addToSale(@PathVariable("id") Integer id) {
        serverDbService.saveOneCardById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Удалить товар из списка продаж - убрать с витрины.
     * @param id уникальный номер товара.
     * @return статус ответа.
     */
    @TrackUserAction
    @GetMapping("/storage/delete_from_sale/{id}")
    public ResponseEntity<Void> deleteFromSale(@PathVariable("id") Long id) {
        serverDbService.deleteCardFromSaleById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Получить страницу из списка товаров, выставленных на продажу.
     * @param page номер страницы.
     * @return страница списка товаров.
     */
    @GetMapping("/sale/page/{page}")
    public ResponseEntity<Cards> getPageCardsInSale(@PathVariable("page") Integer page) {
        Cards cards = serverDbService.getPageCardsStorageFromSale(page);
        return new ResponseEntity<>(cards, HttpStatus.OK);

    }

    /**
     * Добавить товар в корзину.
     * @param cardId - уникальный номер товара в продаже
     * @param userName - имя покупателя
     * @return статус ответа.
     */
    @GetMapping("/basket/add_to_basket/{card_id}/{user_name}")
    public ResponseEntity<Message> addToBasket(@PathVariable("card_id") Long cardId
            , @PathVariable("user_name") String userName) {
        Message message = new Message();
        try {
            serverDbService.moveCardToBasket(cardId, userName);
            addCardToBasketCounter.increment();
            message.setMessage("OK");
        } catch (NoSuchElementException | ExcessAmountException e) {
            message.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Получить список товаров в корзине покупателя.
     * @param page номер запрашиваемой пользователем страницы из списка товаров.
     * @param userName имя/логин покупателя.
     * @return список товаров в корзине и статус ответа.
     */
    @GetMapping("/basket/page/{page}/{user_name}")
    public ResponseEntity<Basket> getAllFromBasket(@PathVariable("page") Integer page
                                                    ,@PathVariable("user_name") String userName) {
        return new ResponseEntity<>(serverDbService.getPageFromBasket(page, userName), HttpStatus.OK);
    }

    /**
     * Возврат товара из корзины на полку.
     * @param id - id товара.
     * @return статус ответа.
     */
    @GetMapping("/basket/return_to_sale/{id}")
    public ResponseEntity<Void> deleteFromBasket(@PathVariable("id") Long id) {
        serverDbService.returnCardFromBasketToSale(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Оплата товара из корзины покупателя.
     * @param userName имя/логин покупателя.
     * @return статус ответа.
     */
    @TrackUserAction
    @GetMapping("/basket/pay/{user_name}")
    public ResponseEntity<Message> basketPay(@PathVariable("user_name") String userName) {
        return new ResponseEntity<>(characterApiService.basketPay(userName), HttpStatus.OK);
    }

    /**
     * Поиск пользователя веб-ресурса по имени.
     * @param name имя пользователя.
     * @return пользователь.
     */
    @GetMapping("/buyer/{name}")
    public ResponseEntity<Buyer> findUserByName(@PathVariable("name") String name) {
        Buyer buyer = userDbService.findBuyerByUsername(name);
        log.info(buyer.toString());
        return new ResponseEntity<>(buyer, HttpStatus.OK);
    }

    /**
     * Поиск пользователя веб-сервиса склада магазина по имени.
     * @param name имя пользователя.
     * @return пользователь.
     */
    @GetMapping("/storage_user/{name}")
    public ResponseEntity<StorageUser> findStorageUserByName(@PathVariable("name") String name) {
        StorageUser storageUser = userDbService.findStorageUserByUsername(name);
        log.info(storageUser.toString());
        return new ResponseEntity<>(storageUser, HttpStatus.OK);
    }

    /**
     * Зарегистрировать пользователя.
     * @param id уникальный номер пользователя.
     * @return статус ответа.
     */
    @GetMapping("/characters/register/{id}")
    public ResponseEntity<Message> registerNewUser(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(characterApiService.registerNewUser(id), HttpStatus.OK);
    }

}
