package ru.gb.group5984.model.observer;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.gb.group5984.model.storage.CardsStorage;

/**
 * Событие поступления нового товара в продажу.
 */
@Getter
public class NewProductEvent extends ApplicationEvent {
    private final CardsStorage cardsStorage;


    public NewProductEvent(Object source, CardsStorage cardsStorage) {
        super(source);
        this.cardsStorage = cardsStorage;

    }
}
