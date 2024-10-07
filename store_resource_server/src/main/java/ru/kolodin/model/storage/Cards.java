package ru.kolodin.model.storage;


import lombok.Data;

import java.util.List;

/**
 * Страница со списком товаров, выставленных на продажу.
 */
@Data
public class Cards {

    /**
     * Информационная часть о странице.
     */
    CardsInfo info;

    /**
     * Список товаров.
     */
    List<CardsStorage> cardsStorageList;
}
