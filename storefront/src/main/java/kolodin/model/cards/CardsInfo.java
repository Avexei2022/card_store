package kolodin.model.cards;

import lombok.Data;

/**
 * Информационная часть страницы о списке товаров, выставленных на продажу,
 * загруженная постранично из базы данных.
 */
@Data
public class CardsInfo {

    /**
     * Общее количество товаров в продаже.
     */
    private Long count;

    /**
     * Количество страниц в общем списке товаров.
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
