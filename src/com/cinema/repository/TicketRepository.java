package com.cinema.repository;

import com.cinema.model.Ticket;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository {

    boolean createTicket(List <Ticket> tickets);

    List<Ticket> getAllTickets();

    List<Ticket> getAllUserTickets(UUID id);

    public boolean updateForReturn(Integer id, UUID personId);

    int bookTicket(UUID id_person, String id_movie, Integer raw, Integer place);

    List<Ticket> getAllPossibleFilmTickets(String idMovie);

//    Person getTicketByID(String username);

//    Ticket update(Ticket person, Integer id);


    boolean deleteTicketById(Integer id);
}
