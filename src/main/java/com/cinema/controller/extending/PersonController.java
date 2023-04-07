package com.cinema.controller.extending;

import com.cinema.model.Movie;
import com.cinema.model.Person;
import com.cinema.repository.*;
import com.cinema.service.*;
import com.cinema.util.Role;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Scanner;

@Slf4j
public class PersonController {

    final PersonRepository personRepository = new PersonRepositoryImpl();
    final MovieRepository movieRepository = new MovieRepositoryImpl();
    final TicketRepository ticketRepository = new TicketRepositoryImpl();
    final TicketService ticketService = new TicketServiceImpl(ticketRepository);
    final MovieService movieService = new MovieServiceImpl(movieRepository, ticketRepository);
    final PersonService personService = new PersonServiceImpl(personRepository);
    protected Person person;
    private final Scanner scanner = new Scanner(System.in);
    private String step;

    public void startMenu() {
        do {
            System.out.println("""
                    1 - join us;
                    2 - enter;
                    0 - exit;
                    """);
            step = scanner.nextLine();
            haveResult(step);
        } while (!step.equals("0"));
    }

    private void haveResult(String step) {
        switch (step) {
            case "1" -> {
                System.out.println("Registration form: ");
                person = personService.create();
                userMenu(person.getId());
            }
            case "2" -> {
                System.out.println("Log in with");
                checkPerson(personService.come());
            }
            case "0" -> {
                System.out.println("Out");
                log.info("Program stopped");
                System.exit(0);
            }
            default -> System.out.println("Incorrect meaning");
        }
    }

    protected void seeUserMenu(String step, String id) {
        switch (step) {
            case "1" -> {
                movieService.getAllMovies();
                log.info(person.getRole() + " " + person.getName() + " see poster of the movie theatre");
            }
            case "2" -> {
                Optional<Movie> movie = movieService.sortByDate();
                ticketService.bookTicket(id, movie);
            }
            case "3" -> ticketService.returnTicket(id);
            case "4" -> ticketService.getAllUserTickets(id);
            case "0" -> {
                log.info(person.getRole() + " " + person.getName() + " end session");
                startMenu();
            }
        }
    }

    private void userMenu(String id) {
        do {
            log.info(person.getRole() + " " + person.getName() + " look through main menu");
            System.out.println("""
                    1 - what's on;
                    2 - buy ticket;
                    3 - return ticket;
                    4 - my tickets;
                    0 - return to start menu;
                    """);
            step = scanner.nextLine();
            seeUserMenu(step, id);
        } while (!step.equals("0"));
    }

    private void chooseMenu(Person person) {
        if (Role.MANAGER.equals(person.getRole())) {
            System.out.println("Manager menu: ");
            log.info("Manager " + person.getName() + " come");
            ManagerController managerController = new ManagerController(person);
            managerController.managerMenu();
        } else if (Role.ADMINISTRATOR.equals(person.getRole())) {
            System.out.println("Administrator menu: ");
            log.info("Administrator " + person.getName() + " come");
            AdminController adminController = new AdminController(person);
            adminController.administratorMenu();
        } else {
            System.out.println("User menu: ");
            log.info("User " + person.getName() + " come");
            userMenu(person.getId());
        }
    }

    private void checkPerson(Optional <Person> personOptional) {
        if (personOptional.isPresent()) {
            person = personOptional.get();
            chooseMenu(person);
        } else {
            System.out.println("You're not authorized");
            startMenu();
        }
    }

    public PersonController(Person person) {
        this.person = person;
    }

    public PersonController() {
    }

}
