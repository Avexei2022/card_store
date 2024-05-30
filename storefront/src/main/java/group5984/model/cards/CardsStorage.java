package group5984.model.cards;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Товар, выставленный на продажу с ценником и остатками на складе
 */
@Data
public class CardsStorage {

    /**
     * Уникальный номер товара.
     */
    private Long id;

    /**
     * Данные о товаре.
     */
    private Card card;

    /**
     * Количество товара.
     */
    private Integer amount;

    /**
     * Цена единицы товара.
     */
    private BigDecimal price;
}
