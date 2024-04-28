package group5984.model.storage;

import group5984.model.characters.Content;
import lombok.Data;


@Data
public class CardsStorage {
    private Long id;
    private Content content;
    private Integer amount;
    private Double price;
}
