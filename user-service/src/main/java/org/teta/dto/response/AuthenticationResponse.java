package org.teta.dto.response;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private AuthUserResponse user;
    private String token;
}
