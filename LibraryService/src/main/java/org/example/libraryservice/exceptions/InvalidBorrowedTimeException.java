package org.example.libraryservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBorrowedTimeException extends RuntimeException
{
    public InvalidBorrowedTimeException(String message)
    {
        super(message);
    }
}
