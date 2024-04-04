package model;

public class Room {
    private long id;
    private int matricule;
    private boolean status;
    private RoomType roomType;

    // Constructors, getters, and setters

    public Room() {
        // Default constructor
    }

    public Room(long id, int matricule, boolean status, RoomType roomType) {
        this.id = id;
        this.matricule = matricule;
        this.status = status;
        this.roomType = roomType;
    }

    // Getter and Setter methods

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
}
