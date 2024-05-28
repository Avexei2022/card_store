package group5984.model.cards;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Товар, выставленный на продажу с ценником и остатками на складе
 */
@Data
public class CardsStorage {
    private Long id;
    private Card card;
    private Integer amount;
    private BigDecimal price;
}
