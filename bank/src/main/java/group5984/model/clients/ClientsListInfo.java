package group5984.model.clients;

import lombok.Data;

/**
 * Информационная часть страницы клиентов банка:
 * - общее количество клиентов;
 * - количество страниц в списке кандидатов;
 * - номера следующей, текущей и предыдущей страниц.
 */
@Data
public class ClientsListInfo {
    private Long count;
    private Integer pages;
    private Integer next;
    private Integer current;
    private Integer prev;
}
