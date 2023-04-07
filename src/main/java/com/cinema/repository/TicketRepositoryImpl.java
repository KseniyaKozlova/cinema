package com.cinema.repository;

import com.cinema.model.Ticket;
import com.cinema.util.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class TicketRepositoryImpl implements TicketRepository {

    @Override
    public boolean createTicket(List<Ticket> tickets) {
        try (Connection connection = ConnectionManager.open()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO ticket (row, seat, price, movie_id) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            Iterator<Ticket> iterator = tickets.iterator();
            while (iterator.hasNext()) {
                Ticket ticket = iterator.next();
                preparedStatement.setInt(1, ticket.getRaw());
                preparedStatement.setInt(2, ticket.getSeat());
                preparedStatement.setInt(3, ticket.getPrice());
                preparedStatement.setString(4, ticket.getMovieId());
                preparedStatement.addBatch();
            }
            int[] numUpdates = preparedStatement.executeBatch();
            System.out.println(numUpdates.length);
            connection.commit();
            return true;
        } catch (SQLException b) {
            b.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Ticket> getAllUserTickets(String idPerson) throws RuntimeException {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ticket.id, ticket.row, ticket.seat, ticket.price, " +
                    "movie.id, movie.movieName, movie.date, movie.time, movie.age " +
                    "FROM ticket INNER JOIN movie " +
                    "ON ticket.movie_id = movie.id " +
                    "WHERE person_id  = ?");
            preparedStatement.setString(1, idPerson);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer idTicket = resultSet.getInt("ticket.id");
                Integer row = resultSet.getInt("ticket.row");
                Integer seat = resultSet.getInt("ticket.seat");
                Integer price = resultSet.getInt("ticket.price");
                String idFilm = resultSet.getString("movie.id");
                String movie = resultSet.getString("movie.movieName");
                LocalDate date = LocalDate.parse(resultSet.getString("movie.date"));
                LocalTime time = LocalTime.parse(resultSet.getString("movie.time"));
//                Integer age = resultSet.getInt("movie.age");
                Ticket ticket = new Ticket(idTicket, idPerson, idFilm, row, seat, price);
                tickets.add(ticket);
                System.out.println(ticket + " on film: " + movie + " at time " + date + " " + time);
            }
            return tickets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Ticket> getAllPossibleFilmTickets(String idMovie) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT row, seat, price, movie_id FROM ticket " +
                    "WHERE movie_id = ? " +
                    "AND person_id IS NULL");
            preparedStatement.setString(1, idMovie);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer row = resultSet.getInt("row");
                Integer seat = resultSet.getInt("seat");
                Integer price = resultSet.getInt("price");
                Ticket ticket = new Ticket(idMovie, row, seat, price);
                tickets.add(ticket);
            }
            return tickets;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int bookTicket(String id_person, String id_movie, Integer raw, Integer place) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ticket " +
                    "SET person_id = ? WHERE movie_id = ? AND row = ? AND seat = ? AND person_id IS NULL");
            preparedStatement.setString(1, id_person);
            preparedStatement.setString(2, id_movie);
            preparedStatement.setInt(3, raw);
            preparedStatement.setInt(4, place);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean updateForReturn(Integer id, String personId) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ticket " +
                    "SET person_id = NULL WHERE id = ? AND person_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, personId);
            if (preparedStatement.executeUpdate() == 0) {
                System.out.println("No such ticket");
                return false;
            } else {
                System.out.println("Ticket was successfully updated");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Attention: ticket was not deleted");
            return false;
        }
    }
}

//    @Override
//    public boolean deleteTicketById(Integer id) {
//        try (Connection connection = ConnectionManager.open()) {
//            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ticket WHERE id = ?");
//            preparedStatement.setString(1, id.toString());
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (!resultSet.next()) {
//                System.out.println("There is no ticket with specified number");
//                return false;
//            } else {
//                System.out.println("Ticket number " + id + " was deleted");
//                return true;
//            }
//        } catch (SQLException e) {
//            //logirovanie
//            System.out.println("exception");
//            return false;
//        }
////        return false;
//    }
//}
