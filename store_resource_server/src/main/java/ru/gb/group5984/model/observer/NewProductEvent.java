package ru.gb.group5984.model.observer;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.gb.group5984.model.storage.CardsStorage;

/**
 * Событие поступления нового товара в продажу.
 */
@Getter
public class NewProductEvent extends ApplicationEvent {

    /**
     * Товар на складе.
     */
    private final CardsStorage cardsStorage;

    /**
     * Конструктор класса.
     * @param source источник.
     * @param cardsStorage товар на складе.
     */
    public NewProductEvent(Object source, CardsStorage cardsStorage) {
        super(source);
        this.cardsStorage = cardsStorage;

    }
}
