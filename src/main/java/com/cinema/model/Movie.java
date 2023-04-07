package com.cinema.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Movie {

    private String id;
    private String movieName;
    private LocalDate date;
    private LocalTime time;
    private int age;
    private List<Ticket> tickets = new ArrayList<>();
//    private CinemaHall cinemaHall;
    private static final int ROW = 3;
    private static final int SEAT = 4;
//    private static final Ticket [][] TICKETS = new Ticket[ROW][SEAT];

    public List<Ticket> createTickets() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < SEAT; j++) {
//                TICKETS [i][j] = new Ticket(null, id, i+1, j++, 60);
                tickets.add(new Ticket(null, id, i+1, j+1, 60));
            }
        }
        return tickets;
//        tickets.forEach(System.out::println);
    }

//    public void seeTickets() {
//        System.out.println("                  Cinema screen front");
//        for (int i = 0; i < ROW; i++) {
//            for (int j = 0; j < SEAT; j++) {
//                System.out.print(TICKETS [i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println("                      Back wall");
//    }

//    public void buyTickets(Integer personId, int rowNumber, int seatNumber) throws NullPointerException {
//        int rowNumberNew = rowNumber - 1;
//        int seatNumberNew = seatNumber - 1;
//        if (rowNumberNew > ROW || seatNumberNew > SEAT) { //change with ArrayIndexOutOfBoundsException
//            System.out.println("Incorrect meaning");
//        } else if (TICKETS[rowNumberNew][seatNumberNew].getPersonId() != null) {
//            System.out.println("This place has been taken!");
//        } else {
//            TICKETS [rowNumberNew][seatNumberNew].setPersonId(personId);
//            System.out.println(TICKETS[rowNumberNew][seatNumberNew].getPersonId());
//            System.out.println("You booked place in row " + rowNumber + " and seat " + seatNumber);
//        }
//    }

//    public void buyTickets(Integer personId, int rowNumber, int seatNumber) throws NullPointerException {
//        if (rowNumberNew > ROW || seatNumberNew > SEAT) { //change with ArrayIndexOutOfBoundsException
//            System.out.println("Incorrect meaning");
//        if (t != null) {
//            System.out.println("This place has been taken!");
//        } else {
//            TICKETS [rowNumberNew][seatNumberNew].setPersonId(personId);
//            System.out.println(TICKETS[rowNumberNew][seatNumberNew].getPersonId());
//            System.out.println("You booked place in row " + rowNumber + " and seat " + seatNumber);
//        }
//    }

    public Movie(String movieName, LocalDate date, LocalTime time, Integer age) {
        this.id = UUID.randomUUID().toString();
        this.movieName = movieName;
        this.date = date;
        this.time = time;
        this.age = age;
        this.tickets = createTickets();
    }

    public Movie(String id, String movieName, LocalDate date, LocalTime time, Integer age) {
        this.id = id;
        this.movieName = movieName;
        this.date = date;
        this.time = time;
        this.age = age;
        this.tickets = createTickets();
    }


    public Movie(String movieName) {
        this.movieName = movieName;
    }

//    public Movie(LocalDate date, LocalTime time, Integer age) {
//        this.id = id;
//        this.movieName = movieName;
//        this.date = date;
//        this.time = time;
//        this.age = age;
//        createTickets();
//    }

//    public Movie(String movieName, LocalDate date, LocalTime time, Integer age) {
//        this.id = id;
//        this.movieName = movieName;
//        this.date = date;
//        this.time = time;
//        this.age = age;
//        createTickets();
//    }

//    private void seeSeats() {
//        List<Ticket> ticketList = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 5; j++) {
//                System.out.print(ticketList.add(new Ticket(i, j, 60, true)));
//                System.out.print(" ");
//            }
//            System.out.println();
//        }
//    }

    public Movie() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", age=" + age +
                '}';
    }

}
