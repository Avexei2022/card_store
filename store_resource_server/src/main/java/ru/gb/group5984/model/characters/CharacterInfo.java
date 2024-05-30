package ru.gb.group5984.model.characters;

import lombok.Data;

/**
 * Информационная часть страницы о списке товаров, находящихся на складе,
 *  но не выставленных на продажу.
 */
@Data
public class CharacterInfo {

    /**
     * Количество товара на складе невыставленного на продажу.
     */
    private Integer count;

    /**
     * Количество страниц в списке товаров.
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
