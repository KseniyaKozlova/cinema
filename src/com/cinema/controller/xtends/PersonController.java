package com.cinema.controller.xtends;

import com.cinema.model.Movie;
import com.cinema.model.Person;
import com.cinema.repository.PersonRepository;
import com.cinema.repository.PersonRepositoryImpl;
import com.cinema.service.*;
import com.cinema.util.Role;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class PersonController {

    final PersonRepository personRepository = new PersonRepositoryImpl();
    final TicketService ticketService = new TicketServiceImpl();
    MovieService movieService = new MovieServiceImpl();
    PersonService personService = new PersonServiceImpl(personRepository, ticketService);
    Person person;
    Scanner scanner = new Scanner(System.in);
    String step;

    public PersonController(Person person, Scanner scanner, String step) {
        this.person = person;
        this.scanner = scanner;
        this.step = step;
    }

    public PersonController() {
    }

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
                System.out.println("registration form: ");
                person = personService.create();
                userMenu(person.getId());
            }
            case "2" -> {
                System.out.println("Log in with: ");
                checkPerson(personService.come());
            }
            case "0" -> {
                System.out.println("out");
                System.exit(0);
            }
            default -> System.out.println("incorrect meaning");
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

    private void chooseMenu(Person person) {
        if (Role.MANAGER.equals(person.getRole())) {
            System.out.println("Administrator menu: ");
            ManagerController managerController = new ManagerController(person, scanner, step);
            managerController.managerMenu();
        } else if (Role.ADMINISTRATOR.equals(person.getRole())) {
            System.out.println("Manager menu: ");
//            seeAdministratorMenu();
        } else {
            System.out.println("User menu: ");
            userMenu(person.getId());
        }
    }

    public void userMenu(UUID id) {
        do {
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

    private void seeUserMenu(String step, UUID id) {
        switch (step) {
            case "1":
                movieService.getAllMovies();
                break;
            case "2":
                Optional <Movie> movie = movieService.sortByDate();
                ticketService.bookTicket(id, movie);
                break;
            case "3":
                ticketService.returnTicket(id);
                break;
            case "4":
                ticketService.getAllUserTickets(id);
                break;
            case "0":
                startMenu();
                break;
        }
    }
}
