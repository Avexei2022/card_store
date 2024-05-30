package ru.gb.group5984.model.exceptions;

/**
 * Пользовательское исключение.
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
