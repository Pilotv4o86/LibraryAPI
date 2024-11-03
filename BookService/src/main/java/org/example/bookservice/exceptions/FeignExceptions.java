package org.example.bookservice.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.ForbiddenException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FeignExceptions implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        String errorMessage = extractErrorMessage(response);

        return switch (response.status()) {
            case 404 -> new BookNotFoundException(errorMessage);
            case 409 -> new BookIsTakenException(errorMessage);
            default -> new RuntimeException(errorMessage);
        };
    }

    private String extractErrorMessage(Response response) {
        if (response.body() != null) {
            try (InputStream inputStream = response.body().asInputStream()) {
                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                return "Unknown error";
            }
        }
        return "No error message";
    }
}
