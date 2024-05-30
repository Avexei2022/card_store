package group5984.model.visitors;

import lombok.Data;

import java.util.List;

/**
 * Страница со списком кандидатов в клиенты банка.
 */
@Data
public class Characters {

    /**
     * Информационная часть страницы кандидатов в клиенты банка.
     */
    private CharacterInfo info;

    /**
     * Страница из списка кандидатов в клиенты банка.
     */
    private List<CharacterResult> results;
}
