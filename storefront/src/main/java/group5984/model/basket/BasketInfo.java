package group5984.model.basket;

import lombok.Data;

/**
 * Информация о корзине покупателя:
 * - количество товара;
 * - количество страниц в списке товаров корзины;
 * - номера следующей, текущей и предыдущей страниц;
 * - общая сумма товара в корзине.
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
