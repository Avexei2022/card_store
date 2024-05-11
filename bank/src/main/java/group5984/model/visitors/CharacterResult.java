package group5984.model.visitors;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Посетители, желающие открыть счет в банке
 * Добавлены аннотации для базы данных
 */
@Data
@Setter
@Getter
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
