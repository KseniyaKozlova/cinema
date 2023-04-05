package com.cinema.service;

import com.cinema.model.Movie;
import com.cinema.model.Ticket;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketService {

    void bookTicket(UUID idPerson, Optional<Movie> movie);

    void getAllPossibleFilmTickets(String idMovie);

    public void returnTicket(UUID idPerson);

    void getAllUserTickets(UUID id);

}
