package io.github.ilyalisov.common.web.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

/**
 * Configuration properties for JWT (JSON Web Token) security settings.
 * Maps properties with prefix "security.jwt" from application configuration
 * files (application.yaml or application.properties).
 * <p>
 * These properties are used to configure JWT token generation, validation,
 * and expiration times for different types of tokens used in the application.
 * </p>
 *
 * @author Ilya Lisov
 * @since 0.1.1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    /**
     * Secret key used for signing and verifying JWT tokens.
     * This is a required property and should be kept secure.
     * Recommended to use a long, random string for production environments.
     */
    private String secret;

    /**
     * Expiration duration for access tokens.
     * These are short-lived tokens used for API access.
     * Default format: ISO-8601 duration format (e.g., "2h", "30m", "1d")
     */
    private Duration access;

    /**
     * Expiration duration for refresh tokens.
     * These are long-lived tokens used to obtain new access tokens.
     * Default format: ISO-8601 duration format (e.g., "30d", "90d")
     */
    private Duration refresh;

    /**
     * Expiration duration for account activation tokens.
     * Used for email verification and account activation flows.
     * Default format: ISO-8601 duration format (e.g., "1d", "24h")
     */
    private Duration activation;

    /**
     * Expiration duration for password reset tokens.
     * Used for password recovery and reset functionality.
     * Default format: ISO-8601 duration format (e.g., "1h", "30m")
     */
    private Duration reset;

}
