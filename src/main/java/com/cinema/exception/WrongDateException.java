package com.cinema.exception;

public class WrongDateException extends Exception{

    public WrongDateException() {
    }

    public WrongDateException(String message) {
        super(message);
    }

    public WrongDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
