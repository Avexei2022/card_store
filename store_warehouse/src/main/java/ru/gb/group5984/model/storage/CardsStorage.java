package ru.gb.group5984.model.storage;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.gb.group5984.model.characters.CharacterResult;

@Entity
@Data
@Table(name = "cards_storage")
@Setter
@Getter
public class CardsStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private CharacterResult card;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "price")
    private Double price;
}
