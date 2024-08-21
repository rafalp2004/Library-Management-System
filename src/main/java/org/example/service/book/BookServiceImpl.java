package org.example.service.book;

import org.example.LibraryFactory;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.enums.Genre;
import org.example.exceptions.AuthorDoesNotExistException;
import org.example.exceptions.BookDoesNotExistException;
import org.example.service.author.AuthorService;
import org.example.service.author.AuthorServiceImpl;
import org.example.service.loan.LoanService;
import org.example.service.loan.LoanServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {

    private static BookServiceImpl instance;
    private final AuthorService authorService = AuthorServiceImpl.getInstance();
    private final LoanService loanService = LoanServiceImpl.getInstance();
    private final Map<Integer, Book> books;

    private BookServiceImpl() {
        books = LibraryFactory.getBooks(10, authorService.findAll())
                .stream()
                .collect(Collectors.toMap(Book::getId, book -> book));
    }

    public static BookServiceImpl getInstance() {
        if (instance == null) {
            instance = new BookServiceImpl();
        }
        return instance;
    }


    @Override
    public Book findById(int id) throws BookDoesNotExistException {
        Book book = books.get(id);
        if (book != null) {
            return book;
        } else throw new BookDoesNotExistException("Book with id: " + id + " does not exist.");
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    @Override
    public List<Book> findByTitle(String title) {
        return books.values().stream()
                .filter(s -> s.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByGenre(Genre genre) {
        return books.values().stream()
                .filter(s -> s.getGenre() == genre)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findBooksByAuthorId(int id) throws AuthorDoesNotExistException {

        if (authorService.findById(id) != null) {
            return books.values().stream()
                    .filter(s -> s.getAuthor().getId() == id)
                    .collect(Collectors.toList());
        } else {
            throw new AuthorDoesNotExistException("Author with id " + id + " does not exist");
        }
    }


    @Override
    public void createBook(String title, String authorFirstName, String authorLastName, Genre genre, int quantity) {
        Author author = new Author(authorFirstName, authorLastName);
        LocalDate publishedYear = LibraryFactory.generateRandomDate();
        Book book = new Book(author, title, genre, publishedYear, quantity);
        books.put(book.getId(), book);
    }

    @Override
    public void createBook(String title, int authorId, Genre genre, int quantity) throws AuthorDoesNotExistException {
        Author author = authorService.findById(authorId);
        LocalDate publishedYear = LibraryFactory.generateRandomDate();
        Book book = new Book(author, title, genre, publishedYear, quantity);
        books.put(book.getId(), book);
    }


    @Override
    public void deleteBook(int id, int quantity) throws BookDoesNotExistException {

        Book book = findById(id);

        if (book.getQuantityOfAvailableBooks() >= quantity) {
            book.setQuantity(book.getQuantity() - quantity);
        } else {
            throw new BookDoesNotExistException("There is no available books in library to remove with id: " + id);
        }
        if (book.getQuantity() == 0) {
            books.remove(book.getId());
        }

    }

    public void decreaseQuantity(int id) {
        try {
            Book book = findById(id);
            book.setQuantity(book.getQuantity() - 1);
            if (book.getQuantity() == 0) {
                books.remove(book.getId());
            }
        } catch (BookDoesNotExistException e) {
            System.out.println(e.getMessage());
        }

    }
}