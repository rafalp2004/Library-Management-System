package org.example.service.loan;

import org.example.LibraryFactory;
import org.example.entity.Book;
import org.example.entity.Loan;
import org.example.entity.User;
import org.example.exceptions.BookDoesNotExistException;
import org.example.exceptions.LoanDoesNotAllowedException;
import org.example.exceptions.LoanDoesNotExistException;
import org.example.exceptions.UserDoesNotExistException;
import org.example.service.author.AuthorService;
import org.example.service.author.AuthorServiceImpl;
import org.example.service.book.BookService;
import org.example.service.book.BookServiceImpl;
import org.example.service.user.UserService;
import org.example.service.user.UserServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class LoanServiceImpl implements LoanService {

    private static LoanServiceImpl instance;

    private UserService userService;
    private BookService bookService;
    private AuthorService authorService = AuthorServiceImpl.getInstance();

    List<Loan> loans;

    private LoanServiceImpl() {

    }

    public void init(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
        this.loans = LibraryFactory.generateLoans(userService.findAll(), bookService.findAll());
    }

    public static LoanServiceImpl getInstance() {
        if (instance == null) {
            instance = new LoanServiceImpl();
        }
        return instance;
    }

    @Override
    public Loan findByUserIdAndBookId(int userId, int bookId) throws LoanDoesNotExistException, UserDoesNotExistException, BookDoesNotExistException {
        if (userService.findById(userId) == null) {
            throw new UserDoesNotExistException("User with id: " + userId + " does not exist");
        }
        if (bookService.findById(userId) == null) {
            throw new BookDoesNotExistException("Book with id: " + bookId + " does not exist");
        }
        return loans.stream()
                .filter(s -> (s.getBook().getId() == bookId && s.getUser().getId() == userId))
                .findFirst()
                .orElseThrow(() -> new LoanDoesNotExistException("Loan doesn not exist"));
    }

    @Override
    public List<Loan> findAll() {
        return loans;
    }


    @Override
    public List<Loan> findActiveLoans() {
        return loans.stream()
                .filter(s -> s.isReturned() == false)
                .collect(Collectors.toList());

    }

    @Override
    public List<Loan> findByUserId(int id) throws UserDoesNotExistException {
        if (userService.findById(id) != null) {
            return loans.stream()
                    .filter(s -> s.getUser().getId() == id)
                    .collect(Collectors.toList());
        } else throw new UserDoesNotExistException("User with id: " + id + " does not exist");

    }

    @Override
    public List<Loan> findByBookId(int id) {
        return loans.stream()
                .filter(s -> s.getBook().getId() == id)
                .collect(Collectors.toList());
    }

    //TODO Check if map in User class is nesessary. If not - remove it.
    @Override
    public void rentBook(int userId, int bookId) throws BookDoesNotExistException, UserDoesNotExistException, LoanDoesNotAllowedException {

        Book book = bookService.findById(bookId);
        User user = userService.findById(userId);
        if (book.getQuantityOfAvailableBooks() > 0) {
            loans.add(new Loan(userService.findById(userId), bookService.findById(bookId)));
            user.addBook(book);
            book.increaseRentedQuantity();
        } else {
            throw new LoanDoesNotAllowedException("There is no avaible book with id: " + book.getId());
        }
    }


    @Override
    public void returnBook(int userId, int bookId) throws LoanDoesNotExistException, UserDoesNotExistException, BookDoesNotExistException {
        Loan loan = findByUserIdAndBookId(userId, bookId);
        Book book = bookService.findById(bookId);
        if (loan.isReturned()) {
            throw new LoanDoesNotExistException("Loan does not exist");
        }

        book.decreaseRentedQuantity();
        loan.setStatus(true);

    }
}
