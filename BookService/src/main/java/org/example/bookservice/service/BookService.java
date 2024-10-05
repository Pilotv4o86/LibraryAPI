package org.example.bookservice.service;

import org.example.bookservice.model.Book;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface BookService
{
    List<Book> getBooks();
    Book getById(Integer id);
    Book save(Book book);
    Book update(Integer id, Book book);
    void delete(Integer id);

}

