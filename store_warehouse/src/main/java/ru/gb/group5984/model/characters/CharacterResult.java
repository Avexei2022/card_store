package ru.gb.group5984.model.characters;

import lombok.Data;

import java.util.Date;

/**
 * Товар, находящийся на складе, но не выставленный на продажу,
 * информация о котором хранится в базе данных.
 */

@Data
public class CharacterResult {
    private Integer id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private String image;
    private String url;
    private Date created;
}
