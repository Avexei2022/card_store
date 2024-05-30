package group5984.service.api;

import group5984.model.basket.Basket;
import group5984.model.cards.Cards;
import group5984.model.messeges.Message;


/**
 * Интерфейс сервиса взаимодействия с API сервиса ресурсов магазина.
 */
public interface ContentApiService {

    /**
     * Получить со склада список товаров, выставленных на продажу.
     * @param page запрашиваемая страница товаров.
     * @return Список товаров на полке.
     */
    Cards getPageFromSale(String page);

    /**
     * Получить список товаров в корзине покупателя.
     * @param userName имя/логин покупателя.
     * @param page запрашиваемая страница товаров
     * @return список товаров в корзине
     */
    Basket getPageFromBasket(String userName, String page);

    /**
     * Добавить товар в корзину покупателя.
     * @param id - уникальный номер товара.
     * @param userName - имя пользователя.
     * @return сообщение о результате.
     */
    Message addToBasketById(Integer id, String userName);

    /**
     * Вернуть товар из корзины покупателя на полку
     * @param id - уникальный номер товара.
     */
    void deleteFromBasketById(Integer id);

    /**
     * Оплатить товар в корзине.
     * @param userName имя пользователя.
     * @return сообщение о результате.
     */
    Message basketPay(String userName);

}
