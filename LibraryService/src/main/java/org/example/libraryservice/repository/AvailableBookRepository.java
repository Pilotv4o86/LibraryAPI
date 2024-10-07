package org.example.libraryservice.repository;

import org.example.libraryservice.model.AvailableBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailableBookRepository extends JpaRepository<AvailableBook, Long>
{
    List<AvailableBook> findAvailableBooks();
}
