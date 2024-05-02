package group5984.model.clients;

import group5984.model.visitors.CharacterResult;
import jakarta.persistence.*;
import lombok.Data;

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
    private Double balance;

}
