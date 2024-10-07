package ru.kolodin.model.basket;

import jakarta.persistence.*;
import lombok.Data;
import ru.kolodin.model.characters.CharacterResult;
import ru.kolodin.model.users.Buyer;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Товар в корзине покупателей
 * , информация о котором хранится в базе данных.
 */
@Entity
@Data
@Table(name = "card_in_basket")
public class CardInBasket {
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
     * Количество товара одного наименования в корзине.
     */
    @Column(name = "amount")
    private Integer amount;
    /**
     * Цена единицы товара.
     */
    @Column(name = "price")
    private BigDecimal price;
    /**
     * Уникальный номер товара на складе.
     */
    @Column(name="cards_storage_id")
    private Long cardsStorageId;
    /**
     * Дата перемещения товара в корзину
     * - резервирования товара.
     */
    @Column(name = "created")
    private LocalDate created;
    /**
     * Уникальный номер покупателя.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Buyer user;
}
