package org.example.service.author;

import org.example.LibraryFactory;
import org.example.entity.Author;
import org.example.exceptions.AuthorDoesNotExistException;
import org.example.exceptions.BookDoesNotExistException;
import org.example.service.book.BookService;
import org.example.service.book.BookServiceImpl;
import org.example.service.loan.LoanService;
import org.example.service.loan.LoanServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthorServiceImpl implements AuthorService {

    private static AuthorServiceImpl instance;
    private static BookService bookService;
    private static LoanService loanService;

    private Map<Integer, Author> authors;

    private AuthorServiceImpl() {
        authors = LibraryFactory.generateAuthors()
                .stream()
                .collect(Collectors.toMap(Author::getId, author -> author));
    }

    //Lazy initialization
    public BookService getBookService() {
        if (bookService == null) {
            bookService = BookServiceImpl.getInstance();
        }
        return bookService;
    }

    //Lazy initialization
    public LoanService getLoanService() {
        if (loanService == null) {
            loanService = LoanServiceImpl.getInstance();
        }
        return loanService;
    }


    public static AuthorServiceImpl getInstance() {
        if (instance == null) {
            instance = new AuthorServiceImpl();
        }
        return instance;
    }


    @Override
    public Author findById(int id) throws AuthorDoesNotExistException {
        return authors.values().stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElseThrow(() -> new AuthorDoesNotExistException("Author with id " + id + " does not exist"));
    }

    @Override
    public List<Author> findAll() {
        return new ArrayList<>(authors.values());
    }

    @Override
    public List<Author> findByName(String name) {
        return authors.values().stream()
                .filter(s -> (s.getLastName().contains(name) || s.getFirstName().contains(name)))
                .collect(Collectors.toList());
    }

    @Override
    public void createAuthor(String firstName, String lastName) {
        Author author = new Author(firstName, lastName);
        authors.put(author.getId(), author);
    }

    @Override
    public void deleteAuthorById(int id) throws AuthorDoesNotExistException {
        if (!authors.containsKey(id)) {
            throw new AuthorDoesNotExistException("Author with id: " + id + " does not exist.");
        }
        bookService = getBookService();
        loanService = getLoanService();
        bookService.findBooksByAuthorId(id).forEach(s-> {
            try {
                bookService.deleteBook(s.getId(), s.getQuantityOfAvailableBooks());
            } catch (BookDoesNotExistException e) {
                System.out.println(e.getMessage());
            }
        });


        authors.remove(id);
    }
}
