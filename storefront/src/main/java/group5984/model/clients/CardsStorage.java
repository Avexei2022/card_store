package group5984.model.clients;

import group5984.model.visitors.Card;
import lombok.Data;

/**
 * Товар, выставленный на продажу с ценником и остатками на складе
 */
@Data
public class CardsStorage {
    private Long id;
    private Card card;
    private Integer amount;
    private Double price;
}
