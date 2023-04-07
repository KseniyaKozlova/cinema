package com.cinema.exception;

import java.util.function.Supplier;

public class NoSuchPersonException extends RuntimeException {
    public NoSuchPersonException() {
        super();
    }

    public NoSuchPersonException(String message) {
        super(message);
    }

    public NoSuchPersonException(String message, Throwable cause) {
        super(message, cause);
    }

    public static Supplier<NoSuchPersonException> noSuchPersonException(String message) {
        return () -> new NoSuchPersonException(message);
    }
}
