package com.cinema.service;

import com.cinema.model.Movie;
import com.cinema.model.Ticket;
import com.cinema.repository.TicketRepository;
import com.cinema.exception.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;
    private Scanner scanner = new Scanner(System.in);

    private static final Pattern PATTERN_UUID =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    public void bookTicket(String idPerson) {
        try {
            checkUUID(idPerson);
            scanner = new Scanner(System.in);
            String idMovie = scanner.nextLine();
            checkUUID(idMovie);
            log.info("Chosen movie has id: " + idMovie);
            bookingProcess(idPerson, idMovie);
        } catch (InputMismatchException | AllPlacesBookedException | InconvertibleIdException e) {
            e.printStackTrace();
        }
    }

    public void returnTicket(String idPerson) {
        try {
            checkUUID(idPerson);
            checkTickets(ticketRepository.getAllUserTickets(idPerson));
            System.out.print("Enter ticket number you want return: ");
            scanner = new Scanner(System.in);
            Integer ticketNumber = scanner.nextInt();
            System.out.println(ticketRepository.updateForReturn(ticketNumber, idPerson));
            log.info("Ticket number " + ticketNumber + " was successfully returned");
        } catch (InputMismatchException | InconvertibleIdException | PersonWithoutTickets e) {
            e.printStackTrace();
            System.err.println("Incorrect number");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bookTicket(String idPerson, Optional <Movie> movie) {
        if (movie.isPresent()) {
            Movie movieChoose = movie.get();
            String idMovie = movieChoose.getId();
            System.out.println("You choose movie: " + movieChoose.getMovieName() + ". Seance date: " +
                    movieChoose.getDate() + ". Seance time: " + movieChoose.getTime());
            log.info("Chosen movie: " + movieChoose.getMovieName() + ". Seance date: " +
                    movieChoose.getDate() + ". Seance time: " + movieChoose.getTime());
            try {
                checkUUID(idPerson);
                bookingProcess(idPerson, idMovie);
            } catch (InputMismatchException | AllPlacesBookedException | InconvertibleIdException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Incorrect movie meaning");
        }
    }

    @Override
    public void getAllUserTickets(String id) {
        try {
            checkUUID(id);
            checkTickets(ticketRepository.getAllUserTickets(id));
        } catch (PersonWithoutTickets | InconvertibleIdException e) {
            e.printStackTrace();
            log.info("Person don't have any tickets");
        } catch (RuntimeException e) {
            System.err.println("Connection fail");
        }
    }

    private void bookingProcess(String idPerson, String idMovie) throws InputMismatchException, AllPlacesBookedException, InconvertibleIdException {
        List<Ticket> ticketList = seeInformation(idMovie);
        int quantity = scanner.nextInt();
        while (quantity != 0 && quantity <= ticketList.size()) {
            quantity--;
            System.out.println("Enter raw: ");
            Integer raw = scanner.nextInt();
            System.out.println("Enter seat: ");
            Integer seat = scanner.nextInt();
            if (ticketRepository.bookTicket(idPerson, idMovie, raw, seat) != 0) {
                System.out.println("Your place is booked, thank you");
                ticketRepository.bookTicket(idPerson, idMovie, raw, seat);
                log.info("Movie ticket purchased (raw " + raw + ", seat " + seat + ")");
            } else System.out.println("You can't book this place");
        }
        System.out.println("Quantity of empty seats:  " + ticketList.size());
    }

    private List<Ticket> seeInformation(String idMovie) throws AllPlacesBookedException {
        List <Ticket> ticketList = ticketRepository.getAllPossibleFilmTickets(idMovie);
        checkList(ticketList);
        log.info("Menu of tickets is opened");
        System.out.println("Empty seats for your movie:");
        ticketList.forEach(System.out::println);
        System.out.println("How much tickets do you want to buy? Enter ticket quantity: ");
        System.out.println("Quantity of empty seats: " + ticketList.size());
        return ticketList;
    }

    private void checkUUID(String id) throws InconvertibleIdException{
        if (!PATTERN_UUID.matcher(id).matches()) throw new InconvertibleIdException("Incorrect id");
    }

    private void checkList(List <Ticket> ticketList) throws AllPlacesBookedException {
        if (ticketList.isEmpty()) throw new AllPlacesBookedException("Excuse, there are no empty seats for this seance");
    }

    private void checkTickets(List <Ticket> ticketList) throws PersonWithoutTickets {
        if (ticketList.isEmpty()) throw new PersonWithoutTickets("There are no tickets");
    }

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
}
