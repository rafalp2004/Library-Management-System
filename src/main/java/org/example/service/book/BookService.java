package org.example.service.book;

import org.example.entity.Book;
import org.example.enums.Genre;
import org.example.exceptions.AuthorDoesNotExistException;
import org.example.exceptions.BookDoesNotExistException;

import java.util.List;

public interface BookService {
    Book findById(int id) throws BookDoesNotExistException;
    List<Book> findAll();
    List<Book> findByTitle(String title);
    List<Book> findByGenre(Genre genre);
    List<Book> findBooksByAuthorId(int id) throws AuthorDoesNotExistException;
    void createBook(String title, String authorFirstName, String authorLastName, Genre genre, int quantity);
    void createBook(String title, int authorId, Genre genre, int quantity) throws AuthorDoesNotExistException;
    void deleteBook(int id, int quantity) throws BookDoesNotExistException;
}
