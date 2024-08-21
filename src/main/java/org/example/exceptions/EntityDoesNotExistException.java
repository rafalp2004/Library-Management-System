package org.example.exceptions;

public class EntityDoesNotExistException extends Exception {
    public EntityDoesNotExistException(String message){
        super(message);
    }
}
