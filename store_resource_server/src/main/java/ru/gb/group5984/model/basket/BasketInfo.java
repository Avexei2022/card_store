package ru.gb.group5984.model.basket;

import lombok.Data;

/**
 * Информационная часть страницы о списке товаров, находящихся
 * в корзине покупателей.
 */
@Data
public class BasketInfo {
    private Long count;
    private Integer pages;
    private Integer next;
    private Integer current;
    private Integer prev;
    private Double totalPrice;
}
