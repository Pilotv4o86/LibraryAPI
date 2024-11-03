package org.example.bookservice.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.bookservice.dto.BookDto;
import org.example.bookservice.dto.BookUpdateDto;
import org.example.bookservice.exceptions.BookNotFoundException;
import org.example.bookservice.exceptions.DuplicateIsbnException;
import org.example.bookservice.exceptions.InvalidBookException;
import org.example.bookservice.mapper.BookMapper;
import org.example.bookservice.model.Book;
import org.example.bookservice.repository.BookRepository;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import javax.print.attribute.standard.PageRanges;
import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Validated
public class BookService {

    private final LibraryService libraryService;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDto> getBooks(Integer page, Integer size)
    {
        return bookRepository.findAll(PageRequest.of(page, size)).stream()
                .map(bookMapper::toBookDto).toList();
    }

    public BookDto create(@Valid BookDto bookDto,
                          BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new InvalidBookException(bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", ")));
        }

        Book book = bookMapper.toBook(bookDto);

        if (bookRepository.findByIsbn(book.getIsbn()).isPresent())
        {
            throw new DuplicateIsbnException("ISBN " + book.getIsbn() + " already exists");
        }

        bookRepository.save(book);
        libraryService.addBook(book.getId());
        return bookMapper.toBookDto(book);
    }

    public BookDto update(Integer id,
                          @Valid BookUpdateDto bookRequest,
                          BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            throw new InvalidBookException(bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", ")));
        }

        Book oldBook = bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Book not found with ID " + id));
        bookMapper.copyFields(oldBook, bookRequest);

        return bookMapper.toBookDto(bookRepository.save(oldBook));
    }

    public void delete(Integer id)
    {
        libraryService.deleteById(id);
        bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Book not found with ID " + id));
        bookRepository.deleteById(id);
    }

    public BookDto getById(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Book not found with ID " + id));
        return bookMapper.toBookDto(book);
    }

    public BookDto getByIsbn(String isbn)
    {
        return bookMapper.toBookDto(bookRepository.findByIsbn(isbn).orElseThrow(() ->
                new BookNotFoundException("Book not found with ISBN " + isbn)));
    }
}