package org.example.libraryservice;

import org.example.libraryservice.dto.AvailableBookDto;
import org.example.libraryservice.mapper.AvailableBookMapper;
import org.example.libraryservice.model.AvailableBook;
import org.example.libraryservice.dto.BookUpdateRequest;
import org.example.libraryservice.repository.AvailableBookRepository;
import org.example.libraryservice.service.LibraryService;
import org.example.libraryservice.exceptions.BookIsTakenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LibraryServiceApplicationTests {

    @Mock
    private AvailableBookRepository availableBookRepository;

    @Mock
    private AvailableBookMapper availableBookMapper;

    @InjectMocks
    private LibraryService libraryService;


    private AvailableBook availableBook;
    private AvailableBookDto availableBookDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        availableBook = new AvailableBook();
        availableBook.setId(1);
        availableBook.setBookId(123);
        availableBook.setAvailable(true);

        availableBookDto = new AvailableBookDto();
        availableBookDto.setId(1);
        availableBookDto.setBookId(123);
        availableBookDto.setAvailable(true);
    }

    @Test
    public void testGetAllBooks() {
        Page<AvailableBook> page = new PageImpl<>(List.of(availableBook));
        when(availableBookRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);
        when(availableBookMapper.toBookDto(availableBook)).thenReturn(availableBookDto);

        List<AvailableBookDto> result = libraryService.getAllBooks(0, 10);

        assertEquals(1, result.size());
        assertEquals(availableBookDto, result.getFirst());
    }

    @Test
    public void testAddAvailableBook() {
        when(availableBookRepository.save(any(AvailableBook.class))).thenReturn(availableBook);
        when(availableBookMapper.toBookDto(availableBook)).thenReturn(availableBookDto);

        AvailableBookDto result = libraryService.addAvailableBook(123);

        assertEquals(availableBookDto, result);
        assertTrue(result.isAvailable());
    }

    @Test
    public void testUpdateBookInfo() {
        BookUpdateRequest updateRequest = new BookUpdateRequest();
        updateRequest.setBorrowedAt(LocalDate.now().minusDays(1));
        updateRequest.setReturnAt(LocalDate.now().plusDays(1));

        when(availableBookRepository.findById(1)).thenReturn(Optional.of(availableBook));
        when(availableBookMapper.toBookDto(availableBook)).thenReturn(availableBookDto);
        when(availableBookRepository.save(availableBook)).thenReturn(availableBook);

        AvailableBookDto result = libraryService.updateBookInfo(1, updateRequest);

        assertNotNull(result);
        assertFalse(availableBook.isAvailable());
    }

    @Test
    public void testGetAvailableBooks()
    {
        List<AvailableBook> availableBooks = List.of(availableBook);
        when(availableBookRepository.findAllByIsAvailableTrue(PageRequest.of(0, 10)))
                .thenReturn(Optional.of(availableBooks));
        when(availableBookMapper.toBookDto(availableBook)).thenReturn(availableBookDto);

        List<AvailableBookDto> result = libraryService.getAvailableBooks(0, 10);

        assertEquals(1, result.size());
        assertEquals(availableBookDto, result.get(0));
    }

    @Test
    public void testDeleteBookById_BookIsTaken() {
        when(availableBookRepository.findByBookId(123)).thenReturn(Optional.of(availableBook));
        availableBook.setAvailable(false); // simulate that the book is taken

        assertThrows(BookIsTakenException.class, () -> libraryService.deleteBookById(123));
    }

    @Test
    public void testDeleteBookById_Success() {
        when(availableBookRepository.findByBookId(123)).thenReturn(Optional.of(availableBook));

        libraryService.deleteBookById(123);

        verify(availableBookRepository).deleteById(availableBook.getId());
    }
}
