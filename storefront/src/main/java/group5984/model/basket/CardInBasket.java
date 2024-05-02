package group5984.model.basket;

import group5984.model.visitors.Card;
import lombok.Data;

import java.util.Date;

/**
 * Товар в корзине покупателя:
 * - карточка товара;
 * - количество товара;
 * - стоимость единицы товара;
 * - номер товара в базе данных склада;
 * - дата и время перемещения товара в корзину.
 */
@Data
public class CardInBasket {
    private Long id;
    private Card card;
    private Integer amount;
    private Double price;
    private Long cardsStorageId;
    private Date created;
}
