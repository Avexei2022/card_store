package ru.gb.group5984.model.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Тело/обертка исключения
 */
@Data
public class ExceptionBody {
    private String message;
    private LocalDateTime dateTime;
}
