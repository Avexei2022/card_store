package ru.gb.group5984.model.basket;

import lombok.Data;
import ru.gb.group5984.model.characters.CharacterResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Товар в корзине покупателей, информация о котором хранится в базе данных.
 */

@Data
public class CardInBasket {

    /**
     * Уникальный номер товара.
     */
    private Long id;

    /**
     * Данные о товаре.
     */
    private CharacterResult card;

    /**
     * Количество товара одного наименования в корзине.
     */
    private Integer amount;

    /**
     * Цена единицы товара.
     */
    private BigDecimal price;

    /**
     * Уникальный номер товара на складе.
     */
    private Long cardsStorageId;

    /**
     * Дата перемещения товара в корзину - резервирования товара.
     */
    private LocalDateTime created;
}
