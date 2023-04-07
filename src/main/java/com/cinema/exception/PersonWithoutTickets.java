package com.cinema.exception;

public class PersonWithoutTickets extends Exception {

    public PersonWithoutTickets() {
    }

    public PersonWithoutTickets(String message) {
        super(message);
    }

    public PersonWithoutTickets(String message, Throwable cause) {
        super(message, cause);
    }
}
