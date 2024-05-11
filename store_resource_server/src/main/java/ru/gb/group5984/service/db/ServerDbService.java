package ru.gb.group5984.service.db;

import ru.gb.group5984.model.basket.Basket;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.characters.Characters;
import ru.gb.group5984.model.messeges.Message;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.model.storage.CardsStorage;

/**
 * Интерфейс сервиса склада магазина
 */
public interface ServerDbService {

    /**
     * Сохранение единицы товара, закупленного у поставщика,
     * в базе данных товаров на складе.
     * @param characterResult Единица товара
     */
    void saveOneCharacter(CharacterResult characterResult);


    /**
     * Получить все товары постранично, хранящиеся на складе.
     * @param page - номер страницы.
     * @return список товаров на складе.
     */
    Characters getPageCharactersFromStorage(Integer page);

    /**
     * Удалить единицу товара из базы данных товаров на складе
     * @param id Id Товара
     */
    Message deleteFromStorageById(Integer id);

    /**
     * Выствить товар на продажу.
     * @param id - id товара.
     */
    void saveOneCardById(Integer id);


    /**
     * Получить все товары постранично, выставленные на продажу.
     * @param page - запрашиваемая пользователем страница.
     * @return список товаров в продаже.
     */
    Cards getPageCardsStorageFromSale(Integer page);

    /**
     * Удалить товар из списка продаж / убрать с полки.
     * @param id - id товара.
     */
    void deleteCardFromSaleById(Long id);

    /**
     * Закупка единицы товара у поставщика и сохранение на складе магазина.
     * @param cardsStorage единица товара.
     */
    void saveCardStorage(CardsStorage cardsStorage);

    /**
     * Переместить единицу товара с полки в корзину покупателя.
     * @param id - id товара.
     */
    void moveCardToBasket(Long id);

    /**
     * Возврат единицы товара из корзины покупателя на полку магазина.
     * @param id id - товара в корзине.
     */
    void returnCardFromBasketToSale(Long id);

    /**
     * Получить все товары постранично, зарезервированные в корзине.
     * @param page - запрашиваемая пользователем страница.
     * @return список товаров в корзине.
     */
    Basket getAllFromBasket(Integer page);

    /**
     * Получить общую сумму товаров в корзине.
     * @return сумма товаров в корзине.
     */
    Double getTotalPriceFromBasket();

    /**
     * Удалить все товары из корзины.
     */
    void deleteAllFromBasket();
}
