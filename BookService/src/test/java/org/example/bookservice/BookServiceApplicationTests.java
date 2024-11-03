package org.example.bookservice;

import org.example.bookservice.dto.BookDto;
import org.example.bookservice.dto.BookUpdateDto;
import org.example.bookservice.exceptions.BookNotFoundException;
import org.example.bookservice.exceptions.DuplicateIsbnException;
import org.example.bookservice.exceptions.InvalidBookException;
import org.example.bookservice.mapper.BookMapper;
import org.example.bookservice.model.Book;
import org.example.bookservice.repository.BookRepository;
import org.example.bookservice.service.BookService;
import org.example.bookservice.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceApplicationTests {

    @Mock
    private LibraryService libraryService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDto bookDto;
    private BookUpdateDto bookDto2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book();
        book.setId(1);
        book.setIsbn("1234567890");

        bookDto = new BookDto();
        bookDto.setId(1);
        bookDto.setIsbn("1234567890");

        bookDto2 = new BookUpdateDto();
        bookDto2.setIsbn("1234567891");
    }

    @Test
    public void testGetBooks() {
        Page<Book> page = new PageImpl<>(List.of(book));
        when(bookRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);
        when(bookMapper.toBookDto(book)).thenReturn(bookDto);
        List<BookDto> result = bookService.getBooks(0, 10);
        assertEquals(1, result.size());
        assertEquals(bookDto, result.get(0));
    }

    @Test
    public void testCreate_BindingResultHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of());
        assertThrows(InvalidBookException.class, () -> bookService.create(bookDto, bindingResult));
    }

    @Test
    public void testCreate_DuplicateISBN() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookMapper.toBook(bookDto)).thenReturn(book);
        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(Optional.of(book));
        assertThrows(DuplicateIsbnException.class, () -> bookService.create(bookDto, bindingResult));
    }

    @Test
    public void testCreate_Success() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookMapper.toBook(bookDto)).thenReturn(book);
        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toBookDto(book)).thenReturn(bookDto);
        BookDto result = bookService.create(bookDto, bindingResult);
        assertEquals(bookDto, result);
    }

    @Test
    public void testUpdate_BindingResultHasErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of());
        assertThrows(InvalidBookException.class, () -> bookService.update(1, bookDto2, bindingResult));
    }

    @Test
    public void testUpdate_BookNotFound() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.update(1, bookDto2, bindingResult));
    }

    @Test
    public void testUpdate_Success() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        bookService.update(1, bookDto2, bindingResult);
        verify(bookMapper).copyFields(book, bookDto2);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toBookDto(book)).thenReturn(bookDto);
        BookDto result = bookService.update(1, bookDto2, bindingResult);
        assertEquals(bookDto, result);
    }

    @Test
    public void testDelete() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        doNothing().when(libraryService).deleteById(1);
        bookService.delete(1);
        verify(bookRepository).deleteById(1);
    }

    @Test
    public void testGetById_BookNotFound() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.getById(1));
    }

    @Test
    public void testGetById_Success() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(bookMapper.toBookDto(book)).thenReturn(bookDto);
        BookDto result = bookService.getById(1);
        assertEquals(bookDto, result);
    }

    @Test
    public void testGetByIsbn_BookNotFound() {
        when(bookRepository.findByIsbn("1234567890")).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.getByIsbn("1234567890"));
    }

    @Test
    public void testGetByIsbn_Success() {
        when(bookRepository.findByIsbn("1234567890")).thenReturn(Optional.of(book));
        when(bookMapper.toBookDto(book)).thenReturn(bookDto);
        BookDto result = bookService.getByIsbn("1234567890");
        assertEquals(bookDto, result);
    }
}
