package com.juan.red_social_backend.shared.exceptions;

public class NotEnoughPermissions extends RuntimeException {
    public NotEnoughPermissions(String message) {
        super(message);
    }
}
