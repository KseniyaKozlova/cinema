package com.cinema.model;

import java.util.Scanner;
import java.util.UUID;

public class Ticket {

//    private UUID id;
    private Integer id;
    private String personId;
    private String movieId;
    private Integer raw;
    private Integer seat;
    private Integer price;
//    private boolean emptySeat;

    public Ticket(String personId, String movieId, Integer raw, Integer seat, Integer price) {
        this.personId = personId;
        this.movieId = movieId;
        this.raw = raw;
        this.seat = seat;
        this.price = price;
//        emptySeat = true;
    }

    public Ticket(Integer id, String personId, String movieId, Integer raw, Integer seat, Integer price) {
        this.id = id;
        this.personId = personId;
        this.movieId = movieId;
        this.raw = raw;
        this.seat = seat;
        this.price = price;
    }

    public Ticket(String movieId, Integer raw, Integer seat, Integer price) {
        this.movieId = movieId;
        this.raw = raw;
        this.seat = seat;
        this.price = price;
    }

//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovie(String movie) {
        this.movieId = movieId;
    }

    public Integer getRaw() {
        return raw;
    }

    public void setRaw(Integer raw) {
        this.raw = raw;
    }

    public Integer getSeat() {
        return seat;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

//    public boolean getEmptySeat() {
//        return emptySeat;
//    }
//
//    public void setNotEmptySeat() {
//        emptySeat = false;
//    }

//    public void setNotEmptySeat() {
//        if (!person.equals(null)) {
//            emptySeat = false;
//        }
//    }

    @Override
    public String toString() {
        return "Ticket{" +
                "number " + id +
//                "personId=" + personId +
//                ", movie=" + movie +
                ", raw=" + raw +
                ", seat=" + seat +
                ", price=" + price +
                '}';
    }
}
