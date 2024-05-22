package ru.gb.group5984.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.group5984.aspect.TrackUserAction;
import ru.gb.group5984.model.basket.Basket;
import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.model.exceptions.ExcessAmountException;
import ru.gb.group5984.model.messeges.Message;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.model.users.User;
import ru.gb.group5984.service.api.CharacterApiService;
import ru.gb.group5984.service.db.ServerDbService;
import ru.gb.group5984.service.db.UserDbService;

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
    private final ServerDbService serverDbService;
    private final UserDbService userDbService;
    private final CharacterApiService characterApiService;

    private final Counter addCardToStorageCounter = Metrics.counter("add_card_to_storage_ count");
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
     * @param id номер товара.
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
     * @param id id товара.
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
     * @param id номер товара.
     * @return статус ответа.
     */
    @GetMapping("/storage/add_to_sale/{id}")
    public ResponseEntity<Void> addToSale(@PathVariable("id") Integer id) {
        serverDbService.saveOneCardById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Удалить товар из списка продаж - убрать с витрины.
     * @param id номер товара.
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
     * @param id -id товара.
     * @return статус ответа.
     */
    @GetMapping("/basket/add_to_basket/{id}")
    public ResponseEntity<Message> addToBasket(@PathVariable("id") Long id) {
        Message message = new Message();
        try {
            serverDbService.moveCardToBasket(id);
            addCardToBasketCounter.increment();
            message.setMessage("OK");
        } catch (NoSuchElementException | ExcessAmountException e) {
            message.setMessage(e.getMessage());
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * Получить список товаров в корзине.
     * @param page номер запрашиваемой пользователем страницы из списка товаров.
     * @return список товаров в корзине и статус ответа.
     */
    @GetMapping("/basket/page/{page}")
    public ResponseEntity<Basket> getAllFromBasket(@PathVariable("page") Integer page) {
        return new ResponseEntity<>(serverDbService.getPageFromBasket(page), HttpStatus.OK);
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
     * @return статус ответа.
     */
    @TrackUserAction
    @GetMapping("/basket/pay")
    public ResponseEntity<Message> basketPay() {
        return new ResponseEntity<>(characterApiService.basketPay(), HttpStatus.OK);
    }

    /**
     * Поиск пользователя по имени.
     * @param name имя пользователя.
     * @return пользователь.
     */
    @GetMapping("/user/{name}")
    public ResponseEntity<User> findUserByName(@PathVariable("name") String name) {
        User user = userDbService.findUserByUsername(name);
        log.info(user.toString());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
