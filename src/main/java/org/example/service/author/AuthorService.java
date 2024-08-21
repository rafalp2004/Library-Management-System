package org.example.service.author;

import org.example.entity.Author;
import org.example.exceptions.AuthorDoesNotExistException;

import java.util.List;

public interface AuthorService {

     Author findById(int id) throws AuthorDoesNotExistException;
     List<Author> findAll();
     List<Author> findByName(String name);
     void createAuthor(String firstName, String lastName);
     void deleteAuthorById(int i) throws AuthorDoesNotExistException;


}
