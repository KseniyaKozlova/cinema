package com.cinema.controller.xtends;

import com.cinema.model.Movie;
import com.cinema.model.Person;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class ManagerController extends PersonController {

    public ManagerController(Person person, Scanner scanner, String step) {
        super(person, scanner, step);
        this.scanner = scanner;
        this.step = step;
    }

    public void managerMenu() {
        do {
            System.out.println("""
                1 - what's on;
                2 - edit movies;
                3 - return user ticket;
                4 - buy ticket for user;
                5 - buy ticket for me;
                6 - return my ticket;
                7 - see my tickets;
                8 - see user tickets;
                0 - return to start menu;
                """);
            step = scanner.nextLine();
            seeManagerMenu(step, person.getId());
            userMenu(person.getId());
        } while (!step.equals("0"));
    }

    @Override
    public void userMenu(UUID id) {
        super.userMenu(id);
    }

    private void seeManagerMenu(String step, UUID id) {
        switch (step) {
            case "1":
                movieService.getAllMovies();
                break;
            case "2":
                System.out.println("Choose film: ");
//                list of films
//                movieService.getAllMovies().forEach(System.out::println);
//                enter film id
                movieService.updateById();
//                updatemethod by id for movie
            case "3":
                System.out.println("Choose user: ");
//                list of persons
//                enter person id/ not admin!
//                have list of tickets by person's id
//                enter ticket id
//                delete ticket by id????/ delete person's id from ticket + delete from list tickets
            case "4":
//                System.out.println("Choose user");
//                personService.getAllPersons();
//                System.out.println("Enter user id you need: ");
//                UUID id = UUID.fromString(scanner.nextLine());
//                System.out.println("Our films: ");
//                movieService.getAllMovies().forEach(System.out::println);
//                Movie movie = movieService.chooseMovie();
//                System.out.println("You choose movie: " + movie);
//                ticketService.getAllPossibleFilmTickets(movie.getId()).forEach(System.out::println);
//                System.out.println("How much tickets do you want to buy for this user? Enter ticket quantity: ");
//                int quantity = scanner.nextInt();
////        System.out.println("Select seat");
//                ticketService.bookTicket(id, movie.getId(), quantity);
            case "5":
                Optional<Movie> movie = movieService.sortByDate();
                ticketService.bookTicket(id, movie);
                break;
            case "6":

            case "0":
                startMenu();
            default:
                System.out.println("incorrect meaning");
        }
    }

}
