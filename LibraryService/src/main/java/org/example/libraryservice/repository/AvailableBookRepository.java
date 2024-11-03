package org.example.libraryservice.repository;

import org.example.libraryservice.model.AvailableBook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvailableBookRepository extends JpaRepository<AvailableBook, Integer>
{
    @Query("SELECT ab FROM AvailableBook ab WHERE ab.isAvailable = true")
    Optional<List<AvailableBook>> findAllByIsAvailableTrue(PageRequest pageRequest);
    Optional<AvailableBook> findByBookId(int id);
}
