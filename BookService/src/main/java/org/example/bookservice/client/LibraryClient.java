package org.example.bookservice.client;

import org.example.bookservice.model.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "LibraryService")
public interface LibraryClient
{
    @PostMapping("/add-book")
    void addBook(Integer bookId);
}
