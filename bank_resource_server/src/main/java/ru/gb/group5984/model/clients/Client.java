package ru.gb.group5984.model.clients;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import ru.gb.group5984.model.visitors.CharacterResult;

import java.math.BigDecimal;

/**
 * Клиент банка
 * Добавлены аннотации для базы данных
 */
@Entity
@Data
@Table(name = "clientOfBank")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account")
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_detail")
    private CharacterResult clientDetail;
    @Column(name = "balance")
    private BigDecimal balance;

}
