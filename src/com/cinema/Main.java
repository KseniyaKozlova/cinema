package com.cinema;

import com.cinema.controller.xtends.*;

public class Main {


    public static void main(String[] args) {
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

//        Menu.start();
        PersonController personController = new PersonController();
        personController.startMenu();

//        PersonService personService = new PersonServiceImpl();
//        System.out.println(personService.come());
//        Scanner scanner = new Scanner(System.in);


//        System.out.println("Films: ");
////                list of films
//        movieService.getAllMovies().forEach(System.out::println);
//        movieService.seeFilms();
//                choose film by name
//                list of named films
//                film by date and time


//        Movie movie = new Movie("hbjkbhk", LocalDate.now(), LocalTime.now(), 18);
//        movie.createTickets();

//        TicketService ticketService = new TicketServiceImpl();
//        MovieService movieService = new MovieServiceImpl();
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/cinema", "root", "");
//            DatabaseMetaData metaData = connection.getMetaData();
//            System.out.println(metaData.supportsBatchUpdates());
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }

//        System.out.print("enter film title: ");
//        String title = scanner.nextLine();
//        System.out.print("enter date: ");
//        LocalDate date = LocalDate.parse(scanner.next(), dateFormatter);
//        System.out.print("enter time: ");
//        LocalTime time = LocalTime.parse(scanner.next(), timeFormatter);
//            System.out.println(movieService.sortByDate());


//        System.out.println(movieService.create());

//        CinemaHall cinemaHall = new CinemaHall();
//        cinemaHall.buyTickets(2,5);

//        try {
//            Movie movie = new Movie();
//            movie.createTickets();
//            movie.seeTickets();
//            movie.buyTickets(3436, 1, 1);
//
//            movie.seeTickets();
//            movie.buyTickets(6589, 1, 1);
//        } catch (NullPointerException e) {
//            System.out.println(e.getMessage());
//        }
    }
}
