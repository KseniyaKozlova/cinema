package com.cinema.repository;

import com.cinema.model.Person;
import com.cinema.util.ConnectionManager;
import com.cinema.util.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonRepositoryImpl implements PersonRepository {

    @Override
    public Person create(Person person) {
        try (Connection connection = ConnectionManager.open()) {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO person (id, name, username, password, role)VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, person.getId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getUsername());
            preparedStatement.setString(4, person.getPassword());
            preparedStatement.setString(5, Role.USER.name());
            preparedStatement.execute();
            System.out.println("Your registration was completed successfully");
        } catch (SQLException e) {
            System.out.println("Error");
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
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                Person person = new Person(id, name, username, password, Role.valueOf(role));
                persons.add(person);
            }
        } catch (SQLException e) {
            System.out.println("Error");
        }
        return persons;
    }

    @Override
    public int updateById(String id, Role role) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE person SET role = ? WHERE id = ?");
            preparedStatement.setString(1, role.name());
            preparedStatement.setString(2, id);
            preparedStatement.executeUpdate();
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error");
        }
        return 0;
    }

    @Override
    public boolean deletePersonById(String id) {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM person WHERE id = ?");
            preparedStatement.setString(1, id);
            int resultSet = preparedStatement.executeUpdate();
            return resultSet != 0;
        } catch (SQLException e) {
            System.out.println("No person");
            return false;
        }
    }
}
