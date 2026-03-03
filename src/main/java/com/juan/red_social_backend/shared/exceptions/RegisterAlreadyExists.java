package com.juan.red_social_backend.shared.exceptions;

public class RegisterAlreadyExists extends RuntimeException{
    public RegisterAlreadyExists(String message) {
        super(message);
    }
}
