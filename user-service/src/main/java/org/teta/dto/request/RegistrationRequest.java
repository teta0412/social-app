package org.teta.dto.request;

import org.teta.constants.Regexp;
import org.teta.constants.UserErrorMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {

    @Email(regexp = Regexp.USER_EMAIL_REGEXP, message = UserErrorMessage.EMAIL_NOT_VALID)
    private String email;

    @NotBlank(message = UserErrorMessage.BLANK_NAME)
    @Size(min = 1, max = 50, message = UserErrorMessage.NAME_NOT_VALID)
    private String username;

    private String birthday;
}
