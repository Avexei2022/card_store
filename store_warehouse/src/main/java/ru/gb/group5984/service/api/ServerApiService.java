package ru.gb.group5984.service.api;

import ru.gb.group5984.model.basket.Basket;
import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.model.messeges.Message;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.model.users.User;


/**
 * Интерфейс сервиса склада магазина
 */
public interface ServerApiService {


    /**
     * Получить полный список товаров из базы данных товаров на складе
     * @return список товара
     */
    Characters getPageFromStorage(String page);

    /**
     * Удалить карточку героя из базы данных
     * @param id Id героя
     */
    Message deleteFromStorageById(Integer id);

    /**
     * Выствить товар на продажу
     * @param id - id товара
     */
    void saveOneCardToSaleById(Integer id);


    /**
     * Получить все товары постранично, выставленные на продажу.
     * @param page - запрашиваемая пользователем страница
     * @return список товаров в продаже
     */
    Cards getPageCardsStorageFromSale(Integer page);

    /**
     * Удалить товар из списка продаж / убрать с полки.
     * @param id - id товара
     */
    void deleteCardFromSaleById(Integer id);

    /**
     * Переместить единицу товара с полки в корзину покупателя
     * @param id - id товара
     */
    void moveCardToBasket(Long id);

    /**
     * Возврат единицы товара из корзины покупателя на полку магазина
     * @param id id - товара в корзине
     */
    void returnCardFromBasketToSale(Long id);

    /**
     * Получить все товары постранично, зарезервированные в корзине.
     * @param page - запрашиваемая пользователем страница
     * @return список товаров в корзине
     */
    Basket getAllFromBasket(Integer page);

    User getUserByUserName(String name);
}
