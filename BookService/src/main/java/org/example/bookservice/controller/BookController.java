package org.example.bookservice.controller;

import lombok.AllArgsConstructor;
import org.example.bookservice.model.Book;
import org.example.bookservice.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController
{
    private BookService bookService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> create(@RequestBody Book book)
    {
        return ResponseEntity.ok(bookService.save(book));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public ResponseEntity<List<Book>> findAll()
    {
        return ResponseEntity.ok(bookService.getBooks());
    }


    @PatchMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> update(@RequestBody Book book, @PathVariable Integer id)
    {
        return ResponseEntity.ok(bookService.update(id, book));
    }
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id)
    {
        bookService.delete(id);
    }
}
