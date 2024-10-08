package ru.kolodin.service.db;

import ru.kolodin.model.basket.Basket;
import ru.kolodin.model.basket.CardInBasket;
import ru.kolodin.model.characters.CharacterResult;
import ru.kolodin.model.characters.Characters;
import ru.kolodin.model.messeges.Message;
import ru.kolodin.model.storage.Cards;
import ru.kolodin.model.storage.CardsStorage;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс сервиса склада магазина.
 */
public interface ServerDbService {

    /**
     * Сохранение единицы товара, закупленного у поставщика,
     * в базе данных товаров на складе.
     * @param characterResult Единица товара.
     */
    void saveOneCharacter(CharacterResult characterResult);


    /**
     * Получить все товары постранично, хранящиеся на складе.
     * @param page - номер страницы.
     * @return список товаров на складе.
     */
    Characters getPageCharactersFromStorage(Integer page);

    /**
     * Удалить единицу товара из базы данных товаров на складе.
     * @param id уникальный номер товара.
     * @return сообщение о результате удаления товара.
     */
    Message deleteFromStorageById(Integer id);

    /**
     * Выствить товар на продажу.
     * @param id уникальный номер товара.
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
     * @param id уникальный номер товара.
     */
    void deleteCardFromSaleById(Long id);

    /**
     * Закупка единицы товара у поставщика и сохранение на складе магазина.
     * @param cardsStorage единица товара.
     */
    void saveCardStorage(CardsStorage cardsStorage);

    /**
     * Переместить единицу товара с полки в корзину покупателя.
     * @param cardId - уникальный номер товара в продаже.
     * @param userName - имя/логин покупателя.
     */
    void moveCardToBasket(Long cardId, String userName);

    /**
     * Возврат единицы товара из корзины покупателя на полку магазина.
     * @param id уникальный номер товара.
     */
    void returnCardFromBasketToSale(Long id);

    /**
     * Получить все товары постранично, зарезервированные в корзине.
     * @param page - запрашиваемая пользователем страница.
     * @param userName - имя/логин покупателя.
     * @return список товаров в корзине.
     */
    Basket getPageFromBasket(Integer page, String userName);

    /**
     * Получить все товары, зарезервированные в корзине.
     * @return список товаров в корзине.
     */
    List<CardInBasket> getAllFromBasket();

    /**
     * Получить общую сумму товаров в корзине покупателя.
     * @param userId - уникальный номер покупателя.
     * @return сумма товаров в корзине.
     */
    BigDecimal getTotalPriceFromBasket(Long userId);

    /**
     * Удалить все товары из корзины покупателя.
     * @param userId - уникальный номер покупателя.
     */
    void deleteAllFromBasket(Long userId);

    /**
     * Регистрация нового пользователя.
     * @param characterResult пользователь.
     */
    void registerNewUser(CharacterResult characterResult);
}
