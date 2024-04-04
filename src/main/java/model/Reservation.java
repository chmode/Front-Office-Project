package model;

public class Reservation {

    private long id;
    private String checkInDate;
    private String checkOutDate;
    private Room room; // Add a Room attribute

    // Constructors, getters, and setters

    public Reservation() {
        // Default constructor
    }

    public Reservation(long id, String checkInDate, String checkOutDate, Room room) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
    }

    public Reservation(String checkInDate, String checkOutDate, Room room) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.room = room;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
