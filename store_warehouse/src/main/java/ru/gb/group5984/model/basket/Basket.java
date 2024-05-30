package ru.gb.group5984.model.basket;

import lombok.Data;

import java.util.List;

/**
 * Страница со списком товаров, находящихся в корзине покупателей.
 */
@Data
public class Basket {

    /**
     * Информационная часть страницы списка товаров.
     */
    BasketInfo info;

    /**
     * Список товаров.
     */
    List<CardInBasket> cardInBasketList;
}
