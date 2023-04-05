package com.cinema.util.exception;

public class AllPlacesBookedException extends Exception{

    public AllPlacesBookedException() {
    }

    public AllPlacesBookedException(String message) {
        super(message);
    }

    public AllPlacesBookedException(String message, Throwable cause) {
        super(message, cause);
    }
}
