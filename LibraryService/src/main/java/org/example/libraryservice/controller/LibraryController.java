package org.example.libraryservice.controller;

import lombok.AllArgsConstructor;
import org.example.libraryservice.dto.AvailableBookDto;
import org.example.libraryservice.dto.BookUpdateRequest;
import org.example.libraryservice.service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/library")
public class LibraryController {

    private LibraryService libraryService;

    @GetMapping("/all-books")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    public ResponseEntity<List<AvailableBookDto>> getAllBooks(@RequestParam(defaultValue = "0") Integer page,
                                                              @RequestParam(defaultValue = "5") Integer size)
    {
        return ResponseEntity.ok(libraryService.getAllBooks(page, size));
    }

    @GetMapping("/available-books")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    public ResponseEntity<List<AvailableBookDto>> getAvailableBooks(@RequestParam(defaultValue = "0") Integer page,
                                                                    @RequestParam(defaultValue = "5") Integer size)
    {
        return ResponseEntity.ok(libraryService.getAvailableBooks(page, size));
    }

    @PutMapping("{bookId}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AvailableBookDto> updateBook(
            @PathVariable Integer bookId,
            @Validated @RequestBody BookUpdateRequest request) {

        return ResponseEntity.ok(libraryService.updateBookInfo(bookId, request));
    }

    @PostMapping("/add-book")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AvailableBookDto> addBook(@RequestBody Integer bookId) {
        return ResponseEntity.ok(libraryService.addAvailableBook(bookId));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBookById(@PathVariable Integer id)
    {
        libraryService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }
}
