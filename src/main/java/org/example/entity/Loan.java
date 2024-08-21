package org.example.entity;

import java.time.LocalDate;

public class Loan {
    private User user;
    private Book book;
    private LocalDate rentedDate;
    private boolean isReturned;

    public Loan(User user, Book book) {
        this.user = user;
        this.book = book;
        this.rentedDate = LocalDate.now();
        isReturned = false;
    }

    public void setStatus(boolean returned) {
        isReturned = returned;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    @Override
    public String toString() {
        return
                "user=" + user.getId() +" "+ user.getFirstName()+" "+ user.getFirstName()+
                ", book={ ID: " + book.getId() + "| title:" + book.getTitle() + "| rented quantity: " + book.getRentedQuantity() + "| total quantity: " + book.getQuantity() + "| avaible quantity: " + book.getQuantityOfAvailableBooks()+"}" +
                ", rentedDate=" + rentedDate +
                ", isReturned=" + isReturned;
    }
}
