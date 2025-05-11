package org.teta.service;

import org.teta.event.UpdateUserEvent;
import org.teta.event.UserNotificationDto;
import org.teta.model.User;

public interface UserHandlerService {

    User handleNewOrUpdateUser(UpdateUserEvent userEvent);

    User getOrCreateUser(UserNotificationDto user);
}
