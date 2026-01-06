package io.github.ilyalisov.common.config;

import io.github.ilyalisov.common.web.security.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for JWT properties and related beans.
 *
 * @author Ilya Lisov
 * @since 0.1.1
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration {
}
