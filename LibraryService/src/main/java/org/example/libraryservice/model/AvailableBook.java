package org.example.libraryservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "available_books")
@AllArgsConstructor
public class AvailableBook
{
    @Id
    @GeneratedValue
    private Integer id;
    private Long bookId;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
    boolean isAvailable;
    public AvailableBook(){}
}
