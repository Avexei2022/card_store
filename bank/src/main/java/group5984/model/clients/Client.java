package group5984.model.clients;

import group5984.model.visitors.CharacterResult;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Клиент банка.
 */
@Data
public class Client {

    /**
     * Уникальный номер клиента.
     */
    private Long id;

    /**
     * Данные клиента.
     */
    private CharacterResult clientDetail;

    /**
     * Баланс счета в банке.
     */
    private BigDecimal balance;

}
