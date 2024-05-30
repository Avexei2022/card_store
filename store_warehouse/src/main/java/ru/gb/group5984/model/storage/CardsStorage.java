package ru.gb.group5984.model.storage;

import lombok.Data;

import ru.gb.group5984.model.characters.CharacterResult;

import java.math.BigDecimal;


/**
 * Товар, выставленный на продажу, информация о котором хранится в базе данных.
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
    private CharacterResult card;

    /**
     * Количество товара.
     */
    private Integer amount;

    /**
     * Цена единицы товара.
     */
    private BigDecimal price;
}
