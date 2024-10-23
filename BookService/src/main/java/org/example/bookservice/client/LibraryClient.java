package org.example.bookservice.client;

import org.example.bookservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "LibraryService", configuration = FeignClientConfig.class)
public interface LibraryClient {

    @PostMapping("/library/add-book")
    void addBook(Integer bookId);
}


