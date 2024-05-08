package ru.gb.group5984.service.db;

import ru.gb.group5984.model.basket.Basket;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.storage.Cards;
import ru.gb.group5984.model.storage.CardsStorage;

import java.util.List;

/**
 * Интерфейс сервиса склада магазина
 */
public interface CharacterDbService {

    /**
     * Сохранение единицы товара, закупленного у поставщика,
     * в базе данных товаров на складе.
     * @param characterResult Единица товара
     */
    void saveOneCharacter(CharacterResult characterResult);

    /**
     * Получить полный список товаров из базы данных товаров на складе
     * @return список товара
     */
    List<CharacterResult> getAllFromStorage();

    /**
     * Удалить карточку героя из базы данных
     * @param id Id героя
     */
    void deleteById(Integer id);

    /**
     * Выствить товар на продажу
     * @param id - id товара
     */
    void saveOneCardById(Integer id);

    /**
     * Получить список товара, выставленного на продажу
     * @return список товара.
     */
    List<CharacterResult> getAllCardFromSale();

    /**
     * Получить все товары постранично, выставленные на продажу.
     * @param page - запрашиваемая пользователем страница
     * @return список товаров в продаже
     */
    Cards getAllCardsStorageFromSale(Integer page);

    /**
     * Удалить товар из списка продаж / убрать с полки.
     * @param id - id товара
     */
    void deleteCardFromSaleById(Integer id);

    /**
     * Закупка единицы товара у поставщика и сохранение на складе магазина
     * @param cardsStorage единица товара
     */
    void saveCardStorage(CardsStorage cardsStorage);

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

    /**
     * Получить общую сумму товаров в корзине
     * @return сумма товаров в корзине
     */
    Double getTotalPriceFromBasket();

    /**
     * Удалить все товары из корзины
     */
    void deleteAllFromBasket();
}
