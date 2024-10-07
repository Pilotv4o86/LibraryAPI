package org.example.libraryservice.service;

import org.example.libraryservice.model.AvailableBook;
import org.example.libraryservice.repository.AvailableBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LibraryService {

    @Autowired
    private AvailableBookRepository availableBookRepository;

    // Добавить книгу в список доступных книг
    public AvailableBook addAvailableBook(Long bookId) {
        AvailableBook availableBook = new AvailableBook();
        availableBook.setBookId(bookId);
        availableBook.setAvailable(true);
        return availableBookRepository.save(availableBook);
    }

    // Обновить информацию о книге (например, когда её взяли или вернули)
    public AvailableBook updateBookInfo(Long bookId, LocalDateTime borrowedAt, LocalDateTime returnAt) {
        AvailableBook availableBook = availableBookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found") );

        availableBook.setBorrowedAt(borrowedAt);
        availableBook.setReturnedAt(returnAt);
        availableBook.setAvailable(false);  // Книга стала недоступной

        return availableBookRepository.save(availableBook);
    }

    // Получить список всех доступных книг
    public List<AvailableBook> getAvailableBooks() {
        return availableBookRepository.findAvailableBooks();
    }
}
