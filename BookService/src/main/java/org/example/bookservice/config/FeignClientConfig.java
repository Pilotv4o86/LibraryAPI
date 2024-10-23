package org.example.bookservice.config;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignClientConfig {

    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public RequestInterceptor getRequestInterceptor() {
        return requestTemplate -> {
            // Получаем аутентификационные данные
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Проверяем, что аутентификация прошла успешно
            if (authentication != null && authentication.isAuthenticated() && authentication.getCredentials() instanceof String) {
                // Извлекаем JWT токен из credentials
                String token = authentication.getCredentials().toString();
                // Добавляем токен в заголовок Authorization
                requestTemplate.header(AUTHORIZATION_HEADER, BEARER + token);
            }
        };
    }
}

