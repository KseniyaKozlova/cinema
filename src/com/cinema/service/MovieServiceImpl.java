package com.cinema.service;

import com.cinema.model.Movie;
import com.cinema.model.Person;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.MovieRepositoryImpl;
import com.cinema.repository.TicketRepository;
import com.cinema.repository.TicketRepositoryImpl;
import com.cinema.util.exception.NoSuchMovieException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository = new MovieRepositoryImpl();
    private final TicketService ticketService = new TicketServiceImpl();
    private Scanner scanner = new Scanner(System.in);
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    Movie movie = new Movie();

    @Override
    public boolean create() {

        System.out.println("enter title: ");
        String movieTitle = scanner.nextLine();
        System.out.println("enter date: ");
        String date = scanner.next();
        System.out.println("enter time ");
        String time = scanner.next();
        System.out.println("enter age: ");
        Integer age = scanner.nextInt();

        Movie movie = new Movie(movieTitle, LocalDate.parse(date, dateFormatter), LocalTime.parse(time, timeFormatter), age);
        boolean isGet = movieRepository.createMovie(movie);

        TicketRepository ticketRepository = new TicketRepositoryImpl();
        ticketRepository.createTicket(movie.getTickets());

        return isGet;
    }

    @Override
    public void getAllMovies() {
        System.out.println("Our films: ");
        movieRepository.getAllMovies().forEach(System.out::println);
    }

    @Override
    public Movie getMovieById() {

        System.out.println("enter UUID of movie: ");
        Pattern pattern =
                Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
//        if (pattern.matcher(id).matches()) {
            String id = scanner.next(pattern);
//        }
        return movieRepository.getMovieById(id);
    }


    @Override
    public Movie updateById() {
        System.out.println("Enter id of film you want to edit: ");
        String id = scanner.next();
        scanner = new Scanner(System.in);
        System.out.println("Enter new name: ");
        String movieTitle = scanner.nextLine();
        System.out.println("Enter new date: ");
        LocalDate date = LocalDate.parse(scanner.next(), dateFormatter);
        System.out.println("Enter new time: ");
        LocalTime time = LocalTime.parse(scanner.next(), timeFormatter);
        System.out.println("Enter new age: ");
        Integer age = scanner.nextInt();

        Movie movie1 = new Movie(id, movieTitle, date, time, age);

        return movieRepository.updateById(id, movie1);
    }

//    public void seeFilms() {
//        System.out.println(getAllMovies());
//    }

    public Optional <Movie> sortByDate() {
        List <Movie> movies = movieRepository.getAllMovies();
        System.out.println("What film would you like to see?");
        movies.forEach(System.out::println);
        System.out.print("Enter movie title: ");
        scanner = new Scanner(System.in);
        String title = scanner.nextLine();
        try {
            List <Movie> filtMovies = movies.stream()
                    .filter(movie1 -> movie1.getMovieName().equals(title))
                    .toList();
            checkList(filtMovies);
            filtMovies.forEach(System.out::println);
            if (filtMovies.size() == 1) {
                System.out.println("Only one seance");
                return filtMovies.stream().findFirst();
            }
            System.out.print("Please, choose date of film. ");
            LocalDate date = enterDate();
            List <Movie> filtMoviesDate =filtMovies.stream()
                    .filter(movie1 -> movie1.getDate().equals(date))
                    .collect(Collectors.toList());
            checkList(filtMoviesDate);
            if (filtMoviesDate.size() == 1) {
                System.out.println("Only one seance");
                return filtMoviesDate.stream().findFirst();
            }
                System.out.print("What time is more convenient to you? ");
                filtMoviesDate.forEach(System.out::println);
                System.out.print("Enter time (pattern: __:__): ");
                LocalTime time = LocalTime.parse(scanner.next(), timeFormatter);
                List<Movie> movieByTime = filtMoviesDate.stream()
                        .filter(movie1 -> movie1.getTime().equals(time))
                        .collect(Collectors.toList());
                checkList(movieByTime);
                return movieByTime.stream().findFirst();
        } catch (InputMismatchException | DateTimeException | NoSuchMovieException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void checkList(List <Movie> movieList) throws NoSuchMovieException {
        if (movieList.isEmpty()) throw new NoSuchMovieException("Excuse, we don't have this seance");
    }

    private LocalDate enterDate() throws InputMismatchException {
        System.out.print("Enter month: ");
        Integer month = scanner.nextInt();
        System.out.print("Enter day: ");
        Integer day = scanner.nextInt();
        return LocalDate.of(2023, month, day);
    }

    private boolean checkUUID(String id) {
        Pattern pattern =
                Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
        Matcher m = pattern.matcher(id);
        return m.matches();
    }

    public Movie chooseMovie() {
        System.out.println("What film would you like to see?");
        scanner = new Scanner(System.in);
        System.out.print("Enter movie title: ");
        String name = scanner.nextLine();
        if (checkUUID(name)) {
        System.out.println("Please, choose date of film: ");
        movieRepository.getMovieByName(name).forEach(System.out::println);}
        System.out.print("Enter date: ");
        LocalDate date = LocalDate.parse(scanner.next(), dateFormatter);
        System.out.println("What time is more convenient to you? ");
        movieRepository.getMovieByDate(date, name).forEach(System.out::println);
        System.out.print("Enter time: ");
        LocalTime time = LocalTime.parse(scanner.next(), timeFormatter);
        return movieRepository.getMovieByTime(time, date, name).orElse(new Movie());
    }

    @Override
    public boolean deleteMovieById(String id) {
        return movieRepository.deleteMovieById(UUID.fromString(id));
    }

}
