package org.example.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AuthResponse implements Serializable
{
    private String username;
    private String token;
}
