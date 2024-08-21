package org.example.managers;

import org.example.entity.Author;
import org.example.entity.User;
import org.example.exceptions.AuthorDoesNotExistException;
import org.example.exceptions.UserDoesNotExistException;
import org.example.service.author.AuthorService;
import org.example.service.author.AuthorServiceImpl;
import org.example.service.user.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class AuthorManager {
    private static AuthorManager instance;
    private static AuthorService authorService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private AuthorManager() {
        authorService = AuthorServiceImpl.getInstance();

    }

    public static AuthorManager getInstance() {
        if (instance == null) {
            instance = new AuthorManager();
        }
        return instance;


    }

    public void manageAuthors() {
        int option;
        System.out.println("Author Manager");
        do {
            printAuthorManagerMenu();

            try {
                option = Integer.parseInt(reader.readLine());
                switch (option) {
                    case 1:
                        displayAll();
                        break;
                    case 2:
                        findAuthorById();
                        break;
                    case 3:
                        searchAuthorByName();
                        break;
                    case 4:
                        createAuthor();
                        break;
                    case 5:
                        deleteAuthor();
                        break;
                }
            } catch (NumberFormatException | IOException e) {
                option = 0;
            }


        } while (option != 0);
    }

    private void deleteAuthor() {
        try {
            System.out.println("Warning! All current author's book in library will be removed. Provide user's ID");
            authorService.deleteAuthorById(Integer.parseInt(reader.readLine()));
            System.out.println("Author has been deleted.");
        } catch (NumberFormatException | IOException | AuthorDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createAuthor() throws IOException {
        try {
            System.out.println("Provide first name: ");
            String firstName = reader.readLine();
            System.out.println("Provide last name: ");
            String lastName = reader.readLine();

            authorService.createAuthor(firstName, lastName);
            System.out.println("Author has been created.");
            System.out.println();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void searchAuthorByName() {
        try {
            System.out.println("Provide user's name");
            System.out.println("---------------");
            authorService.findByName(reader.readLine()).forEach(s -> System.out.println(s));
            System.out.println("---------------");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void findAuthorById() {
        System.out.println("Provide author's ID: ");
        try {
            Author author = authorService.findById(Integer.parseInt(reader.readLine()));
            System.out.println(author);
        } catch (NumberFormatException | IOException | AuthorDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private void displayAll() {
        System.out.println("---------------");
        authorService.findAll().forEach(s -> System.out.println(s));
        System.out.println("---------------");
    }

    private void printAuthorManagerMenu() {
        System.out.println("1. Display all");
        System.out.println("2. Find author by ID");
        System.out.println("3. Search Authors by name");
        System.out.println("4. Create Author");
        System.out.println("5. Delete Author");
        System.out.println("0. Back to main menu");
    }

}