package ru.gb.group5984.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.group5984.model.exceptions.ExceptionBody;
import ru.gb.group5984.model.exceptions.ExcessAmountException;
import ru.gb.group5984.model.exceptions.ResourceNotFoundException;

import java.time.LocalDateTime;


/**
 * Контроллер обработки исключений.
 */
@RestController
public class BankRestExceptionController {
    /**
     * Исключение при недостатке средств на счете клиента.
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
     * Исключение при отсутствии счета.
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
