package group5984.model.basket;

import lombok.Data;

import java.util.List;

/**
 * Корзина покупателя. Содержит информационную часть и список товара.
 */
@Data
public class Basket {
    BasketInfo info;
    List<CardInBasket> cardInBasketList;
}
