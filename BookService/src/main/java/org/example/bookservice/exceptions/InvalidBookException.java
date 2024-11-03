package org.example.bookservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBookException extends RuntimeException
{
    public InvalidBookException(String message)
    {
        super(message);
    }
}
