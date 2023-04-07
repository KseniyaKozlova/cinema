package com.cinema.repository;

import com.cinema.model.Person;
import com.cinema.util.Role;

import java.util.List;

public interface PersonRepository {

    /**
     * Create new account on login in BD
     */
    Person create(Person person);

    /**
     * Have all possible accounts from BD
     */
    List<Person> getAllPersons();

    /**
     * Change user access level in BD
     */
    int updateById(String id, Role role);

    /**
     * Delete user from BD
     */
    boolean deletePersonById(String id);
}
