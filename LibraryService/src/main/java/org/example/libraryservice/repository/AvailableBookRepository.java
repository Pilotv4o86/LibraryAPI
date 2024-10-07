package org.example.libraryservice.repository;

import org.example.libraryservice.model.AvailableBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvailableBookRepository extends JpaRepository<AvailableBook, Integer>
{
    List<AvailableBook> findAllByIsAvailableTrue();
}
