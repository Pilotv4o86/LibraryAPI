package org.example.bookservice.controller;

import lombok.AllArgsConstructor;
import org.example.bookservice.model.Book;
import org.example.bookservice.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController
{

    private BookService bookService;

    @PostMapping("/create")
    public Book create(@RequestBody Book book)
    {
        return bookService.save(book);
    }

    @GetMapping("/list")
    public List<Book> findAll()
    {
        return bookService.getBooks();
    }

    @PatchMapping("/{id}/update")
    public Book update(@RequestBody Book book, @PathVariable Integer id)
    {
        return bookService.update(id, book);
    }
    @DeleteMapping("/{id}/delete")
    public void detele(@PathVariable Integer id)
    {
        bookService.delete(id);
    }
}
