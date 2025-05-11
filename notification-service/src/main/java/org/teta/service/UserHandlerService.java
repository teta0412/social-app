package org.teta.service;

import event.UpdateUserEvent;
import event.UserNotificationDto;
import org.teta.model.User;

public interface UserHandlerService {

    User handleNewOrUpdateUser(UpdateUserEvent userEvent);

    User getOrCreateUser(UserNotificationDto user);
}
