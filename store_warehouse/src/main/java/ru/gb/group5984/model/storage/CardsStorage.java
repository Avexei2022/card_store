package ru.gb.group5984.model.storage;

import lombok.Data;

import ru.gb.group5984.model.characters.CharacterResult;


/**
 * Товар, выставленный на продажу, информация о котором хранится в базе данных
 */
@Data
public class CardsStorage {
    private Long id;
    private CharacterResult card;
    private Integer amount;
    private Double price;
}
