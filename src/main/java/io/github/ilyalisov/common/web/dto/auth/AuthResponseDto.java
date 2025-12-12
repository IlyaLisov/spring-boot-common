package io.github.ilyalisov.common.web.dto.auth;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthResponseDto {

    private String token;
    private String refreshToken;

}
