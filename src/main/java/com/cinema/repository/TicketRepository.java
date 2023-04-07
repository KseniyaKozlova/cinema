package com.cinema.repository;

import com.cinema.model.Ticket;

import java.util.List;

public interface TicketRepository {

    /**
     * Create tickets for new seance (standart cinemahall)
     */
    boolean createTicket(List <Ticket> tickets);

    /**
     * Get all specified user tickets from DB
     */
    List<Ticket> getAllUserTickets(String id) throws RuntimeException;

    /**
     * Return user ticket (set null)
     */
    boolean updateForReturn(Integer id, String personId);

    /**
     * Book ticket that user chose
     */
    int bookTicket(String id_person, String id_movie, Integer raw, Integer place);

    /**
     * Get all tickets for specified film
     */
    List<Ticket> getAllPossibleFilmTickets(String idMovie);
}
