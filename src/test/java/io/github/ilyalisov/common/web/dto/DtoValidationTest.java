package io.github.ilyalisov.common.web.dto;

import io.github.ilyalisov.common.web.dto.auth.ActivateRequestDto;
import io.github.ilyalisov.common.web.dto.auth.AuthRequestDto;
import io.github.ilyalisov.common.web.dto.auth.RefreshRequestDto;
import io.github.ilyalisov.common.web.dto.auth.ResetRequestDto;
import io.github.ilyalisov.common.web.dto.auth.RestoreRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void authRequestDto_shouldBeValid_whenFieldsAreCorrect() {
        AuthRequestDto dto = new AuthRequestDto();
        dto.setUsername("validUser");
        dto.setPassword("validPassword");
        Set<ConstraintViolation<AuthRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void authRequestDto_shouldBeInvalid_whenUsernameIsEmpty() {
        AuthRequestDto dto = new AuthRequestDto();
        dto.setUsername("");
        dto.setPassword("validPassword");
        Set<ConstraintViolation<AuthRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath()
                        .toString()
                        .equals("username"))
        );
    }

    @Test
    void authRequestDto_shouldBeInvalid_whenPasswordIsEmpty() {
        AuthRequestDto dto = new AuthRequestDto();
        dto.setUsername("validUser");
        dto.setPassword("");
        Set<ConstraintViolation<AuthRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath()
                        .toString()
                        .equals("password"))
        );
    }

    @Test
    void authRequestDto_shouldBeInvalid_whenUsernameIsTooLong() {
        AuthRequestDto dto = new AuthRequestDto();
        dto.setUsername("a".repeat(ValidationScopesKt.MAX_FIELD_LENGTH + 1));
        dto.setPassword("validPassword");
        Set<ConstraintViolation<AuthRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath()
                        .toString()
                        .equals("username"))
        );
    }

    @Test
    void restoreRequestDto_shouldBeValid_whenUsernameIsCorrect() {
        RestoreRequestDto dto = new RestoreRequestDto();
        dto.setUsername("validUser");
        Set<ConstraintViolation<RestoreRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void restoreRequestDto_shouldBeInvalid_whenUsernameIsEmpty() {
        RestoreRequestDto dto = new RestoreRequestDto();
        dto.setUsername("");
        Set<ConstraintViolation<RestoreRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath()
                        .toString()
                        .equals("username"))
        );
    }

    @Test
    void activateRequestDto_shouldBeValid_whenFieldsAreCorrect() {
        ActivateRequestDto dto = new ActivateRequestDto();
        dto.setActivateToken("validToken");
        Set<ConstraintViolation<ActivateRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void activateRequestDto_shouldBeInvalid_whenTokenIsEmpty() {
        ActivateRequestDto dto = new ActivateRequestDto();
        dto.setActivateToken("");
        Set<ConstraintViolation<ActivateRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath()
                        .toString()
                        .equals("activateToken"))
        );
    }

    @Test
    void refreshRequestDto_shouldBeValid_whenFieldsAreCorrect() {
        RefreshRequestDto dto = new RefreshRequestDto();
        dto.setRefreshToken("validToken");
        Set<ConstraintViolation<RefreshRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void refreshRequestDto_shouldBeInvalid_whenTokenIsEmpty() {
        RefreshRequestDto dto = new RefreshRequestDto();
        dto.setRefreshToken("");
        Set<ConstraintViolation<RefreshRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath()
                        .toString()
                        .equals("refreshToken"))
        );
    }

    @Test
    void resetRequestDto_shouldBeValid_whenFieldsAreCorrect() {
        ResetRequestDto dto = new ResetRequestDto();
        dto.setResetToken("validToken");
        dto.setNewPassword("validPassword");
        Set<ConstraintViolation<ResetRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void resetRequestDto_shouldBeInvalid_whenFieldsAreEmpty() {
        ResetRequestDto dto = new ResetRequestDto();
        dto.setResetToken("");
        dto.setNewPassword("");
        Set<ConstraintViolation<ResetRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath()
                        .toString()
                        .equals("resetToken"))
        );
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath()
                        .toString()
                        .equals("newPassword"))
        );
    }

}
