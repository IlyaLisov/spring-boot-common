package io.github.ilyalisov.common.web.dto.auth;

import io.github.ilyalisov.common.web.dto.ValidationScopesKt;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class ResetRequestDto {

    @Length(
            min = 1,
            max = ValidationScopesKt.MAX_FIELD_LENGTH,
            message = "Username length must be in {min} - {max} symbols."
    )
    @NotEmpty(
            message = "Username can not be empty."
    )
    private String username;

    @Length(
            min = 1,
            max = ValidationScopesKt.MAX_FIELD_LENGTH,
            message = "Password length must be in {min} - {max} symbols."
    )
    @NotEmpty(
            message = "Password can not be empty."
    )
    private String newPassword;

}
