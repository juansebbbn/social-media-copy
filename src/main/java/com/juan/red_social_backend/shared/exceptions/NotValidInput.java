package com.juan.red_social_backend.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotValidInput extends RuntimeException {
    public NotValidInput(String message) {
        super(message);
    }
}