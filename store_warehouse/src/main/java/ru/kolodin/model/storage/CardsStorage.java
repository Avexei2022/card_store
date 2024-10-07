package ru.kolodin.model.storage;

import lombok.Data;

import ru.kolodin.model.characters.CharacterResult;

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
