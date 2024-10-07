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
    private CardsInfo info;

    /**
     * Список товаров.
     */
    private List<CardsStorage> cardsStorageList;
}
