package org.example.entity;

import org.example.enums.Genre;

import java.time.LocalDate;
import java.util.Random;

public class Book {
    private static int counterId=1000;
    private int id;
    private String title;
    private Author author;
    private Genre genre;
    private LocalDate publishedYear;
    private int quantity = 0;
    private int rentedQuantity = 0;

    Random random = new Random();
    public Book(Author author, String title, Genre genre, LocalDate publishedYear, int quantity) {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.publishedYear = publishedYear;
        this.quantity =quantity;
        this.id=counterId;
        counterId++;
    }

    public int getId() {
        return id;
    }


    public void increaseRentedQuantity() {
       rentedQuantity++;
    }

    public void decreaseRentedQuantity() {
        rentedQuantity--;
    }


    public void addBook(int quantity) {
        this.quantity += quantity;
    }

    public void removeBook(int quantity) {
        if (quantity > this.quantity) {
            this.quantity = 0;
        } else this.quantity -= quantity;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public LocalDate getPublishedYear() {
        return publishedYear;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getQuantityOfAvailableBooks(){
        int freeBooks =  quantity - rentedQuantity;
        return  freeBooks > 0?  freeBooks: 0;
    }

    public int getRentedQuantity() {
        return rentedQuantity;
    }

    @Override
    public String toString() {
        return  "Id='" + id + '\'' +
                "Title='" + title + '\'' +
                ", author=" + author.toString() +
                ", genre=" + genre +
                ", publishedYear=" + publishedYear +
                ", rentedQuantity=" + rentedQuantity+
                ", quantity= " + quantity;
    }
}
