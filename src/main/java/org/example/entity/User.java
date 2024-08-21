package org.example.entity;

import org.example.enums.Status;

import java.util.*;

public class User {
    private static int counterId = 1000;
    private int id;
    private String firstName;
    private String lastName;
    private Map<Book, Enum> rentedBooks;


    public User(String firstName, String lastName) {
        this.id = counterId;
        counterId++;
        this.firstName = firstName;
        this.lastName = lastName;
        rentedBooks = new HashMap<>();
    }

    public static int getCounterId() {
        return counterId;
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

    public Map<Book, Enum> getRentedBooks() {
        return rentedBooks;
    }

    public void addBook(Book book){
        rentedBooks.put(book, Status.RENTED);
    }
    public void returnBook(Book book){
        rentedBooks.put(book, Status.RETURNED);
    }

    @Override
    public String toString() {
        return "Id: " + id + " " +
                firstName +
                " " + lastName;
    }
}
