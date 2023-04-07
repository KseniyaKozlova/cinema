package com.cinema.repository;

import com.cinema.model.Movie;
import com.cinema.util.ConnectionManager;
import com.cinema.exception.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieRepositoryImpl implements MovieRepository{

    @Override
    public boolean createMovie(Movie movie) {
        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO movie (id, movieName, date, time, age) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, movie.getId());
            preparedStatement.setString(2, movie.getMovieName());
            preparedStatement.setString(3, movie.getDate().toString());
            preparedStatement.setString(4, movie.getTime().toString());
            preparedStatement.setInt(5, movie.getAge());
            return preparedStatement.executeUpdate() != 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Movie> getAllMovies() throws NoMovies{
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM movie ORDER BY date ASC, time ASC");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String title = resultSet.getString("movieName");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                Integer age = resultSet.getInt("age");
                Movie movie = new Movie(id, title, LocalDate.parse(date), LocalTime.parse(time), age);
                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            throw new NoMovies("We don't have any seance");
        }
    }

    @Override
    public Optional <Movie> getMovieById(String id) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM movie WHERE id = ?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("movieName");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                LocalTime time = resultSet.getTime("time").toLocalTime();
                Integer age = resultSet.getInt("age");
                return Optional.of(new Movie(id, title, date, time, age));
            }
        } catch (SQLException e) {
            System.err.println("System error");
        }
        return Optional.empty();
    }

    @Override
    public Optional <Movie> updateById(String id, String name, LocalDate date, LocalTime time, int age) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE movie " +
                    "SET movieName = ?, date = ?, time = ?, age = ? WHERE id = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setDate(2, java.sql.Date.valueOf(date));
            preparedStatement.setTime(3, java.sql.Time.valueOf(time));
            preparedStatement.setInt(4, age);
            preparedStatement.setString(5, id);
            preparedStatement.executeUpdate();
            return Optional.of(new Movie(id, name, date, time, age));
        } catch (SQLException e) {
            System.err.println("System error");
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteMovieById(String id) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM movie WHERE id = ?");
            preparedStatement.setString(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("System error");
        }
        return false;
    }
}
