package group5984.model.visitors;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Кандидаты, желающие открыть счет в банке.
 */
@Data
@Setter
@Getter
public class CharacterResult {

    /**
     * Уникальный номер кандидата.
     */
    private Integer id;

    /**
     * Имя кандидата.
     */
    private String name;

    /**
     * Статус кандидата.
     */
    private String status;

    /**
     * Особенности кандидата.
     */
    private String species;

    /**
     * Тип кандидата.
     */
    private String type;

    /**
     * Пол кандидата.
     */
    private String gender;

    /**
     * Фото кандидата.
     */
    private String image;

    /**
     * Ссылка на страницу кандидата.
     */
    private String url;

    /**
     * Дата создания.
     */
    private Date created;
}
