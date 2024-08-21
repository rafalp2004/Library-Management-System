package org.example.entity;

public class Author {
    private static int idCounter = 1000;
    private int id;
    private String firstName;
    private String lastName;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = idCounter;
        idCounter++;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    @Override
    public String toString() {
        return "Id: " + id + " " +
                firstName +
                " " + lastName;
    }
}
