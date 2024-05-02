package ru.gb.group5984.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.group5984.model.basket.Basket;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.service.api.CharacterApiService;
import ru.gb.group5984.service.db.CharacterDbService;



/**
 * REST Контроллер склада магазина
 * Проверка свагером http://localhost:8082/swagger-ui/index.html
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/storage/rest")
@Log
public class StorageRestController {
    private final CharacterDbService serviceDb;
    private final CharacterApiService serviceApi;

    /**
     * Переадресация на первую страницу списка товаров
     * @return адрес первой страницы списка товаров
     */
    @GetMapping("")
    public String redirectToFirstPage() {
        return "redirect:/storage/rest/cards/page/1";
    }

    /**
     * Получить список товаров, выставленных на продажу
     * @return Список товаров и статус ответа
     */
    @GetMapping("/cards/page/{page}")
    public ResponseEntity<Cards> getAllCards(@PathVariable("page") Integer page) {
        return new ResponseEntity<>(serviceDb.getAllCardsStorageFromSale(page), HttpStatus.OK);
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
