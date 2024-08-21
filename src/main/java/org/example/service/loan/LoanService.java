package org.example.service.loan;

import org.example.entity.Loan;
import org.example.exceptions.BookDoesNotExistException;
import org.example.exceptions.LoanDoesNotAllowedException;
import org.example.exceptions.LoanDoesNotExistException;
import org.example.exceptions.UserDoesNotExistException;

import java.util.List;

public interface LoanService {
    Loan findByUserIdAndBookId(int userId, int bookId) throws LoanDoesNotExistException, UserDoesNotExistException, BookDoesNotExistException;
    List<Loan> findAll();
    List<Loan> findActiveLoans();
    List<Loan> findByUserId(int id) throws UserDoesNotExistException;
    List<Loan> findByBookId(int id) throws BookDoesNotExistException;

    void rentBook(int userId, int bookId) throws LoanDoesNotAllowedException, BookDoesNotExistException, UserDoesNotExistException;
    void returnBook(int userId, int bookId) throws LoanDoesNotExistException, UserDoesNotExistException, BookDoesNotExistException;
}
