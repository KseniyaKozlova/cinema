package com.cinema.service;

import com.cinema.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    /**
     * Create new account on login
     */
    Person create();

    /**
     * See all possible accounts with logins and id
     */
    List<Person> getAllPersons();

    /**
     * Change user access level
     */
    void updatePersonById();

    /**
     * Check and have information about logged user
     */
    Optional <Person> come();

    /**
     * Delete user from base
     */
    void deletePersonById();
}
