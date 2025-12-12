package io.github.ilyalisov.common.web.dto.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RefreshRequestDto {

    @NotEmpty(
            message = "Refresh token can not be empty."
    )
    private String refreshToken;

}
