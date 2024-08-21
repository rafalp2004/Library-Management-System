package org.example.service.user;

import org.example.entity.Book;
import org.example.entity.User;
import org.example.exceptions.UserDoesNotExistException;

import java.util.List;

public interface UserService {
    User findById(int id) throws UserDoesNotExistException;
    List<User> findAll();
    List<User> findByName(String name);
    List<Book> getRentedBooksByUserId(int id);
    void createUser(String firstName, String lastName);
    void deleteUser(int id) throws UserDoesNotExistException;

}
