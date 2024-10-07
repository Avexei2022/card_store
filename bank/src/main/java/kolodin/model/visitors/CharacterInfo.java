package kolodin.model.visitors;

import lombok.Data;

/**
 * Информационная часть страницы кандидатов в клиенты банка.
 */
@Data
public class CharacterInfo {

    /**
     * Общее количество кандидатов в клиенты банка.
     */
    private Integer count;

    /**
     * Количество страниц в списке кандидатов;
     */
    private Integer pages;

    /**
     * Номер следующей страницы.
     */
    private String next;

    /**
     * Номер предыдущей страницы.
     */
    private String prev;
}
