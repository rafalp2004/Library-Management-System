package org.example.managers;

import org.example.entity.Book;
import org.example.entity.User;
import org.example.exceptions.UserDoesNotExistException;
import org.example.service.user.UserService;
import org.example.service.user.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class UserManager {
    private static UserManager instance;
    private final UserService userService;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private UserManager() {
        userService = UserServiceImpl.getInstance();

    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;

    }

    public void manageUsers() {

        System.out.println("User Manager.");
        int option;
        do {
            printUserManagerMenu();
            try {

                System.out.println("User Manager.");
                option = Integer.parseInt(reader.readLine());
                switch (option) {
                    case 1:
                        displayAll();
                        break;
                    case 2:
                        findUserById();
                        break;
                    case 3:
                        searchUsersByName();
                        break;
                    case 4:
                        displayUserRentedBooks();
                        break;
                    case 5:
                        createUser();
                        break;
                    case 6:
                        deleteUser();
                        break;
                }
            } catch (NumberFormatException | IOException e) {
                option = 0;
            }


        } while (option != 0);
    }

    private void displayAll() {
        System.out.println("---------------");
        userService.findAll().forEach(s -> System.out.println(s));
        System.out.println("---------------");
    }

    private void deleteUser() {
        try {
            System.out.println("Provide user's ID");
            userService.deleteUser(Integer.parseInt(reader.readLine()));
            System.out.println("User has been deleted.");
        } catch (NumberFormatException | IOException | UserDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createUser() {

        try {
            System.out.println("Provide first name: ");
            String firstName = reader.readLine();
            System.out.println("Provide last name: ");
            String lastName = reader.readLine();

            userService.createUser(firstName, lastName);
            System.out.println("User has been created.");
            System.out.println();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    private void displayUserRentedBooks() {
        try {
            System.out.println("Provide user's ID");
            List<Book> books = userService.getRentedBooksByUserId(Integer.parseInt(reader.readLine()));
            System.out.println("---------------");
            books.forEach(s -> System.out.println(s));
            System.out.println("---------------");
        } catch (NumberFormatException | IOException e) {
            e.getMessage();
        }
    }

    private void searchUsersByName() {
        try {
            System.out.println("Provide user's name");
            List<User> users = userService.findByName(reader.readLine());
            System.out.println("---------------");
            users.forEach(s -> System.out.println(s));
            System.out.println("---------------");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void findUserById() {

        System.out.println("Provide user's ID: ");
        try {
            int id = Integer.parseInt(reader.readLine());
            User user = userService.findById(id);
            System.out.println(user);
        } catch (NumberFormatException | UserDoesNotExistException | IOException e) {
            System.out.println("not found user with this ID ");
        }
    }

    private void printUserManagerMenu() {
        System.out.println("1. Display all");
        System.out.println("2. Find user by ID");
        System.out.println("3. Search users by name");
        System.out.println("4. Display user's rented books");
        System.out.println("5. Create User");
        System.out.println("6. Delete User");
        System.out.println("0. Back to main menu");
    }

}
