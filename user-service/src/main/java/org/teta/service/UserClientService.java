package org.teta.service;

import org.teta.dto.response.notification.NotificationUserResponse;
import org.teta.dto.response.user.UserResponse;
import org.teta.event.UpdateUserEvent;

import java.util.List;

public interface UserClientService {

    UserResponse getUserResponseById(Long userId);

    List<NotificationUserResponse> getUsersWhichUserSubscribed();

    List<Long> getUserIdsWhichUserSubscribed();

    List<UpdateUserEvent> getBatchUsers(Integer period, Integer page, Integer limit);
}
