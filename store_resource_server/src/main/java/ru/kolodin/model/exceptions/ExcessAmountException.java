package ru.kolodin.model.exceptions;

/**
 * На счете недостаточно средств
 */
public class ExcessAmountException extends RuntimeException{

    /**
     * Исключение при недостатке средств на счете клиента.
     * @param message текст сообщения об ошибке.
     */
    public ExcessAmountException(String message) {
        super(message);
    }
}
