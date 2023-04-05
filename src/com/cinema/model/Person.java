package com.cinema.model;

import com.cinema.util.Role;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Person implements Serializable {

    public static final long SVUUID = 1L;

    private UUID id;
    private String username;
    private String password;
    private Role role;
    // логин уникальный, хэшируется; пароль может повторяться
    //перед созданием пользователя, проверить, есть ли такой пользователь по логину


//    public Person(Integer id) {
//        this.id = id;
//    }

    public Person(String username, String password, Role role) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Person(UUID id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

//    public Person(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }

    public Person() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(username, person.username) && Objects.equals(password, person.password) && role == person.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, role);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
