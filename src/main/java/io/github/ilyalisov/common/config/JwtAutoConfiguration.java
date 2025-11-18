package io.github.ilyalisov.common.config;

import io.github.ilyalisov.common.web.security.JwtProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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

    /**
     * Provides JwtProperties bean if not already defined in the application
     * context.
     * This bean will be automatically configured from application.yaml
     * properties with prefix "jwt".
     *
     * @return JwtProperties instance configured from application properties
     */
    @Bean
    @ConditionalOnMissingBean
    public JwtProperties jwtProperties() {
        return new JwtProperties(
                null,
                null,
                null,
                null,
                null
        );
    }

}
