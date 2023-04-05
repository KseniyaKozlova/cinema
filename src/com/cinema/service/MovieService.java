package com.cinema.service;

import com.cinema.model.Movie;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieService {

    public boolean create();

    void getAllMovies();

//    void seeFilms();

    Movie getMovieById();

    Optional <Movie> sortByDate();

    Movie chooseMovie();

    Movie updateById();

    boolean deleteMovieById(String id);
}
