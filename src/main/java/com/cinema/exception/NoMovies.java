package com.cinema.exception;

public class NoMovies extends RuntimeException{

    public NoMovies() {
    }

    public NoMovies(String message) {
        super(message);
    }

    public NoMovies(String message, Throwable cause) {
        super(message, cause);
    }
}
