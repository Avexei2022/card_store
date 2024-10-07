package ru.kolodin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kolodin.model.exceptions.ExceptionBody;
import ru.kolodin.model.exceptions.ExcessAmountException;
import ru.kolodin.model.exceptions.ResourceNotFoundException;

import java.time.LocalDateTime;


/**
 * Контроллер обработки исключений.
 */
@RestController
public class StorageRestExceptionController {
    /**
     * Исключение при недостатке товара на складе.
     * @param e объект исключения.
     * @return тело/обертка исключения.
     */
    @ExceptionHandler(ExcessAmountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody excessAmount(ExcessAmountException e){
        ExceptionBody exceptionBody = new ExceptionBody();
        exceptionBody.setMessage(e.getMessage());
        exceptionBody.setDateTime(LocalDateTime.now());
        return exceptionBody;
    }

    /**
     * Исключение при отсутствии товара на складе.
     * @param e объект исключения.
     * @return тело/обертка исключения.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody resourceNotFound(ResourceNotFoundException e){
        ExceptionBody exceptionBody = new ExceptionBody();
        exceptionBody.setMessage(e.getMessage());
        exceptionBody.setDateTime(LocalDateTime.now());
        return exceptionBody;
    }
}
