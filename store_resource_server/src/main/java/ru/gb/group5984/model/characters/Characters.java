package ru.gb.group5984.model.characters;

import lombok.Data;

import java.util.List;

/**
 * Страница со списком товаров, находящихся на складе, но не выставленных на продажу.
 * Содержит информационную часть о странице и список товаров.
 */
@Data
public class Characters {
    CharacterInfo info;
    List<CharacterResult> results;
}
