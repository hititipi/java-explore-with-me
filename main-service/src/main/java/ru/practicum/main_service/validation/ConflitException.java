package ru.practicum.main_service.validation;

import org.springframework.http.HttpStatus;

public class ConflitException extends ValidationException {
    public ConflitException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
