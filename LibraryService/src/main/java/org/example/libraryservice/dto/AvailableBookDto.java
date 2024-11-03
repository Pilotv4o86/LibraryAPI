package org.example.libraryservice.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AvailableBookDto
{
    private Integer id;
    private Integer bookId;
    @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$",
            message = "Date must be in the format YYYY-MM-DD"
    )
    private LocalDate borrowedAt;
    @Pattern(
            regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$",
            message = "Date must be in the format YYYY-MM-DD"
    )
    private LocalDate returnedAt;
    boolean isAvailable;
}
