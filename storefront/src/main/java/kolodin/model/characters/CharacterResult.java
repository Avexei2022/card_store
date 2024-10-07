package kolodin.model.characters;

import lombok.Data;

import java.util.Date;

/**
 * Граждане желающие зарегистрироваться в магазине.
 * Они же - персонажи Rick and Morty.
 */

@Data
public class CharacterResult {

    /**
     * Уникальный номер.
     */
    private Integer id;

    /**
     * Имя.
     */
    private String name;

    /**
     * Статус.
     */
    private String status;

    /**
     * Особенности.
     */
    private String species;

    /**
     * Тип.
     */
    private String type;

    /**
     * Пол.
     */
    private String gender;

    /**
     * Изображение.
     */
    private String image;

    /**
     * Ссылка на страницу персонажа.
     */
    private String url;

    /**
     * Дата регистрации.
     */
    private Date created;
}
