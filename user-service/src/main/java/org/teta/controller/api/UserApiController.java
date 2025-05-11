package org.teta.controller.api;

import constants.PathConstants;
import dto.response.notification.NotificationUserResponse;
import dto.response.user.UserResponse;
import event.UpdateUserEvent;
import org.teta.service.UserClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.API_V1_USER)
public class UserApiController {

    private final UserClientService userService;

    @GetMapping(PathConstants.USER_ID)
    public UserResponse getUserById(@PathVariable("userId") Long userId) {
        return userService.getUserResponseById(userId);
    }

    @GetMapping(PathConstants.SUBSCRIBERS)
    public List<NotificationUserResponse> getUsersWhichUserSubscribed() {
        return userService.getUsersWhichUserSubscribed();
    }

    @GetMapping(PathConstants.SUBSCRIBERS_IDS)
    public List<Long> getUserIdsWhichUserSubscribed() {
        return userService.getUserIdsWhichUserSubscribed();
    }

    @GetMapping(PathConstants.BATCH_USERS)
    public List<UpdateUserEvent> getBatchUsers(@RequestParam("period") Integer period,
                                               @RequestParam("page") Integer page,
                                               @RequestParam("limit") Integer limit) {
        return userService.getBatchUsers(period, page, limit);
    }
}
