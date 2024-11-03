package org.example.bookservice.config;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.example.bookservice.exceptions.FeignExceptions;
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
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated() && authentication.getCredentials() instanceof String) {
                String token = authentication.getCredentials().toString();
                requestTemplate.header(AUTHORIZATION_HEADER, BEARER + token);
            }
        };
    }

    @Bean
    public ErrorDecoder errorDecoder()
    {
        return new FeignExceptions();
    }
}

