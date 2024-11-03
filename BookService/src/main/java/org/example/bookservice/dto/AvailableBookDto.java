package org.example.bookservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailableBookDto
{
    private Integer id;
    private Integer bookId;
    private LocalDate borrowedAt;
    private LocalDate returnedAt;
    boolean isAvailable;

}
