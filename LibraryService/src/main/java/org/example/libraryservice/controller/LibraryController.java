package org.example.libraryservice.controller;

import org.example.libraryservice.dto.BookUpdateRequest;
import org.example.libraryservice.model.AvailableBook;
import org.example.libraryservice.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/library")
public class LibraryController
{
    @Autowired
    private LibraryService libraryService;

    // Получение списка свободных книг
    @GetMapping("/available-books")
    public List<AvailableBook> getAvailableBooks() {
        return libraryService.getAvailableBooks();
    }

    // Изменение информации о книге (например, при взятии книги)
    @PutMapping("{bookId}/update")
    public ResponseEntity<AvailableBook> updateBook(
            @PathVariable Long bookId,
            @RequestBody BookUpdateRequest request) {

        AvailableBook updatedBook = libraryService.updateBookInfo(bookId, request.getBorrowedAt(), request.getReturnAt());
        return ResponseEntity.ok(updatedBook);
    }

    // Добавление новой книги в список доступных книг (вызовется первым сервисом)
    @PostMapping("/add-book")
    public ResponseEntity<AvailableBook> addBook(@RequestBody Long bookId) {
        AvailableBook newBook = libraryService.addAvailableBook(bookId);
        return ResponseEntity.ok(newBook);
    }
}
