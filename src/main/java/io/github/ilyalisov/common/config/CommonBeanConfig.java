package io.github.ilyalisov.common.config;

import io.github.ilyalisov.common.web.security.JwtProperties;
import io.github.ilyalisov.jwt.service.TokenService;
import io.github.ilyalisov.jwt.service.TokenServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Autoconfiguration for common beans used across Spring Boot applications.
 * Provides default implementations for essential components like password
 * encoding and JWT token services.
 * <p>
 * This configuration is automatically activated when the library is included
 * in a Spring Boot application and the required dependencies are present.
 * </p>
 *
 * @author Ilya Lisov
 * @see JwtProperties
 * @see TokenService
 * @see PasswordEncoder
 * @since 0.1.1
 */
@Configuration
@RequiredArgsConstructor
public class CommonBeanConfig {

    private final JwtProperties jwtProperties;

    /**
     * Provides a BCrypt password encoder bean if no other PasswordEncoder
     * bean is already defined in the application context.
     * <p>
     * This bean uses the BCrypt hashing algorithm with default strength (10).
     * Applications can provide their own PasswordEncoder implementation to
     * override this default.
     * </p>
     *
     * @return BCryptPasswordEncoder instance configured with default strength
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides a JWT token service bean if no other TokenService bean is
     * already defined in the application context.
     * <p>
     * This bean is configured using JWT properties from the application
     * configuration (application.yaml or application.properties) with
     * prefix "jwt". The service requires a secret key to be configured.
     * </p>
     *
     * @return TokenService instance configured with JWT properties
     * @throws IllegalStateException if JWT secret is not configured
     * @see JwtProperties
     */
    @Bean
    @ConditionalOnMissingBean(TokenService.class)
    public TokenService tokenService() {
        if (jwtProperties.getSecret() == null
                || jwtProperties.getSecret().trim().isEmpty()) {
            throw new IllegalStateException(
                    "JWT secret must be configured in application "
                            + "properties with prefix 'jwt'"
            );
        }
        return new TokenServiceImpl(
                jwtProperties.getSecret()
        );
    }

}
