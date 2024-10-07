package kolodin.model.basket;

import lombok.Data;

import java.util.List;

/**
 * Корзина покупателя. Содержит информационную часть и список товара.
 */
@Data
public class Basket {

    /**
     * Информационная часть страницы списка товаров.
     */
    private BasketInfo info;

    /**
     * Список товаров.
     */
    private List<CardInBasket> cardInBasketList;
}
