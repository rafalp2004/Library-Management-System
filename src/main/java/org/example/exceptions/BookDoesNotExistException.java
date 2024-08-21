package org.example.exceptions;

public class BookDoesNotExistException extends Exception {
    public BookDoesNotExistException(String message){
       super(message);
    }
}
