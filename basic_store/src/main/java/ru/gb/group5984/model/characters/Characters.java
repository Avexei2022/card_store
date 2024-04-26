package ru.gb.group5984.model.characters;

import lombok.Data;

import java.util.List;

/**
 * Объединенная информация о странице и списке героев
 * в соответствии с документацией Rick and Morty
 */
@Data
public class Characters {
    CharacterInfo info;
    List<CharacterResult> results;
}
