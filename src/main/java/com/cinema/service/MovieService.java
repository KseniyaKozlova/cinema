package com.cinema.service;

import com.cinema.model.Movie;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieService {

    /**
     * Create new seance (movie)
     */
    void create();

    /**
     * See all movies
     */
    void getAllMovies();

//    void seeFilms();

//    Movie getMovieById();

    /**
     * Choose movie process for user
     */
    Optional <Movie> sortByDate();

    /**
     * Correct title of specified film
     */
    void updateTitle();

    /**
     * Correct date of specified film
     */
    void updateDate();

    /**
     * Correct time of specified film
     */
    void updateTime();

    /**
     * Correct age of specified film
     */
    void updateAge();

    /**
     * Delete specified film by input UUID
     */
    void deleteMovieById();
}
