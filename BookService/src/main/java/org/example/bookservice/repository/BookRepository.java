package org.example.bookservice.repository;

import org.example.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Integer>
{
    Optional<Book> findByIsbn(String isbn);
}
