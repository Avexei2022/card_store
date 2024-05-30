package group5984.model.cards;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Карточка героя в соответствии с документацией Rick and Morty

 */
@Data
@Setter
@Getter
public class Card {

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
     * Дата создания.
     */
    private Date created;
}
