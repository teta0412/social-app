package util;

import constants.ErrorMessage;
import constants.HeaderConstants;
import exception.ApiRequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AuthUtil {

    public static Long getAuthenticatedUserId() {
        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
        String userId = request.getHeader(HeaderConstants.AUTH_USER_ID_HEADER);

        if (userId == null) {
            throw new ApiRequestException(ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        } else {
            return Long.parseLong(userId);
        }
    }
}
