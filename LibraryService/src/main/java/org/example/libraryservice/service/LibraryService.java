package org.example.libraryservice.service;

import lombok.AllArgsConstructor;
import org.example.libraryservice.dto.AvailableBookDto;
import org.example.libraryservice.exceptions.BookIsTakenException;
import org.example.libraryservice.exceptions.BookNotFoundException;
import org.example.libraryservice.exceptions.InvalidBorrowedTimeException;
import org.example.libraryservice.mapper.AvailableBookMapper;
import org.example.libraryservice.model.AvailableBook;
import org.example.libraryservice.dto.BookUpdateRequest;
import org.example.libraryservice.repository.AvailableBookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class LibraryService
{
    private AvailableBookRepository availableBookRepository;
    private AvailableBookMapper availableBookMapper;

    public List<AvailableBookDto> getAllBooks(Integer page,
                                              Integer size)
    {
        return availableBookRepository.findAll(PageRequest.of(page, size)).
                getContent()
                .stream()
                .map(availableBookMapper::toBookDto)
                .toList();
    }

    public AvailableBookDto addAvailableBook(Integer bookId) {
        AvailableBook availableBook = new AvailableBook();
        availableBook.setBookId(bookId);
        availableBook.setAvailable(true);
        return availableBookMapper.toBookDto(availableBookRepository.save(availableBook));
    }

    public AvailableBookDto updateBookInfo(Integer bookId,
                                           BookUpdateRequest bookUpdateRequest)
    {

        AvailableBook availableBook = availableBookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID " + bookId));

        if ((bookUpdateRequest.getBorrowedAt() != null &&
                bookUpdateRequest.getReturnAt() != null)) {
            if (bookUpdateRequest.getBorrowedAt().isAfter(bookUpdateRequest.getReturnAt())) {
                throw new InvalidBorrowedTimeException("Borrowed at before available book");
            }
        }

        availableBook.setAvailable((bookUpdateRequest.getBorrowedAt() == null &&
                bookUpdateRequest.getReturnAt() == null));
        availableBook.setBorrowedAt(bookUpdateRequest.getBorrowedAt());
        availableBook.setReturnedAt(bookUpdateRequest.getReturnAt());


        return availableBookMapper.toBookDto(availableBookRepository.save(availableBook));
    }

    public List<AvailableBookDto> getAvailableBooks(Integer page, Integer size) {
        return  availableBookRepository.findAllByIsAvailableTrue(PageRequest.of(page,size))
                .get()
                .stream()
                .map(availableBookMapper::toBookDto)
                .toList();
    }

    public void deleteBookById(Integer bookId)
    {
        AvailableBook availableBook = availableBookRepository.findByBookId(bookId).orElseThrow(() ->
                new BookNotFoundException("Book not found with ID " + bookId));
        if (!availableBook.isAvailable())
        {
            throw new BookIsTakenException("Book is taken with ID " + bookId);
        }
        availableBookRepository.deleteById(availableBook.getId());
    }
}
