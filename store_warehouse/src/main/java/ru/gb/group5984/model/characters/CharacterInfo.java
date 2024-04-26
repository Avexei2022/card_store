package ru.gb.group5984.model.characters;

import lombok.Data;

/**
 * Информационная часть страницы в соотвествии с документацией Rick and Morty
 */
@Data
public class CharacterInfo {
    private Integer count;
    private Integer pages;
    private String next;
    private String prev;
}
