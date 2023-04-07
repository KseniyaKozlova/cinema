package com.cinema.exception;

public class InconvertibleIdException extends RuntimeException{

    public InconvertibleIdException() {
    }

    public InconvertibleIdException(String message) {
        super(message);
    }

    public InconvertibleIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
