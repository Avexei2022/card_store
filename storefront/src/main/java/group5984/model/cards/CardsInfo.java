package group5984.model.cards;

import lombok.Data;

/**
 * Информационная часть страницы:
 * - общее количество товара выставленного на продажу;
 * - количество страниц в списке товаров;
 * - номера следующей, текущей и предыдущей страниц.
 */
@Data
public class CardsInfo {
    private Long count;
    private Integer pages;
    private Integer next;
    private Integer current;
    private Integer prev;
}
