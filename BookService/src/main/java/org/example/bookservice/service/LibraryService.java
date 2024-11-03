package org.example.bookservice.service;

import lombok.AllArgsConstructor;
import org.example.bookservice.client.LibraryClient;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LibraryService
{
    private LibraryClient libraryClient;
    public void addBook(Integer id)
    {
        libraryClient.addBook(id);
    }
    public void deleteById(Integer id)
    {
        libraryClient.deleteBookById(id);
    }
}
