package org.teta.dto.response.user;

import lombok.Data;

@Data
public class UserPrincipalResponse {
    private Long id;
    private String email;
    private String activationCode;
}
