package kolodin.model.characters;

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
    private CharacterInfo info;

    /**
     * Страница персонажей.
     */
    private List<CharacterResult> results;
}
