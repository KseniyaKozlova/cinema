package com.cinema.service;

import com.cinema.exception.*;
import com.cinema.model.Movie;
import com.cinema.repository.MovieRepository;
import com.cinema.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    private final TicketRepository ticketRepository;

    private Scanner scanner = new Scanner(System.in);
    private static final Pattern PATTERN_UUID =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");


    @Override
    public void create() {
        try {
            scanner = new Scanner(System.in);
            System.out.print("Enter title: ");
            String movieTitle = scanner.nextLine();
            System.out.print("Enter date: ");
            LocalDate date = enterDate();
            checkDate(date);
            System.out.print("Enter time (pattern: __:__): ");
            LocalTime time = LocalTime.parse(scanner.next(), TIME_FORMATTER);
            System.out.print("Enter age: ");
            int age = scanner.nextInt();
            checkAge(age);

            Movie movie = new Movie(movieTitle, date, time, age);
            if (movieRepository.createMovie(movie)) {
                System.out.println("New movie in cinema: " + movie);
                ticketRepository.createTicket(movie.getTickets());
                log.info("Movie " + movie.getMovieName() + " was added");
            } else System.out.println("Can't create this movie");

        } catch (InputMismatchException | DateTimeException | WrongAgeException | WrongDateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllMovies() {
        try {
            System.out.println("Our films: ");
            movieRepository.getAllMovies().forEach(System.out::println);
        } catch (NoMovies e) {
            e.printStackTrace();
            System.out.println("Try later");
        }
    }

    @Override
    public void updateTitle() {
        Optional <Movie> movie1 = getMovieById();
        if (movie1.isPresent()) {
            Movie movie = movie1.get();
            System.out.println("Enter new movie title: ");
            String title = scanner.nextLine();
            Optional <Movie> movieNew = movieRepository.updateById(movie.getId(), title, movie.getDate(), movie.getTime(), movie.getAge());
            checkNewMovie(movieNew);
            log.info("Movie title " + movie.getMovieName() + " has been modified to " + title);
        } else System.out.println("Movie doesn't exist");
    }

    @Override
    public void updateDate() {
        try {
            Optional <Movie> movie1 = getMovieById();
            if (movie1.isPresent()) {
                Movie movie = movie1.get();
                System.out.println("Enter new movie date: ");
                LocalDate date = enterDate();
                checkDate(date);
                Optional <Movie> movieNew = movieRepository.updateById(movie.getId(), movie.getMovieName(), date, movie.getTime(), movie.getAge());
                checkNewMovie(movieNew);
                log.info("Movie date " + movie.getDate() + " has been modified to " + date);
            } else System.out.println("Movie doesn't exist");
        } catch (InputMismatchException | DateTimeException | WrongDateException e) {
            System.out.println("Incorrect date");
        }
    }

    @Override
    public void updateTime() {
        try {
            Optional <Movie> movie1 = getMovieById();
            if (movie1.isPresent()) {
                Movie movie = movie1.get();
                System.out.print("Enter new movie time (pattern: __:__): ");
                LocalTime time = LocalTime.parse(scanner.next(), TIME_FORMATTER);
                Optional <Movie> movieNew = movieRepository.updateById(movie.getId(), movie.getMovieName(), movie.getDate(), time, movie.getAge());
                checkNewMovie(movieNew);
                log.info("Movie time " + movie.getTime() + " has been modified to " + time);
            } else System.out.println("Movie doesn't exist");
        } catch (InputMismatchException | DateTimeException e) {
            System.out.println("Incorrect time");
        }
    }

    @Override
    public void updateAge() {
        try {
            Optional <Movie> movie1 = getMovieById();
            if (movie1.isPresent()) {
                Movie movie = movie1.get();
                System.out.print("Enter new movie age (from 0 to 18): ");
                int age = scanner.nextInt();
                checkAge(age);
                Optional <Movie> movieNew = movieRepository.updateById(movie.getId(), movie.getMovieName(), movie.getDate(), movie.getTime(), age);
                checkNewMovie(movieNew);
                log.info("Movie age " + movie.getAge() + " has been modified to " + age);
            } else System.out.println("Movie doesn't exist");
        } catch (InputMismatchException | WrongAgeException e) {
            System.out.println("Incorrect age");
        }
    }

    @Override
    public Optional <Movie> sortByDate() {
        try {
            List <Movie> movies = movieRepository.getAllMovies();
            System.out.println("What film would you like to see?");
            movies.forEach(System.out::println);
            System.out.print("Enter movie title: ");
            scanner = new Scanner(System.in);
            String title = scanner.nextLine();

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
                LocalTime time = LocalTime.parse(scanner.next(), TIME_FORMATTER);
                List<Movie> movieByTime = filtMoviesDate.stream()
                        .filter(movie1 -> movie1.getTime().equals(time))
                        .collect(Collectors.toList());
                checkList(movieByTime);
                return movieByTime.stream().findFirst();
        } catch (InputMismatchException | DateTimeException | NoSuchMovieException | NoMovies e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void deleteMovieById() {
        getAllMovies();
        Optional<Movie> movie = getMovieById();
        if (movie.isPresent()) {
            if (movieRepository.deleteMovieById(movie.get().getId())) {
                System.out.println("Movie deleted");
                log.info("Movie " + movie.get().getMovieName() + " was deleted");
            } else System.out.println("Movie can't be deleted");
        } else System.out.println("Movie doesn't exist");
    }

    private Optional <Movie> getMovieById() {
        scanner = new Scanner(System.in);
        System.out.println("Enter UUID of movie: ");
        String idMovie = scanner.nextLine();
        if (PATTERN_UUID.matcher(idMovie).matches()) {
            return movieRepository.getMovieById(idMovie);
        } else {
            System.out.println("Incorrect id");
            return Optional.empty();
        }
    }

    private LocalDate enterDate() throws InputMismatchException {
        System.out.print("Enter month: ");
        int month = scanner.nextInt();
        System.out.print("Enter day: ");
        int day = scanner.nextInt();
        return LocalDate.of(2023, month, day);
    }

    private void checkNewMovie(Optional<Movie> movieNew) {
        if (movieNew.isPresent()) {
            System.out.println("Movie was updated. New " + movieNew.get());
        } else System.out.println("Movie wasn't updated");
    }

    private void checkAge(int age) throws WrongAgeException{
        if (age > 18 || age < 0) {
            throw new WrongAgeException("Incorrect age limit");
        }
    }

    private void checkDate(LocalDate date) throws WrongDateException{
        if (date.isBefore(LocalDate.now())) {
            throw new WrongDateException("Proposed date should be later");
        }
    }

    private void checkList(List <Movie> movieList) throws NoSuchMovieException {
        if (movieList.isEmpty()) throw new NoSuchMovieException("Excuse, we don't have this seance");
    }

    public MovieServiceImpl(MovieRepository movieRepository, TicketRepository ticketRepository) {
        this.movieRepository = movieRepository;
        this.ticketRepository = ticketRepository;
    }
}
