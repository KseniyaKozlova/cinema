package com.cinema.repository;

import com.cinema.model.Person;
import com.cinema.util.ConnectionManager;
import com.cinema.util.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PersonRepositoryImpl implements PersonRepository {





    @Override
    public Person create(Person person) {
        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO person (id, username, password, role)VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, person.getId().toString());
            preparedStatement.setString(2, person.getUsername());
            preparedStatement.setString(3, person.getPassword());
            preparedStatement.setString(4, Role.USER.name());
            preparedStatement.execute();
            System.out.println("Your registration was completed successfully");
        } catch (SQLException e) {
//           return null;
            System.out.println("no");
        }
        return person;
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                Person person = new Person(UUID.fromString(id), username, password, Role.valueOf(role));
                persons.add(person);
            }
            return persons;
        } catch (SQLException e) {
            //logirovanie
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getPersonByName(String username) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE username = ?");
            preparedStatement.setString(1, username);
//            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            //logirovanie
            System.out.println("no");
//            throw new RuntimeException(e);
        }
        return false;
    }


    @Override
    public Person updateById(UUID id, Person person) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE person SET username = ?, password = ? WHERE id = ?");
            preparedStatement.setString(1, person.getUsername());
            preparedStatement.setString(2, person.getPassword());
            preparedStatement.setString(3, id.toString());
            preparedStatement.executeUpdate();
            return person;
        } catch (SQLException e) {
            //logirovanie
            System.out.println("not");
        }
        return null;
    }


    @Override
    public boolean deletePersonById(Integer id) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM person WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            //logirovanie
        }
        return false;
    }

    public Optional <Person> come(String username, String password) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE username = ? AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            Person person = null;
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String role = resultSet.getString("role");
                person = new Person(UUID.fromString(id), username, password, Role.valueOf(role));
            }
            return Optional.ofNullable(person);
        } catch (SQLException e) {
            throw new RuntimeException(e);
            //logirovanie
        }
    }
}
