package com.cinema.repository;

import com.cinema.model.Movie;
import com.cinema.model.Person;
import com.cinema.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MovieRepositoryImpl implements MovieRepository{

    @Override
    public boolean createMovie(Movie movie) {
        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO movie (id, movieName, date, time, age) VALUES (?, ?, ?, ?, ?)"); //Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, movie.getId().toString());
            preparedStatement.setString(2, movie.getMovieName());
            preparedStatement.setString(3, movie.getDate().toString());
            preparedStatement.setString(4, movie.getTime().toString());
            preparedStatement.setInt(5, movie.getAge());
            preparedStatement.execute();
//            ResultSet res = preparedStatement.getGeneratedKeys();
//            if(res.next()){
//                UUID id = (UUID) res.getObject(1);
//                System.out.println(id);
//            } else {
//                System.out.println("no");
//            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }



    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM movie");
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
            //logirovanie
            throw new RuntimeException(e);
        }
    }

    @Override
    public Movie getMovieById(String id) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM movie WHERE id = ?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
                String title = resultSet.getString("movieName");
                LocalDate date = resultSet.getObject("date", LocalDate.class);
                LocalTime time = resultSet.getObject("time", LocalTime.class);
                Integer age = resultSet.getInt("age");
                return new Movie(id, title, date, time, age);
        } catch (SQLException e) {
            //logirovanie
            System.out.println("no");
            throw new RuntimeException(e);
        }
    }

    public List <Movie> getMovieByName(String name) {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT id, movieName, date, time, age " +
                    "FROM movie " +
                    "WHERE movieName = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                Integer age = resultSet.getInt("age");
                Movie movie = new Movie(id, name, LocalDate.parse(date), LocalTime.parse(time), age);
                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            //logirovanie
            System.out.println("no");
            throw new RuntimeException(e);
        }
    }

    public List <Movie> getMovieByDate(LocalDate date, String name) {
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT id, movieName, date, time, age " +
                    "FROM movie " +
                    "WHERE movieName = ? AND date = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, date.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String time = resultSet.getString("time");
                Integer age = resultSet.getInt("age");
                Movie movie = new Movie(id, name, date, LocalTime.parse(time), age);
                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            //logirovanie
            System.out.println("no");
            throw new RuntimeException(e);
        }
    }

    public Optional <Movie> getMovieByTime(LocalTime time, LocalDate date, String name) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT id, movieName, date, time, age " +
                    "FROM movie " +
                    "WHERE movieName = ? AND date = ? AND time = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, date.toString());
            preparedStatement.setString(3, time.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                Integer age = resultSet.getInt("age");
//            System.out.println(new Movie(id, name, date, time, age));
            return Optional.of(new Movie(id, name, date, time, age));}
        } catch (SQLException e) {
            //logirovanie
            e.printStackTrace();
            System.out.println("no");
//            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Movie updateById(String id, Movie movie) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE movie " +
                    "SET movieName = ?, date = ?, time = ?, age = ? WHERE id = ?");
            preparedStatement.setString(1, movie.getMovieName());
            preparedStatement.setString(2, movie.getDate().toString());
            preparedStatement.setString(3, movie.getTime().toString());
            preparedStatement.setInt(4, movie.getAge());
            preparedStatement.setString(5, id);
            preparedStatement.executeUpdate();
            System.out.println("Movie was successfully updated");
            return movie;
        } catch (SQLException e) {
            //logirovanie
        }
        System.out.println("Attention: movie was not deleted");
        return null;
    }

    @Override
    public boolean deleteMovieById(UUID id) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM movie WHERE id = ?");
            preparedStatement.setString(1, id.toString());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            //logirovanie
        }
        return false;
    }
}
