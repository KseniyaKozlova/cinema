package com.cinema.service;

import com.cinema.model.Movie;
import com.cinema.model.Person;
import com.cinema.model.Ticket;
import com.cinema.repository.TicketRepository;
import com.cinema.repository.TicketRepositoryImpl;
import com.cinema.util.exception.AllPlacesBookedException;
import com.cinema.util.exception.NoSuchMovieException;
import com.cinema.util.exception.PersonWithoutTickets;

import java.util.*;

public class TicketServiceImpl implements TicketService{

    private Scanner scanner = new Scanner(System.in);
    TicketRepository ticketRepository = new TicketRepositoryImpl();
    Person person = new Person();

    private void buyTicket() {
        System.out.println("How much tickets do you want to buy? Enter ticket quantity: ");
            int quantity = scanner.nextInt();
//        List <Ticket> ticketList = ticketRepository.getAllTickets();
//        ticketList.stream()
    }

    public void returnTicket(UUID idPerson) {
        List <Ticket> ticketList = ticketRepository.getAllUserTickets(idPerson);
        try {
            if (ticketList.isEmpty()) {
                throw new PersonWithoutTickets("There are no tickets");
            }
            System.out.print("Enter ticket number you want return: ");
            Integer ticketNumber = scanner.nextInt();
            System.out.println(ticketRepository.updateForReturn(ticketNumber, idPerson));
        } catch (PersonWithoutTickets | InputMismatchException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void bookTicket(UUID idPerson, Optional <Movie> movie) {
        if (movie.isPresent()) {
            Movie movieChoose = movie.get();
            String idMovie = movieChoose.getId();
            System.out.println("You choose movie: " + movieChoose.getMovieName() + ". Seance date: " +
                    movieChoose.getDate() + ". Seance time: " + movieChoose.getTime());
            try {
                List <Ticket> ticketList = ticketRepository.getAllPossibleFilmTickets(idMovie);
                checkList(ticketList);
                System.out.println("Empty seats for your movie:");
                ticketList.forEach(System.out::println);
                System.out.println("How much tickets do you want to buy? Enter ticket quantity: ");
                System.out.println("Quantity of empty seats: " + ticketList.size());
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
                } else System.out.println("Place was booked earlier");
            }
                System.out.println("Quantity of empty seats:  " + ticketList.size());
            } catch (InputMismatchException | AllPlacesBookedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Incorrect movie meaning");
        }
    }

    private void checkList(List <Ticket> ticketList) throws AllPlacesBookedException {
        if (ticketList.isEmpty()) throw new AllPlacesBookedException("Excuse, there are no empty seats for this seance");
    }

    @Override
    public void getAllPossibleFilmTickets(String idMovie) {
        try {
            checkList(ticketRepository.getAllPossibleFilmTickets(idMovie));
            ticketRepository.getAllPossibleFilmTickets(idMovie).forEach(System.out::println);
        } catch (AllPlacesBookedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllUserTickets(UUID id) {
        List<Ticket> allUserTickets = ticketRepository.getAllUserTickets(id);
        if ( allUserTickets.isEmpty()) {
            System.out.println("There are no tickets");
        } else {
            System.out.println("Your tickets: ");
            allUserTickets.forEach(System.out::println);
        }
    }

    //    public boolean createTicket() {
//
//    }
}
