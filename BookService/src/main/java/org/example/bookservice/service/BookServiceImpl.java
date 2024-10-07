package org.example.bookservice.service;

import lombok.AllArgsConstructor;
import org.example.bookservice.model.Book;
import org.example.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService
{

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private BookRepository bookRepository;
    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(Integer id) {
        return bookRepository.getReferenceById(id);
    }

    @Override
    public Book save(Book book) {
        Book savedBook = bookRepository.save(book);
        String libraryServiceUrl = "http://localhost:8081/library/add-book";
        restTemplate.postForObject(libraryServiceUrl, savedBook.getId(), Void.class);

        return savedBook;
    }

    @Override
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
    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }


}
