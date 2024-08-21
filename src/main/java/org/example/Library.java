package org.example;

import org.example.exceptions.BookDoesNotExistException;
import org.example.managers.AuthorManager;
import org.example.managers.BookManager;
import org.example.managers.LoanManager;
import org.example.managers.UserManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Library {


    public static void main(String[] args) {


        LoanManager loanManager = LoanManager.getInstance();
        UserManager userManager = UserManager.getInstance();
        BookManager bookManager = BookManager.getInstance();
        AuthorManager authorManager = AuthorManager.getInstance();

        int option;

      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome at Library Managment System.");
        do {
            printMainMenu();
            try {
                option = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException | IOException e ) {
                System.out.println("Invalid input. Please enter a number.");
                option=0;
                continue;
            }
            switch (option) {
                case 1:
                    userManager.manageUsers();
                    break;
                case 2:
                    bookManager.manageBooks();
                    break;
                case 3:
                    loanManager.manageLoans();
                    break;
                case 4:
                    authorManager.manageAuthors();
                    break;
            }
        } while (option != 0);

    }


    public static void printMainMenu() {
        System.out.println("1. Users Management");
        System.out.println("2. Books Management");
        System.out.println("3. Loans Management");
        System.out.println("4. Authors Management");
        System.out.println("0. Exit");
    }
}
