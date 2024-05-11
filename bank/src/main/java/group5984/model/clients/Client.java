package group5984.model.clients;

import group5984.model.visitors.CharacterResult;
import lombok.Data;

/**
 * Клиент банка
 * Добавлены аннотации для базы данных
 */
@Data
public class Client {
    private Long id;
    private CharacterResult clientDetail;
    private Double balance;

}
