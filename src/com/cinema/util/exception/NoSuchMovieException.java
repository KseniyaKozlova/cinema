package com.cinema.util.exception;

import java.util.function.Supplier;

public class NoSuchMovieException extends RuntimeException {
    public NoSuchMovieException(String message) {
        super(message);
    }

    public static Supplier<NoSuchPersonException> noSuchPersonException(String message) {
        return () -> new NoSuchPersonException(message);
    }
}
