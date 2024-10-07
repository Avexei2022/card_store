package ru.kolodin.model.exceptions;

/**
 * Пользовательское исключение.
 */
public class ResourceNotFoundException  extends RuntimeException{

    /**
     * Исключение о недоступности ресурса или об отсутствии данных.
     * @param message текст сообщения об ошибке.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
