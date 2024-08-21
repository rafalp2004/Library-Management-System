package org.example.exceptions;

public class AuthorDoesNotExistException extends Exception{
    public AuthorDoesNotExistException(String message) {
        super(message);
    }
}
