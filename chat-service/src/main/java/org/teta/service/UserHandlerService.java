package org.teta.service;

import org.teta.event.BlockUserEvent;
import org.teta.event.FollowRequestUserEvent;
import org.teta.event.FollowUserEvent;
import org.teta.event.UpdateUserEvent;
import org.teta.model.User;

public interface UserHandlerService {

    User handleNewOrUpdateUser(UpdateUserEvent updateUserEvent);

    void handleBlockUser(BlockUserEvent blockUserEvent, String authId);

    void handleFollowUser(FollowUserEvent followUserEvent, String authId);

    void handleFollowUserRequest(FollowRequestUserEvent followRequestUserEvent, String authId);
}
