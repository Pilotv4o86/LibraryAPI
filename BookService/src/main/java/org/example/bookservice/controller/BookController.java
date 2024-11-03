package org.example.bookservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.bookservice.dto.BookDto;
import org.example.bookservice.dto.BookUpdateDto;
import org.example.bookservice.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/books")
public class BookController
{
    private BookService bookService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody BookDto bookDto,
                                    BindingResult bindingResult)
    {

        BookDto savedBookDto = bookService.create(bookDto, bindingResult);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBookDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedBookDto);
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public ResponseEntity<List<BookDto>> findAll(@RequestParam(defaultValue = "0") Integer page,
                                                 @RequestParam(defaultValue = "5") Integer size)
    {
        return ResponseEntity.ok(bookService.getBooks(page,size));
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookDto> update(@PathVariable Integer id,
                                          @RequestBody BookUpdateDto bookUpdateDto,
                                          BindingResult bindingResult)
    {
        return ResponseEntity.accepted().body(bookService.update(id, bookUpdateDto, bindingResult));
    }
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id)
    {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public ResponseEntity<BookDto> getById(@PathVariable Integer id)
    {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @GetMapping("/{isbn}/isbn")
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public ResponseEntity<BookDto> getByIsbn(@PathVariable String isbn)
    {
        return ResponseEntity.ok(bookService.getByIsbn(isbn));
    }
}
