package com.cinema.service;

import com.cinema.model.Person;
import com.cinema.repository.PersonRepository;
import com.cinema.util.Role;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;
    private static final Pattern PATTERN_UUID =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");


    private Scanner scanner = new Scanner(System.in);
    private static final File FILE = Path.of("resources", "person.out").toFile();
    static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    static final String PASS_PATTERN = "^.{7,10}$";

    @Override
    public Person create() {
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        String userName = enterEmail();
        String password = enterPass();
        Person person = new Person(name, userName, password, Role.USER);
        log.info("Person " + name + " registered");
        return personRepository.create(person);
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.getAllPersons();
    }

    public Optional <Person> come() {
        scanner = new Scanner(System.in);
        System.out.println("Enter your email: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        List <Person> person = personRepository.getAllPersons();
           Optional <Person> personLog = person.stream()
                    .filter(person1 -> person1.getUsername().equals(username) && person1.getPassword().equals(password))
                    .findAny();
           return personLog;
    }

    @Override
    public void updatePersonById() {
        personRepository.getAllPersons().forEach(System.out::println);
        System.out.println("Enter id: ");
        String id = scanner.next();
        if (PATTERN_UUID.matcher(id).matches()) {
            System.out.println("Enter new role: ");
            String userName = scanner.next().toUpperCase();
            if (userName.equals(Role.USER.name())) {
                personRepository.updateById(id, Role.USER);
                System.out.println("User role established");
                log.info("Person role with id " + id + " has been changed to user");
            } else if (userName.equals(Role.MANAGER.name())) {
                personRepository.updateById(id, Role.MANAGER);
                System.out.println("Manager role established");
                log.info("Person role with id " + id + " has been changed to manager");
            } else if (userName.equals(Role.ADMINISTRATOR.name())) {
                personRepository.updateById(id, Role.ADMINISTRATOR);
                System.out.println("Administrator role established");
                log.info("Person role with id " + id + " has been changed to administrator");
            } else System.out.println("We don't have chosen role");
        }
    }


    @Override
    public void deletePersonById() {
        getAllPersons().forEach(System.out::println);
        System.out.println("Enter id: ");
        String id = scanner.next();
        if (PATTERN_UUID.matcher(id).matches()) {
            if (personRepository.deletePersonById(id)) {
                System.out.println("Person deleted");
                log.info("Person with id " + id + " has been succesfully deleted");
            } else System.out.println("No such person");
        } else System.out.println("Incorrect id");
    }

    private String enterEmail() {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        System.out.println("Enter your email (in future you can use it as login): ");
        String userName;
        while (true) {
            userName = scanner.nextLine();
            List <String> emailBase = personRepository.getAllPersons().stream()
                    .map(Person::getUsername)
                    .collect(Collectors.toList());
            if (!userName.isBlank() && pattern.matcher(userName).matches() && !emailBase.contains(userName)) {
                System.out.println("Right email");
                break;
            } else {
                System.out.println("Incorrect email. Try again: ");
            }
        }
        return userName;
    }

    private String enterPass() {
        Pattern pattern = Pattern.compile(PASS_PATTERN);
        System.out.println("Enter your password (password must contain from 7 to 10 symbols): ");
        String password;
        while (true) {
            password = scanner.nextLine();
            if (pattern.matcher(password).matches()) {
                System.out.println("Right password");
                break;
            } else {
                System.out.println("Incorrect password. Try again: ");
            }
        }
        return password;
    }

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
