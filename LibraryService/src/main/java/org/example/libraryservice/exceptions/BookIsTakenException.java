package org.example.libraryservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookIsTakenException extends RuntimeException
{
    public BookIsTakenException(String message)
    {
        super(message);
    }
}