package org.example.libraryservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookUpdateRequest {
    private LocalDateTime borrowedAt;
    private LocalDateTime returnAt;
}