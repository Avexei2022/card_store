package group5984.model.characters;

import lombok.Data;

import java.util.List;

/**
 * Страница со списком персонажей.
 */
@Data
public class Characters {

    /**
     * Информационная часть страницы.
     */
    CharacterInfo info;

    /**
     * Страница персонажей.
     */
    List<CharacterResult> results;
}
