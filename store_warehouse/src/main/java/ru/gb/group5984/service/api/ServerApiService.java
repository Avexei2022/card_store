package ru.gb.group5984.service.api;

import org.springframework.security.core.userdetails.UserDetails;
import ru.gb.group5984.model.basket.Basket;
import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.model.messeges.Message;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.model.users.User;


/**
 * Интерфейс сервиса взаимодействия с API сервиса ресурсов магазина.
 */
public interface ServerApiService {


    /**
     * Получить страницу из списка товаров на складе.
     * @param page номер страницы.
     * @return список товара.
     */
    Characters getPageFromStorage(String page);

    /**
     * Удалить товар со склада.
     * @param id уникальный номер товара.
     * @return сообщение о результате удаления товара.
     */
    Message deleteFromStorageById(Integer id);

    /**
     * Выствить товар на продажу.
     * @param id уникальный номер товара.
     */
    void saveOneCardToSaleById(Integer id);


    /**
     * Получить все товары постранично, выставленные на продажу.
     * @param page - запрашиваемая пользователем страница.
     * @return список товаров в продаже.
     */
    Cards getPageCardsStorageFromSale(Integer page);

    /**
     * Удалить товар из списка продаж / убрать с полки.
     * @param id - уникальный номер товара.
     */
    void deleteCardFromSaleById(Integer id);

    /**
     * Переместить единицу товара с полки в корзину покупателя
     * @param id - уникальный номер товара.
     */
    void moveCardToBasket(Long id);

    /**
     * Возврат единицы товара из корзины покупателя на полку магазина
     * @param id уникальный номер товара.
     */
    void returnCardFromBasketToSale(Long id);

    /**
     * Получить все товары постранично, зарезервированные в корзине.
     * @param page - запрашиваемая пользователем страница.
     * @return список товаров в корзине.
     */
    Basket getAllFromBasket(Integer page);

}
