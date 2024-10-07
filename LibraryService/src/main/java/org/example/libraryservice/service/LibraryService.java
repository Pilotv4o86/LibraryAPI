package org.example.libraryservice.service;

import org.example.libraryservice.model.AvailableBook;
import org.example.libraryservice.repository.AvailableBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private AvailableBookRepository availableBookRepository;


    public List<AvailableBook> getAllBooks()
    {
        return availableBookRepository.findAll();
    }

    // Добавить книгу в список доступных книг
    public AvailableBook addAvailableBook(Integer bookId) {
        AvailableBook availableBook = new AvailableBook();
        availableBook.setBookId(bookId);
        availableBook.setAvailable(true);
        return availableBookRepository.save(availableBook);
    }

    // Обновить информацию о книге (например, когда её взяли или вернули)
    public AvailableBook updateBookInfo(Integer bookId, LocalDate borrowedAt, LocalDate returnAt) {
        AvailableBook availableBook = availableBookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found") );

        availableBook.setAvailable((borrowedAt == null && returnAt == null));  // Книга стала недоступной
        availableBook.setBorrowedAt(borrowedAt);
        availableBook.setReturnedAt(returnAt);


        return availableBookRepository.save(availableBook);
    }

    // Получить список всех доступных книг
    public List<AvailableBook> getAvailableBooks() {
        return availableBookRepository.findAllByIsAvailableTrue();
    }
}
