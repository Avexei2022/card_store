package group5984.model.visitors;

import lombok.Data;

import java.util.List;

/**
 * Страница со списком кандидатов в клиенты банка.
 * Содержит информационную часть о странице и список кандидатов.
 */
@Data
public class Characters {
    CharacterInfo info;
    List<CharacterResult> results;
}
