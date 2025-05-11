package org.teta.client;

import org.teta.configuration.FeignConfiguration;
import org.teta.constants.FeignConstants;
import org.teta.constants.PathConstants;
import org.teta.dto.response.notification.NotificationUserResponse;
import org.teta.dto.response.user.UserResponse;
import org.teta.event.UpdateUserEvent;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@CircuitBreaker(name = FeignConstants.USER_SERVICE)
@FeignClient(name = FeignConstants.USER_SERVICE, path = PathConstants.API_V1_USER, contextId = "UserClient", configuration = FeignConfiguration.class)
public interface UserClient {

    @GetMapping(PathConstants.USER_ID)
    UserResponse getUserById(@PathVariable("userId") Long userId);

    @GetMapping(PathConstants.SUBSCRIBERS)
    List<NotificationUserResponse> getUsersWhichUserSubscribed();

    @GetMapping(PathConstants.SUBSCRIBERS_IDS)
    List<Long> getUserIdsWhichUserSubscribed();

    @GetMapping(PathConstants.BATCH_USERS)
    List<UpdateUserEvent> getBatchUsers(@RequestParam("period") Integer period,
                                        @RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit);
}
