package org.example.managers;

import org.example.entity.Loan;
import org.example.exceptions.BookDoesNotExistException;
import org.example.exceptions.LoanDoesNotAllowedException;
import org.example.exceptions.LoanDoesNotExistException;
import org.example.exceptions.UserDoesNotExistException;
import org.example.service.loan.LoanService;
import org.example.service.loan.LoanServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class LoanManager {
    private static LoanManager instance;
    private final LoanService loanService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private LoanManager() {
        loanService = LoanServiceImpl.getInstance();
    }

    public static LoanManager getInstance() {
        if (instance == null) {
            instance = new LoanManager();
        }
        return instance;
    }

    public void manageLoans() {
        int option;
        System.out.println("Loan Manager");
        do {
            try {

                printLoansMangerMenu();

                option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        displayAll();
                        break;
                    case 2:
                        findLoanUserByIdAndBookID();
                        break;
                    case 3:
                        displayAllActiveLoans();
                        break;
                    case 4:
                        findLoansByUserId();
                        break;
                    case 5:
                        findLoansByBookId();
                        break;
                    case 6:
                        rentBook();
                        break;
                    case 7:
                        returnBook();
                        break;
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println(e.getMessage());
                option = 0;
            }


        } while (option != 0);
    }

    private void displayAll() {
        loanService.findAll().forEach(s -> System.out.println(s));
    }

    private void findLoanUserByIdAndBookID() {

        try {
            System.out.println("Provide user id: ");
            int userId = Integer.parseInt(reader.readLine());
            System.out.println("Provide book id: ");
            int bookId = Integer.parseInt(reader.readLine());
            Loan loan = loanService.findByUserIdAndBookId(userId, bookId);
            System.out.println(loan);
        } catch (LoanDoesNotExistException | NumberFormatException | IOException | UserDoesNotExistException |
                 BookDoesNotExistException e) {
            System.out.println(e.getMessage());
        }

    }

    private void displayAllActiveLoans() {
        loanService.findActiveLoans().forEach(s -> System.out.println(s));
    }


    private void findLoansByUserId() {
        try {
            System.out.println("Provide user ID");
            int userId = Integer.parseInt(reader.readLine());
            loanService.findByUserId(userId).forEach(s -> System.out.println(s));
        } catch (NumberFormatException | IOException | UserDoesNotExistException e) {
            System.out.println(e.getMessage());
        }

    }

    private void findLoansByBookId() {
        try {
            System.out.println("Provide book ID");
            int bookId = Integer.parseInt(reader.readLine());
            loanService.findByBookId(bookId).forEach(s -> System.out.println(s));
        } catch (NumberFormatException | IOException | BookDoesNotExistException e) {
            System.out.println(e.getMessage());
        }

    }

    private void rentBook() {
        try {
            System.out.println("Provide user id: ");
            int userId = Integer.parseInt(reader.readLine());
            System.out.println("Provide book id: ");
            int bookId = Integer.parseInt(reader.readLine());
            loanService.rentBook(userId, bookId);
            System.out.println("Book has been rented");
        } catch (NumberFormatException | IOException | LoanDoesNotAllowedException | BookDoesNotExistException |
                 UserDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private void returnBook() {
        try {
            System.out.println("Provide user id: ");
            int userId = Integer.parseInt(reader.readLine());
            System.out.println("Provide book id: ");
            int bookId = Integer.parseInt(reader.readLine());
            loanService.returnBook(userId, bookId);
            System.out.println("Book has been returned");
        } catch (NumberFormatException | IOException | LoanDoesNotExistException | UserDoesNotExistException |
                 BookDoesNotExistException e) {
            System.out.println(e.getMessage());
        }

    }


    private void printLoansMangerMenu() {
        System.out.println("1. Display all loans");
        System.out.println("2. Find loan by user ID and book ID");
        System.out.println("3. Display all active loans");
        System.out.println("4. Find user's loans by user ID");
        System.out.println("5. Find loans by book ID");
        System.out.println("6. Rent book (user ID, bookID");
        System.out.println("7. Return book (user ID, book ID");
        System.out.println("0. Back to main menu");
    }


}
