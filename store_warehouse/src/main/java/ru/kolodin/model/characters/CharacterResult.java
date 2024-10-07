package ru.kolodin.model.characters;

import lombok.Data;

import java.util.Date;

/**
 * Товар, находящийся на складе, но не выставленный на продажу,
 * информация о котором хранится в базе данных.
 */

@Data
public class CharacterResult {

    /**
     * Уникальный номер товара.
     */
    private Integer id;

    /**
     * Наименование товара.
     */
    private String name;

    /**
     * Статус товара.
     */
    private String status;

    /**
     * Особенности товара.
     */
    private String species;

    /**
     * Тип товара.
     */
    private String type;

    /**
     * Принадлежность товара.
     */
    private String gender;

    /**
     * Изображение товара.
     */
    private String image;

    /**
     * Ссылка на товар.
     */
    private String url;

    /**
     * Дата производство товара.
     */
    private Date created;
}
