package group5984.model.storage;

import group5984.model.characters.Card;
import lombok.Data;

@Data
public class CardsStorage {
    private Long id;
    private Card card;
    private Integer amount;
    private Double price;
}
