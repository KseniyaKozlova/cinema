package com.cinema.controller.xtends;

import com.cinema.model.Movie;

import java.util.UUID;

//public class AdminController extends ManagerController {
//
//    public String administratorMenu() {
//        do {
//            System.out.println("""
//                    1 - what's on;
//                    2 - correct movies;
//                    3 - correct tickets;
//                    4 - correct users;
//                    0 - return to start menu;
//                    """);
//            return scanner.nextLine();
//        } while (!step.equals("0"));
//    }
//
//    private void seeAdministratorMenu() {
//        step = administratorMenu();
//        switch (step) {
//            case "1":
//                System.out.println("Films: ");
////                movieService.getAllMovies().forEach(System.out::println);
//                break;
////                list of films
//            case "2":
//                System.out.println("""
//                        1 - add movie;
//                        2 - edit movie;
//                        3 - delete movie;
//                        0 - return to administrator menu;
//                        """);
//                step = scanner.nextLine();
//                chooseOfAdminMovie(step);
//                break;
//            case "3":
//                System.out.println("""
//                        1 - add tickets; //???
//                        2 - buy ticket;
//                        3 - return ticket;
//                        0 - return to administrator menu;
//                        """);
//                if (scanner.nextLine().equals("1")) {
////                   addmoviemethod
//                } else if (scanner.nextLine().equals("2")) {
//                    System.out.println("Choose user");
//                    personService.getAllPersons();
//                    System.out.println("Enter user id you need: ");
//                    UUID id = UUID.fromString(scanner.nextLine());
//                    System.out.println("Our films: ");
////                    movieService.getAllMovies().forEach(System.out::println);
//                    Movie movie = movieService.chooseMovie();
//                    System.out.println("You choose movie: " + movie);
//                    ticketService.getAllPossibleFilmTickets(movie.getId());
//                    System.out.println("How much tickets do you want to buy for this user? Enter ticket quantity: ");
//                    int quantity = scanner.nextInt();
////        System.out.println("Select seat");
////                        ticketService.bookTicket(id, movie.getId(), quantity);
//                } else if (scanner.nextLine().equals("3")) {
//                    System.out.println("Choose user id: ");
////                    scanner.nextLine();
////                    ....
//                } else if (scanner.nextLine().equals("0")) {
//                    seeAdministratorMenu();
//                } else {
//                    System.out.println("try again");
////                    seeAdministratorMenu(person);????
//                }
//                break;
//            case "4":
//                System.out.println("""
//                        1 - edit user;
//                        2 - delete user;
//                        0 - return to administrator menu;
//                        """);
//                if (scanner.nextLine().equals("1")) {
////                    list of users
//                    personService.getAllPersons();
////                   updateusermethod
//                    personService.updatePersonById();
//                } else if (scanner.nextLine().equals("2")) {
////                   list of movies
//                    personService.getAllPersons();
////                    scanner.nextLine();
////                    personService.deletePersonById();1!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
////                   deletepersonmethod
//                } else if (scanner.nextLine().equals("0")) {
//                    seeAdministratorMenu();
//                } else {
//                    System.out.println("try again");
////                    seeAdministratorMenu(person);????
//                }
//                break;
//            case "0":
//                startMenu();
//                break;
//            default:
//                System.out.println("incorrect meaning");
//                break;
//        }
//    }
//
//    private void chooseOfAdminMovie(String step) {
//        switch (step) {
//            case "1" -> movieService.create();
//            case "2" -> {
////                   list of movies
//                movieService.getAllMovies();
////                    updatemoviemethod
//                movieService.updateById();
//            }
//            case "3" -> {
//                System.out.println("Choose movie id: ");
////                movieService.getAllMovies().forEach(System.out::println);
//                step = scanner.nextLine();
//                movieService.deleteMovieById(step);
//            }
////                    movieService.deleteMovieById();!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//            case "0" -> seeAdministratorMenu();
//            default -> System.out.println("try again");
//
////                    seeAdministratorMenu(person);????
//        }
//    }
//
//}
