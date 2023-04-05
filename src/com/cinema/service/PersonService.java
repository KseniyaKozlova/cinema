package com.cinema.service;

import com.cinema.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    /**
     * Create person
     */
    Person create();

//    Person getPersonByName(String username);

    List<Person> getAllPersons();

    Person updatePersonById(); //by login

    Optional <Person> come();

    boolean deletePersonById(Integer id);
}
