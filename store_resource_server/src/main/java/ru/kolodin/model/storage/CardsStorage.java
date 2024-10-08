package ru.kolodin.model.storage;

import jakarta.persistence.*;
import lombok.Data;
import ru.kolodin.model.characters.CharacterResult;

import java.math.BigDecimal;


/**
 * Товар, выставленный на продажу
 * , информация о котором хранится в базе данных.
 */
@Entity
@Data
@Table(name = "cards_storage")
public class CardsStorage {
    /**
     * Уникальный номер товара.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Данные о товаре.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id")
    private CharacterResult card;
    /**
     * Количество товара.
     */
    @Column(name = "amount")
    private Integer amount;
    /**
     * Цена единицы товара.
     */
    @Column(name = "price")
    private BigDecimal price;
}
