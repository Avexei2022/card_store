package group5984.model.basket;

import group5984.model.cards.Card;
import lombok.Data;

import java.math.BigDecimal;
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
    private BigDecimal price;
    private Long cardsStorageId;
    private Date created;
}
