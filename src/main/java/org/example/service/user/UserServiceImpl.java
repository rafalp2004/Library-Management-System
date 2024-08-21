package org.example.service.user;

import org.example.LibraryFactory;
import org.example.entity.Book;
import org.example.entity.User;
import org.example.enums.Status;
import org.example.exceptions.UserDoesNotExistException;
import org.example.service.author.AuthorService;
import org.example.service.book.BookServiceImpl;
import org.example.service.loan.LoanServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance;
    private LoanServiceImpl loanService;
    private BookServiceImpl bookService;
    private List<User> users;

    private UserServiceImpl() {
        users = LibraryFactory.generateUsers(50);
        loanService = LoanServiceImpl.getInstance();
        bookService = BookServiceImpl.getInstance();
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
            LoanServiceImpl.getInstance().init(instance, BookServiceImpl.getInstance());
        }
        return instance;
    }

    @Override
    public User findById(int id) throws UserDoesNotExistException {
        return users.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UserDoesNotExistException("User with id " + id + " does not exist"));
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public List<User> findByName(String name) {
        return users.stream()
                .filter(s -> (s.getFirstName().contains(name) || s.getLastName().contains(name)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getRentedBooksByUserId(int id) {
        try {
            User user = findById(id);
            return user.getRentedBooks()
                    .entrySet()
                    .stream()
                    .filter(s -> s.getValue() == Status.RENTED)
                    .map(s -> s.getKey())
                    .collect(Collectors.toList());

        } catch (UserDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUser(String firstName, String lastName) {
        users.add(new User(firstName, lastName));

    }

    //TODO Ensure that the users returned all books.
    @Override
    public void deleteUser(int id) throws UserDoesNotExistException {
        User user = users.stream().filter(s -> s.getId() == id).findFirst().orElseThrow(() -> new UserDoesNotExistException("User with id: " + id + " does not exist"));
        loanService.findByUserId(id).stream().filter(s->s.isReturned()==false).forEach(s->{s.getBook().decreaseRentedQuantity(); bookService.decreaseQuantity(s.getBook().getId()); });
        loanService.findAll().removeIf(s->s.getUser().getId()==id);
        users.remove(user);
    }
}
