package com.cinema.controller.extending;

import com.cinema.model.Person;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class ManagerController extends PersonController {

    private Scanner scanner = new Scanner(System.in);

    public void managerMenu() {
        String step;
        do {
            log.info(person.getRole() + " " + person.getName() + " look through main menu");
            System.out.println("""
                1 - what's on;
                2 - buy ticket as user;
                3 - return ticket as user;
                4 - my tickets as user;
                5 - edit movies;
                6 - see user tickets;
                7 - return user ticket;
                8 - buy ticket for user;
                0 - return to start menu;
                """);
            step = scanner.nextLine();
            seeManagerMenu(step);
            log.info("Manager" + person.getName() + "come");
        } while (!step.equals("0"));
    }

    protected void seeManagerMenu(String step) {
        switch (step) {
            case "1", "2", "3", "4", "0":
                super.seeUserMenu(step, person.getId());
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
                        0 - return to manager menu;
                        """);
                step = scanner.nextLine();
                chooseOfManagerMovie(step);
                break;
            case "6":
                String idPerson = getIdPerson();
                log.info(person.getRole() + " looks through tickets of person with id " + idPerson);
                ticketService.getAllUserTickets(idPerson);
                break;
            case "7":
                String idPerson1 = getIdPerson();
                log.info(person.getRole() + " choose return ticket of person with id  " + idPerson1);
                ticketService.returnTicket(idPerson1);
                break;
            case "8":
                String idPerson2 = getIdPerson();
                log.info(person.getRole() + " choose buy ticket for person with id  " + idPerson2);
                System.out.println("Choose movie: ");
                movieService.getAllMovies();
                ticketService.bookTicket(idPerson2);
                break;
            default:
                System.out.println("incorrect meaning");
        }
    }

    private void chooseOfManagerMovie(String step) {
        switch (step) {
            case "1" -> movieService.updateTitle();
            case "2" -> movieService.updateDate();
            case "3" -> movieService.updateTime();
            case "4" -> movieService.updateAge();
            case "0" -> managerMenu();
            default -> System.out.println("try again");
        }
    }

    private String getIdPerson() {
        System.out.println("Choose user: ");
        personService.getAllPersons().forEach(System.out::println);
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public ManagerController(Person person) {
        super(person);
    }
}