package ru.gb.group5984.model.storage;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Data
public class Cards {
    CardsInfo info;
    List<CardsStorage> cardsStorageList;
}
