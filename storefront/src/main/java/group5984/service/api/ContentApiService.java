package group5984.service.api;

import group5984.model.basket.Basket;
import group5984.model.clients.Cards;
import group5984.model.messeges.Message;


/**
 * Интерфейс сервиса витрины / торгового зала магазина.
 * Готовит запросы на склад и получает от него соответствующие ответы
 */
public interface ContentApiService {

    /**
     * Получить со склада список товаров, выставленных на продажу.
     * @param page запрашиваемая страница товаров.
     * @return Список товаров на полке.
     */
    Cards getAllFromSale(String page);

    /**
     * Получить список товаров в корзине покупателя.
     * @param userName имя/логин покупателя.
     * @param page запрашиваемая страница товаров
     * @return список товаров в корзине
     */
    Basket getPageFromBasket(String userName, String page);

    /**
     * Добавить товар в корзину покупателя
     * @param id - id товара
     * @param userName - имя пользователя.
     */
    Message addToBasketById(Integer id, String userName);

    /**
     * Вернуть товар из корзины покупателя на полку
     * @param id - id товара
     */
    void deleteFromBasketById(Integer id);

    /**
     * Оплатить товар в корзине
     */
    Message basketPay(String userName);

}
