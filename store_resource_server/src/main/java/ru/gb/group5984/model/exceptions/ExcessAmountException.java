package ru.gb.group5984.model.exceptions;

/**
 * На счете недостаточно средств
 */
public class ExcessAmountException extends RuntimeException{
    public ExcessAmountException(String message) {
        super(message);
    }
}
