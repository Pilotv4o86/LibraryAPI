package org.example.bookservice.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookUpdateDto
{
    private Integer id;

    @Pattern(
            regexp = "^(97(8|9))?\\d{9}(\\d|X)$",
            message = "Invalid ISBN format"
    )
    private String isbn;

    @Size(max = 255, message = "Title must be less than 255 characters")
    private String title;

    @Size(max = 100, message = "Genre must be less than 100 characters")
    private String genre;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @Size(max = 255, message = "Author must be less than 255 characters")
    private String author;

}
