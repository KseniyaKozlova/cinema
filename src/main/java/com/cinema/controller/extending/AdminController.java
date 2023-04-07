package com.cinema.controller.extending;

import com.cinema.model.Person;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class AdminController extends ManagerController {

    private final Scanner scanner = new Scanner(System.in);

    public void administratorMenu () {
        String step;
        do {
            log.info("Administrator " + person.getName() + " look through main menu");
            System.out.println("""
                    1 - what's on;
                    2 - buy ticket as user;
                    3 - return ticket as user;
                    4 - my tickets as user;
                    5 - edit movies;
                    6 - see user tickets;
                    7 - return user ticket;
                    8 - buy ticket for user;
                    9 - add/delete movie;
                    10 - correct users;
                    0 - return to start menu;
                    """);
            step = scanner.nextLine();
            seeAdministratorMenu(step);
        } while (!step.equals("0"));
    }

    private void seeAdministratorMenu(String step) {
        switch (step) {
            case "1", "2", "3", "4", "6", "7", "8", "0":
                super.seeManagerMenu(step);
                break;
            case "5":
                System.out.println("Movies: ");
                movieService.getAllMovies();
                log.info(person.getRole() + " " + person.getName() + " choose movie menu");
                System.out.println("""
                        1 - edit movie title;
                        2 - edit movie date;
                        3 - edit movie time;
                        4 - edit movie age;
                        0 - return to administrator menu;
                        """);
                step = scanner.nextLine();
                chooseOfMovie(step);
                break;
            case "9":
                log.info("Administrator " + person.getName() + " choose movie menu");
                System.out.println("""
                        1 - add movie;
                        2 - delete movie;
                        0 - return to administrator menu;
                        """);
                step = scanner.nextLine();
                chooseOfAdminMovie(step);
                break;
            case "10":
                log.info("Administrator " + person.getName() + " choose person menu");
                System.out.println("""
                        1 - change access level;
                        2 - delete user;
                        0 - return to administrator menu;
                        """);
                step = scanner.nextLine();
                chooseOfAdminUser(step);
                break;
            default:
                System.out.println("Incorrect meaning");
                break;
        }
    }

    private void chooseOfMovie(String step) {
        switch (step) {
            case "1" -> movieService.updateTitle();
            case "2" -> movieService.updateDate();
            case "3" -> movieService.updateTime();
            case "4" -> movieService.updateAge();
            case "0" -> administratorMenu();
            default -> System.out.println("try again");
        }
    }

    private void chooseOfAdminMovie(String step) {
        switch (step) {
            case "1" -> movieService.create();
            case "2" -> movieService.deleteMovieById();
            case "3" -> administratorMenu();
            default -> System.out.println("Incorrect attempt");
        }
    }

    private void chooseOfAdminUser(String step) {
        switch (step) {
            case "1" -> personService.updatePersonById();
            case "2" -> personService.deletePersonById();
            case "3" -> administratorMenu();
            default -> System.out.println("Incorrect attempt");
        }
    }

    public AdminController(Person person) {
        super(person);
    }
}
