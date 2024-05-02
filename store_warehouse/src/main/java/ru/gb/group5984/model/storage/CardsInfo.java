package ru.gb.group5984.model.storage;

import lombok.Data;

/**
 * Информационная часть страницы о списке товаров, выставленных на продажу,
 * загруженная постранично из базы данных.
 */
@Data
public class CardsInfo {
    private Long count;
    private Integer pages;
    private Integer next;
    private Integer current;
    private Integer prev;
}
