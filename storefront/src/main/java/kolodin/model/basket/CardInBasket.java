package kolodin.model.basket;

import kolodin.model.cards.Card;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Товар в корзине покупателей, информация о котором хранится в базе данных.
 */
@Data
public class CardInBasket {

    /**
     * Уникальный номер товара.
     */
    private Long id;

    /**
     * Данные о товаре.
     */
    private Card card;

    /**
     * Количество товара одного наименования в корзине.
     */
    private Integer amount;

    /**
     * Цена единицы товара.
     */
    private BigDecimal price;

    /**
     * Уникальный номер товара на складе.
     */
    private Long cardsStorageId;

    /**
     * Дата перемещения товара в корзину - резервирования товара.
     */
    private Date created;
}
