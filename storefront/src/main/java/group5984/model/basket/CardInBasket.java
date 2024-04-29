package group5984.model.basket;

import group5984.model.characters.Card;
import lombok.Data;

import java.util.Date;


@Data
public class CardInBasket {
    private Long id;
    private Card card;
    private Integer amount;
    private Double price;
    private Long cardsStorageId;
    private Date created;
}
