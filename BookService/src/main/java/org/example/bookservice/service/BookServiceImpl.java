package org.example.bookservice.service;

import lombok.AllArgsConstructor;
import org.example.bookservice.client.LibraryClient;
import org.example.bookservice.model.Book;
import org.example.bookservice.repository.BookRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final LibraryClient libraryClient;
    private final BookRepository bookRepository;

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Book getById(Integer id) {
        return bookRepository.getReferenceById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Book save(Book book) {
        Book savedBook = bookRepository.save(book);
        libraryClient.addBook(savedBook.getId());

        return savedBook;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Book update(Integer id, Book book) {
        Book oldBook = bookRepository.getReferenceById(id);
        if (book.getIsbn() != null) {
            oldBook.setIsbn(book.getIsbn());
        }
        if (book.getTitle() != null) {
            oldBook.setTitle(book.getTitle());
        }
        if (book.getGenre() != null) {
            oldBook.setGenre(book.getGenre());
        }
        if (book.getDescription() != null) {
            oldBook.setDescription(book.getDescription());
        }
        if (book.getAuthor() != null) {
            oldBook.setAuthor(book.getAuthor());
        }
        return bookRepository.save(oldBook);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }
}


