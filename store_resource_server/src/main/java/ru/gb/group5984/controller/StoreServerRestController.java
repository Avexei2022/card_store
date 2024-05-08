package ru.gb.group5984.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.group5984.configuration.BasicConfig;
import ru.gb.group5984.model.basket.Basket;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.model.storage.CardsStorage;
import ru.gb.group5984.service.api.CharacterApiService;
import ru.gb.group5984.service.db.CharacterDbService;

import java.util.List;


/**
 * REST Контроллер склада магазина
 * Проверка свагером http://localhost:8084/swagger-ui/index.html
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/server")
@Log
public class StoreServerRestController {
    private final CharacterDbService serviceDb;
    private final CharacterApiService serviceApi;
    private final BasicConfig basicConfig;


    /**
     * Запрос списка товаров с рессурса поставщика - Rick and Morty.
     * @param page номер страницы в списке товаров.
     * @return Страница списка товаров с рессурса поставщика - Rick and Morty.
     */
    @GetMapping("/characters/page/{page}")
    public ResponseEntity<Characters> getCharactersPage (@PathVariable("page") String page) {
        String url = basicConfig.getCHARACTER_API() + "/?page=" + page;
        Characters allCharacters = serviceApi.getAllCharacters(url);
        return new ResponseEntity<>(allCharacters, HttpStatus.OK);
    }

    //TODO Удалить
    /**
     * Запрос всех товаров на складе, но невыставленных на продажу.
     * @return список товаров на складе и статус ответа.
     */
    @GetMapping("/storage/all")
    public ResponseEntity<List<CharacterResult>> getAllFromStorage() {
        return new ResponseEntity<>(serviceDb.getAllFromStorage(), HttpStatus.OK);
    }

    /**
     * Добавить единицу товара на склад - закупить у поставщика.
     * @param id номер товара.
     * @return статус ответа.
     */
    @GetMapping("/characters/add_to_storage/{id}")
    public ResponseEntity<Void> addToStorage(@PathVariable("id") Integer id) {
        String url = basicConfig.getCHARACTER_API() + "/" + id;
        serviceApi.saveOneCharacterById(url);
        return ResponseEntity.ok().build();
    }

    /**
     * Удалить единицу товара со склада.
     * @param id id товара.
     * @return статус ответа.
     */
    @GetMapping("/characters/delete_from_storage/{id}")
    public ResponseEntity<Void> deleteFromStorage(@PathVariable("id") Integer id) {
        serviceDb.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Получить страницу из списка товаров, хранящихся на складе.
     * @param page номер страницы.
     * @return страница списка товаров на складе и статус ответа.
     */
    @GetMapping("/storage/page/{page}")
    public ResponseEntity<List<CharacterResult>> getPageCardsInStorage(@PathVariable("page") String page) {
        //TODO оптимизировать - пока тестовый вариант
        return new ResponseEntity<>(serviceDb.getAllFromStorage(), HttpStatus.OK);
    }

    //TODO Удалить
    /**
     * Получить все товары выставленные на продажу.
     * @return список товаров и статус ответа.
     */
    @GetMapping("/sale/all")
    public ResponseEntity<List<CharacterResult>> gelAllFromSale() {
        return new ResponseEntity<>(serviceDb.getAllCardFromSale(), HttpStatus.OK);
    }


    /**
     * Переместить единицу товара со склада на полку продаж.
     * @param id номер товара.
     * @return статус ответа.
     */
    @GetMapping("/storage/add_to_sale/{id}")
    public ResponseEntity<Void> addToSale(@PathVariable("id") Integer id) {
        serviceDb.saveOneCardById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Удалить товар из списка продаж - убрать с витрины.
     * @param id номер товара.
     * @return статус ответа.
     */
    @GetMapping("/storage/delete_from_sale/{id}")
    public ResponseEntity<Void> deleteFromSale(@PathVariable("id") Integer id) {
        serviceDb.deleteCardFromSaleById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Получить страницу из списка товаров, выставленных на продажу.
     * @param page номер страницы
     * @return страница списка товаров.
     */
    @GetMapping("/sale/page/{page}")
    public ResponseEntity<Cards> getPageCardsInSale(@PathVariable("page") Integer page) {
        Cards cards = serviceDb.getAllCardsStorageFromSale(page);
        return new ResponseEntity<>(cards, HttpStatus.OK);

    }

    /**
     * Обновление информации о товаре, выставленного на продажу.
     * @param cardsStorage карточка товара
     * @return статус ответа.
     */
    @PostMapping("/sale/update")
    public ResponseEntity<Void> updateSaleCard(CardsStorage cardsStorage) {
        serviceDb.saveCardStorage(cardsStorage);
        return ResponseEntity.ok().build();
    }

    /**
     * Добавить товар в корзину
     * @param id -id товара
     * @return статус ответа
     */
    @GetMapping("/basket/add_to_basket/{id}")
    public ResponseEntity<Void> addToBasket(@PathVariable("id") Long id) {
        serviceDb.moveCardToBasket(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Получить список товаров в корзине
     * @param page номер запрашиваемой пользователем страницы из списка товаров
     * @return список товаров в корзине и статус ответа
     */
    @GetMapping("/basket/page/{page}")
    public ResponseEntity<Basket> getAllFromBasket(@PathVariable("page") Integer page) {
        return new ResponseEntity<>(serviceDb.getAllFromBasket(page), HttpStatus.OK);
    }

    /**
     * Возврат товара из корзины на полку
     * @param id - id товара
     * @return статус ответа
     */
    @GetMapping("/basket/delete_from_basket/{id}")
    public ResponseEntity<Void> deleteFromBasket(@PathVariable("id") Long id) {
        serviceDb.returnCardFromBasketToSale(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Оплата товара из корзины покупателя.
     * @return
     */
    @GetMapping("/basket/pay")
    public ResponseEntity<Void> basketPay() {
        serviceApi.basketPay();
        return ResponseEntity.ok().build();
    }
}
