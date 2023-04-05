package com.cinema.repository;

import com.cinema.model.Movie;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieRepository {

    boolean createMovie(Movie movie);

    List<Movie> getAllMovies();

    Movie getMovieById(String id);

    List <Movie> getMovieByName(String name);

    List <Movie> getMovieByDate(LocalDate date, String name);

    Optional <Movie> getMovieByTime(LocalTime time, LocalDate date, String name);

    Movie updateById(String id, Movie movie);

    boolean deleteMovieById(UUID id);
}
