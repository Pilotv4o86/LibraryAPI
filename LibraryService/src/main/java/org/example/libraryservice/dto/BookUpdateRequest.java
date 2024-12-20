package org.example.libraryservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookUpdateRequest {
    private LocalDate borrowedAt;
    private LocalDate returnAt;
}