package ru.gb.group5984.model.basket;

import lombok.Data;
import ru.gb.group5984.model.characters.CharacterResult;

import java.time.LocalDateTime;

/**
 * Товар в корзине покупателей, информация о котором хранится в базе данных
 */

@Data
public class CardInBasket {
    private Long id;
    private CharacterResult card;
    private Integer amount;
    private Double price;
    private Long cardsStorageId;
    private LocalDateTime created;
}
