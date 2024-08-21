package org.example.managers;

import org.example.entity.Book;
import org.example.entity.User;
import org.example.enums.Genre;
import org.example.exceptions.AuthorDoesNotExistException;
import org.example.exceptions.BookDoesNotExistException;
import org.example.exceptions.UserDoesNotExistException;
import org.example.service.author.AuthorService;
import org.example.service.author.AuthorServiceImpl;
import org.example.service.book.BookService;
import org.example.service.book.BookServiceImpl;
import org.example.service.user.UserService;
import org.example.service.user.UserServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BookManager {
    private static BookManager instance;
    private final BookService bookService;
    private final AuthorService authorService;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private BookManager() {
        bookService = BookServiceImpl.getInstance();
        authorService = AuthorServiceImpl.getInstance();
    }

    public static BookManager getInstance() {
        if (instance == null) {
            instance = new BookManager();
        }
        return instance;
    }

    public void manageBooks() {
        int option;
        System.out.println("Book Manager.");
        do {
            try {
                printBookManagerMenu();
                option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        displayAll();
                        break;
                    case 2:
                        findBookById();
                        break;
                    case 3:
                        findBooksByTitle();
                        break;
                    case 4:
                        findBooksByGenre();
                        break;
                    case 5:
                        findBooksByAuthorId();
                        break;
                    case 6:
                        addBook();
                        break;
                    case 7:
                        deleteBook();
                        break;
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println(e.getMessage());
                option = 0;
            }

        } while (option != 0);
    }


    private void deleteBook() {


        try {
            System.out.println("Provide book's ID");
            int bookId = Integer.parseInt(reader.readLine());
            int quantity = bookService.findById(bookId).getQuantityOfAvailableBooks();
            System.out.printf("How many books do you want to delete? (0-%d)", quantity);
            int deleteQuantity = Integer.parseInt(reader.readLine());
            bookService.deleteBook(bookId, deleteQuantity);
            System.out.println("Book has been deleted.");

        } catch (BookDoesNotExistException | IOException  e) {
            System.out.println(e.getMessage());        }

    }





private void addBook() {
    try {
        System.out.println("Provide title");
        String title = reader.readLine();
        Genre genre = getGenre();
        System.out.println("Provide quantity");
        int quantity = Integer.parseInt(reader.readLine());
        System.out.println("Choose option: ");
        System.out.println(" 1. Provide Author ID\n2. Create new author");
        int option = Integer.parseInt(reader.readLine());
        if (option == 1) {
            authorService.findAll().forEach(s -> System.out.println(s));
            int id = Integer.parseInt(reader.readLine());
            bookService.createBook(title, id, genre, quantity);
            System.out.println("Book has been added");
        } else if (option == 2) {
            System.out.println("Provide first name");
            String firstName = reader.readLine();
            System.out.println("Provide last name");
            String lastName = reader.readLine();
            bookService.createBook(title, firstName, lastName, genre, quantity);
            System.out.println("Book has been added");
        }

    } catch (NumberFormatException | IOException | AuthorDoesNotExistException e) {
        System.out.println(e.getMessage());
    }
}

private Genre getGenre() {
    System.out.println("Provide genre: (id)");
    System.out.println("1. " + Genre.HORROR);
    System.out.println("2. " + Genre.FANTASY);
    System.out.println("3. " + Genre.BIOGRAPHY);
    System.out.println("4. " + Genre.MYSTERY);
    System.out.println("5. " + Genre.ROMANCE_NOVEL);
    System.out.println("6. " + Genre.LITERARY_FICTION);
    System.out.println("7. " + Genre.SCIENCE_FICTION);

    int option = 0;
    try {
        option = Integer.parseInt(reader.readLine());

        switch (option) {
            case 1:
                return Genre.HORROR;
            case 2:
                return Genre.FANTASY;
            case 3:
                return Genre.BIOGRAPHY;
            case 4:
                return Genre.MYSTERY;
            case 5:
                return Genre.ROMANCE_NOVEL;
            case 6:
                return Genre.LITERARY_FICTION;
            case 7:
                return Genre.SCIENCE_FICTION;
        }

    } catch (NumberFormatException | IOException e) {
        System.out.println("Wrong data provided.");
    }
    return null;
}

private void findBooksByAuthorId() {
    System.out.println("Provide author's id:");
    try {
        int id = Integer.parseInt(reader.readLine());
        bookService.findBooksByAuthorId(id).forEach(s -> System.out.println(s));
    } catch (NumberFormatException | IOException | AuthorDoesNotExistException e) {
        System.out.println(e.getMessage());
    }
}

private void findBooksByGenre() {
    Genre genre = getGenre();
    bookService.findByGenre(genre).forEach(s -> System.out.println(s));
}

private void findBooksByTitle() {
    try {
        System.out.println("Provide title: ");
        String title = reader.readLine();
        bookService.findByTitle(title).forEach(s -> System.out.println(s));
    } catch (IOException e) {
        System.out.println(e.getMessage());

    }
}

private void findBookById() {

    try {
        System.out.println("Provide book's ID: ");
        int id = Integer.parseInt(reader.readLine());
        Book book = bookService.findById(id);
        System.out.println(book);
    } catch (NumberFormatException | IOException | BookDoesNotExistException e) {
        System.out.println(e.getMessage());
    }
}

private void displayAll() {
    System.out.println("---------------");
    bookService.findAll().forEach(s -> System.out.println(s));
    System.out.println("---------------");
}

private void printBookManagerMenu() {
    System.out.println("1. Display all");
    System.out.println("2. Find book by ID");
    System.out.println("3. Find books by title");
    System.out.println("4. Find book by genre");
    System.out.println("5. Find books by Author ID");
    System.out.println("6. Add Book");
    System.out.println("7. Delete Book");
    System.out.println("0 Back to main menu");
}

}
