package ru.gb.group5984.model.basket;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Информационная часть страницы о списке товаров, находящихся
 * в корзине покупателей.
 */
@Data
public class BasketInfo {

    /**
     * Общее количество товаров в корзине.
     */
    private Long count;

    /**
     * Количество страниц в списке товаров.
     */
    private Integer pages;

    /**
     * Номер следующей страницы.
     */
    private Integer next;

    /**
     * Номер текущей страницы.
     */
    private Integer current;

    /**
     * Номер предыдущей страницы.
     */
    private Integer prev;

    /**
     * Общая сумма товаров в корзине.
     */
    private BigDecimal totalPrice;
}
