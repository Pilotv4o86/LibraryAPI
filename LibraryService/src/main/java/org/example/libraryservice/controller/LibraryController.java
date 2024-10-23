package org.example.libraryservice.controller;

import lombok.AllArgsConstructor;
import org.example.libraryservice.model.BookUpdateRequest;
import org.example.libraryservice.model.AvailableBook;
import org.example.libraryservice.service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/library")
public class LibraryController {

    private LibraryService libraryService;

    @GetMapping("/all-books")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    public ResponseEntity<List<AvailableBook>> getAllBooks() {
        return ResponseEntity.ok(libraryService.getAllBooks());
    }

    @GetMapping("/available-books")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    public ResponseEntity<List<AvailableBook>> getAvailableBooks() {
        return ResponseEntity.ok(libraryService.getAvailableBooks());
    }

    @PutMapping("{bookId}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AvailableBook> updateBook(
            @PathVariable Integer bookId,
            @RequestBody BookUpdateRequest request) {
        AvailableBook updatedBook = libraryService.updateBookInfo(bookId, request.getBorrowedAt(), request.getReturnAt());
        return ResponseEntity.ok(updatedBook);
    }

    @PostMapping("/add-book")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AvailableBook> addBook(@RequestBody Integer bookId) {
        AvailableBook newBook = libraryService.addAvailableBook(bookId);
        return ResponseEntity.ok(newBook);
    }
}
