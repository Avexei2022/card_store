package group5984.model.clients;

import lombok.Data;

/**
 * Информационная часть страницы клиентов банка.
 */
@Data
public class ClientsListInfo {

    /**
     * Общее количество клиентов банка.
     */
    private Long count;

    /**
     * Количество страниц в списке клиентов банка.
     */
    private Integer pages;

    /**
     * Номер следующей страницы.
     */
    private Integer next;

    /**
     * Номер текущей страницы.
     */
    private Integer current;

    /**
     * Номер предыдущей страницы.
     */
    private Integer prev;
}
