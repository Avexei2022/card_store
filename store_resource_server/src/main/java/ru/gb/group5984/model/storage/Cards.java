package ru.gb.group5984.model.storage;


import lombok.Data;

import java.util.List;

/**
 * Страница со списком товаров, выставленных на продажу.
 * Содержит информационную часть о странице и список товаров.
 */
@Data
public class Cards {
    CardsInfo info;
    List<CardsStorage> cardsStorageList;
}
