package ru.gb.group5984.model.characters;

import lombok.Data;

/**
 * Информационная часть страницы о списке товаров, находящихся на складе
 * , но не выставленных на продажу.
 */
@Data
public class CharacterInfo {
    private Integer count;
    private Integer pages;
    private String next;
    private String prev;
}
