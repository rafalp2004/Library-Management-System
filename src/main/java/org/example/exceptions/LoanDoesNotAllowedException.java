package org.example.exceptions;

public class LoanDoesNotAllowedException extends Exception{
    public LoanDoesNotAllowedException(String message){
        super(message);
    }
}
