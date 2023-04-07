package com.cinema.model;

public class CinemaHall {

    private static final int ROW = 4;
    private static final int SEAT = 11;
    private static final String [][] SEATS = new String[ROW][SEAT];

    public void createSeats() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < SEAT; j++) {
                SEATS[i][j] = "Open";
            }
        }
    }

    public void displayCinema() {
        System.out.println("                  Cinema screen front");
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < SEAT; j++) {
                System.out.print(SEATS[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("                      Back wall");
    }

    public void buyTickets(int rowNumber, int seatNumber) {
        int rowNumberNew = rowNumber - 1;
        int seatNumberNew = seatNumber - 1;
            if (rowNumberNew > ROW || seatNumberNew > SEAT) { //change with ArrayIndexOutOfBoundsException
                System.out.println("Incorrect meaning");
            } else if (SEATS[rowNumberNew][seatNumberNew].equals("Taken")) {
                System.out.println("This place has been taken!");
            } else {
                SEATS[rowNumberNew][seatNumberNew] = "Taken";
                System.out.println("You booked place in row " + rowNumber + " and seat " + seatNumber);
        }
    }
}