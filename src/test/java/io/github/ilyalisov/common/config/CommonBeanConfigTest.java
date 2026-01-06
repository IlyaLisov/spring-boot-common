package io.github.ilyalisov.common.config;

import io.github.ilyalisov.jwt.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

class CommonBeanConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(
                    CommonBeanConfig.class,
                    JwtAutoConfiguration.class
            ));

    @Test
    void shouldRegisterTokenServiceWhenSecretIsProvided() {
        contextRunner
                .withPropertyValues("security.jwt.secret=secret-key-that-is-at-least-32-characters-long")
                .run(context -> assertThat(context)
                        .hasSingleBean(TokenService.class));
    }

    @Test
    void shouldThrowExceptionWhenSecretIsMissing() {
        contextRunner
                .run(context -> {
                    assertThat(context).hasFailed();
                    assertThat(context.getStartupFailure())
                            .isInstanceOf(BeanCreationException.class)
                            .hasMessageContaining("JWT secret must be configured");
                });
    }

    @Test
    void shouldThrowExceptionWhenSecretIsBlank() {
        contextRunner
                .withPropertyValues("security.jwt.secret=   ")
                .run(context -> {
                    assertThat(context).hasFailed();
                    assertThat(context.getStartupFailure())
                            .isInstanceOf(BeanCreationException.class)
                            .hasMessageContaining("JWT secret must be configured");
                });
    }

}
