package ru.gb.group5984.model.storage;

import jakarta.persistence.*;
import lombok.Data;
import ru.gb.group5984.model.characters.CharacterResult;

import java.math.BigDecimal;


/**
 * Товар, выставленный на продажу, информация о котором хранится в базе данных
 */
@Entity
@Data
@Table(name = "cards_storage")
public class CardsStorage {
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
}
