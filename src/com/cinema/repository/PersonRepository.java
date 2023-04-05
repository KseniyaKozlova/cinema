package com.cinema.repository;

import com.cinema.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository {

    Person create(Person person);

    List<Person> getAllPersons();

    boolean getPersonByName(String username);

    Optional<Person> come(String username, String password);

//    Person getPersonByID(String username);

    Person updateById(UUID id, Person person);

    boolean deletePersonById(Integer id);
}
