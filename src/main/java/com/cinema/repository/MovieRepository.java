package com.cinema.repository;

import com.cinema.model.Movie;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieRepository {

    /**
     * Create new seance (movie) in DB
     */
    boolean createMovie(Movie movie);

    /**
     * Get all films from BD
     */
    List<Movie> getAllMovies();

    /**
     * Get certain film from DB
     */
    Optional <Movie> getMovieById(String id);

    /**
     * Change information about film in DB
     */
    Optional <Movie> updateById(String id, String name, LocalDate date, LocalTime time, int age);

    /**
     * Delete movie and seance from DB
     */
    boolean deleteMovieById(String id);
}
