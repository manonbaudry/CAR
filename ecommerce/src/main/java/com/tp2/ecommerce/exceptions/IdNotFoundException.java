package com.tp2.ecommerce.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class IdNotFoundException extends Exception {
    public IdNotFoundException(String message) {
        super(message);
    }
}
