package ru.gb.group5984.model.basket;

import jakarta.persistence.*;
import lombok.Data;
import ru.gb.group5984.model.characters.CharacterResult;
import ru.gb.group5984.model.users.Buyer;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Товар в корзине покупателей, информация о котором хранится в базе данных
 */

@Entity
@Data
@Table(name = "card_in_basket")
public class CardInBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id")
    private CharacterResult card;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name="cards_storage_id")
    private Long cardsStorageId;
    @Column(name = "created")
    private LocalDate created;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Buyer user;
}
