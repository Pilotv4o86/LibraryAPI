package org.example.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final Key key; // Секретный ключ

    // Конструктор для получения ключа из конфигурации
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); // Генерация ключа из строки
    }

    // Извлекаем username (или другой subject) из токена
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Универсальный метод для извлечения любого поля (claim) из токена
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Извлекаем все claims (полезно для кастомных полей)
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Используем сам ключ, а не его байтовое представление
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Генерация токена с кастомными claims
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims) // Кастомные claims
                .setSubject(userDetails.getUsername()) // Имя пользователя
                .setIssuedAt(new Date(System.currentTimeMillis())) // Время создания токена
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 час
                .signWith(key, SignatureAlgorithm.HS256) // Подпись с использованием ключа и алгоритма HS256
                .compact();
    }

    // Генерация токена с минимальным набором данных (например, только username)
    public String generateToken(UserDetails userDetails) {
        return generateToken(Map.of(), userDetails); // Без кастомных claims
    }

    // Валидация токена: проверка имени пользователя и срока действия токена
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Извлекаем username из токена
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); // Сравнение с UserDetails
    }

    // Проверка истечения срока действия токена
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date()); // Проверка, истек ли токен
    }
}
