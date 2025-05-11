package org.teta.dto.request;

import org.teta.constants.Regexp;
import org.teta.constants.UserErrorMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordResetRequest {

    @Email(regexp = Regexp.USER_EMAIL_REGEXP, message = UserErrorMessage.EMAIL_NOT_VALID)
    private String email;

    @NotBlank(message = UserErrorMessage.EMPTY_PASSWORD)
    @Size(min = 8, message = UserErrorMessage.SHORT_PASSWORD)
    private String password;

    @NotBlank(message = UserErrorMessage.EMPTY_PASSWORD)
    @Size(min = 8, message = UserErrorMessage.SHORT_PASSWORD)
    private String password2;
}
