package group5984.service.api;

import group5984.model.basket.Basket;
import group5984.model.clients.Cards;

/**
 * Интерфейс сервиса витрины / торгового зала магазина
 * Готовит запросы на склад и получает от него соответствующие ответы
 */
public interface ContentApiService {

    /**
     * Получить со склада список товаров, выставленных на продажу
     * @param way путь соответствующего запроса на склад
     * @return Список товаров на полке
     */
    Cards getAllFromSale(String way);

    /**
     * Получить список товаров в корзине покупателя
     * @param way путь соответствующего запроса на склад
     * @return список товаров в корзине
     */
    Basket getAllFromBasket(String way);

    /**
     * Добавить товар в корзину покупателя
     * @param id - id товара
     */
    void addToBasketById(Integer id);

    /**
     * вернуть товар из корзины покупателя на полку
     * @param id - id товара
     */
    void deleteFromBasketById(Integer id);

    /**
     * Оплатить товар в корзине
     */
    void basketPay();
}
