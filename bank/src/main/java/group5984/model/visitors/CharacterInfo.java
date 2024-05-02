package group5984.model.visitors;

import lombok.Data;

/**
 * Информационная часть страницы кандидатов в клиенты:
 * - общее количество кандидатов;
 * - количество страниц в списке кандидатов;
 * - номера следующей, текущей и предыдущей страниц.
 */
@Data
public class CharacterInfo {
    private Integer count;
    private Integer pages;
    private String next;
    private String prev;
}
