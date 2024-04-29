package group5984.model.characters;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Карточка героя в соответствии с документацией Rick and Morty
 * Добавлены аннотации для базы данных
 */
@Data
@Setter
@Getter
public class Card {
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
