package ru.gb.group5984.model.basket;

import lombok.Data;

import java.util.List;

/**
 * Страница со списком товаров, находящихся в корзине покупателей.
 * Содержит информационную часть о странице и список товаров.
 */
@Data
public class Basket {
    BasketInfo info;
    List<CardInBasket> cardInBasketList;
}
