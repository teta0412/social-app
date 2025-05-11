package org.teta.dto.request;

import org.teta.constants.Regexp;
import org.teta.constants.UserErrorMessage;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ProcessEmailRequest {
    @Email(regexp = Regexp.USER_EMAIL_REGEXP, message = UserErrorMessage.EMAIL_NOT_VALID)
    private String email;
}
