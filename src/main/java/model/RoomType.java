package model;

public class RoomType {
    private long id;
    private String label;
    private String description;
    private int numberOfPersons;

    // Constructors, getters, and setters

    public RoomType() {
        // Default constructor
    }

    public RoomType(long id, String label, String description, int numberOfPersons) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.numberOfPersons = numberOfPersons;
    }

    // Getter and Setter methods

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

     public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }
}

