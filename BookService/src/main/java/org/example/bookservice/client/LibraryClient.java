package org.example.bookservice.client;

import org.example.bookservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "LibraryService", configuration = FeignClientConfig.class)
public interface LibraryClient {

    @PostMapping("/library/add-book")
    void addBook(Integer bookId);
}


