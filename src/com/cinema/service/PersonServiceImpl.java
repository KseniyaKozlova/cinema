package com.cinema.service;

import com.cinema.model.Person;
import com.cinema.repository.PersonRepositoryImpl;
import com.cinema.repository.PersonRepository;
import com.cinema.util.Role;
import com.cinema.util.exception.NoSuchPersonException;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PersonServiceImpl implements PersonService{
    //logirovanie, насыщение данными, маппинги


    private final PersonRepository personRepository;
    private final TicketService ticketService;

    public PersonServiceImpl(PersonRepository personRepository, TicketService ticketService) {
        this.personRepository = personRepository;
        this.ticketService = ticketService;
    }

    private Scanner scanner = new Scanner(System.in);
    private static final File FILE = Path.of("resources", "person.out").toFile();
    static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    static final String PASS_PATTERN = "^.{7,10}$";
    Pattern pattern;
//    Pattern pattern =
//            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
//        if (pattern.matcher(id).matches()) {



    private String enterEmail() {
        pattern = Pattern.compile(EMAIL_PATTERN);
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
//                userName = scanner.nextLine();
            }
        }
        return userName;
    }

    private String enterPass() {
        pattern = Pattern.compile(PASS_PATTERN);
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

    @Override
    public Person create() {
        String userName = enterEmail();
//        if (userName == null || userName.isBlank() || personRepository.getPersonByName(userName)) {
//            System.out.println("This person is already exist");
//            return create();
//        } else {
            String password = enterPass();
            Person person = new Person(userName, password, Role.USER);
            return personRepository.create(person);
//        }
    }

//    @Override
//    public Person getPersonByName(String username) {
//        return person;
//    }

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

//        return personRepository.come(username, password).get();
//        try {
//            return personOptional.orElseThrow(NoSuchPersonException::new);
//        } catch (NoSuchPersonException e) {
//            return new Person();
//        }



//        if (personOptional.isPresent()) {
//            person = personOptional.get();
//            return person;}
//            return true;
//        } else {
//            System.out.println("You not registrate");
//            return false;
//        }
//            return personRepository.come(username, password)
//                    .orElseThrow(NoSuchPersonException::new);
//        } catch (NoSuchPersonException e) {
//            System.out.println("no person");
//        }
//        String userName = enterEmail();
//        String password = enterPass();
        List <Person> person = personRepository.getAllPersons();
//        Person personNew = null;
//        try {
           Optional <Person> personLog = person.stream()
                    .filter(person1 -> person1.getUsername().equals(username) && person1.getPassword().equals(password))
                    .findAny();
//            if (personOptional.isPresent()) {
//                personNew = personOptional.get();
//            } else {
//                return "no ";
//            }
//                    .ifPresent(person1 -> person1)
//            if(personNew.isPresent()) {
//
//            }
//                    .orElseGet( () -> System.out.println());
//        } catch (NoSuchPersonException e) {
//            System.out.println("no");
//        }
//        return personNew.map(obj -> {
//                    Person person1 = person;
//                    //some logic with result and return it
//                    return personNew;
//                })
//                .orElseGet(this::come);

           return personLog;
    }

    @Override
    public Person updatePersonById() {

        System.out.println("enter id number: ");
        String id = scanner.next();
//        scanner = new Scanner(System.in);
        System.out.println("enter new login: ");
        String userName = scanner.next();
        System.out.println("enter new password: ");
        String password = scanner.next();

        Person person1 = new Person (UUID.fromString(id), userName, password, Role.USER);

        return personRepository.updateById(UUID.fromString(id), person1);
    }


    @Override
    public boolean deletePersonById(Integer id) {
        return personRepository.deletePersonById(id);
    }

    private void serialise(Person person) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE))) {
            outputStream.writeObject(person);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Person deserialise() {
        Person person1;
        try (ObjectInputStream intputStream = new ObjectInputStream(new FileInputStream(FILE))) {
            person1 = (Person) intputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return person1;
    }

//    public PersonServiceImpl(PersonRepository personRepository, TicketService ticketService) {
//        this.personRepository = personRepository;
//        this.ticketService = ticketService;
//    }
}
