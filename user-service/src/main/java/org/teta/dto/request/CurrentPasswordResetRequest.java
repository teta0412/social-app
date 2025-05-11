package org.teta.dto.request;

import org.teta.constants.UserErrorMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CurrentPasswordResetRequest {
    
    @NotBlank(message = UserErrorMessage.EMPTY_CURRENT_PASSWORD)
    private String currentPassword;
    
    @NotBlank(message = UserErrorMessage.EMPTY_PASSWORD)
    @Size(min = 8, message = UserErrorMessage.SHORT_PASSWORD)
    private String password;
    
    @NotBlank(message = UserErrorMessage.EMPTY_PASSWORD_CONFIRMATION)
    @Size(min = 8, message = UserErrorMessage.SHORT_PASSWORD)
    private String password2;
}
