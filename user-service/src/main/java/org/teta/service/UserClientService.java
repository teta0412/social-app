package org.teta.service;

import dto.response.notification.NotificationUserResponse;
import dto.response.user.UserResponse;
import event.UpdateUserEvent;

import java.util.List;

public interface UserClientService {

    UserResponse getUserResponseById(Long userId);

    List<NotificationUserResponse> getUsersWhichUserSubscribed();

    List<Long> getUserIdsWhichUserSubscribed();

    List<UpdateUserEvent> getBatchUsers(Integer period, Integer page, Integer limit);
}
