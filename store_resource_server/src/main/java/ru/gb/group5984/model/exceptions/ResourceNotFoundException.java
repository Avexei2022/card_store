package ru.gb.group5984.model.exceptions;

/**
 * Счет отсутствует.
 */
public class ResourceNotFoundException  extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
