package com.juan.red_social_backend.shared.exceptions;

public class WrongCredentials extends RuntimeException{
    public WrongCredentials(String message) {
        super(message);
    }
}
