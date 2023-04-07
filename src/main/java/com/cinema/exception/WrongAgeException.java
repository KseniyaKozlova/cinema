package com.cinema.exception;

public class WrongAgeException extends Exception{

    public WrongAgeException() {
    }

    public WrongAgeException(String message) {
        super(message);
    }

    public WrongAgeException(String message, Throwable cause) {
        super(message, cause);
    }
}
