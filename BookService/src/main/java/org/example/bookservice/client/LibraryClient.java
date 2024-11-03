package org.example.bookservice.client;

import org.example.bookservice.config.FeignClientConfig;
import org.example.bookservice.dto.AvailableBookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "LibraryService", configuration = FeignClientConfig.class)
public interface LibraryClient {

    @PostMapping("/library/add-book")
    AvailableBookDto addBook(Integer bookId);
    @DeleteMapping("/library/delete/{id}")
    void deleteBookById(@PathVariable Integer id);
}


