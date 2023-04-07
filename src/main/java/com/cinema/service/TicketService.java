package com.cinema.service;

import com.cinema.model.Movie;
import com.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketService {

    /**
     * Book ticket for user
     */
    void bookTicket(String idPerson, Optional<Movie> movie);

    /**
     * Return booked ticket
     */
    void returnTicket(String idPerson);

    /**
     * See all booked tickets for special user
     */
    void getAllUserTickets(String id);

    /**
     * Book ticket for manager/administrator
     */
    void bookTicket(String idPerson);

}
