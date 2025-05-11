package org.teta.service;

import event.BlockUserEvent;
import event.FollowRequestUserEvent;
import event.FollowUserEvent;
import event.UpdateUserEvent;
import org.teta.model.User;

public interface UserHandlerService {

    User handleNewOrUpdateUser(UpdateUserEvent updateUserEvent);

    void handleBlockUser(BlockUserEvent blockUserEvent, String authId);

    void handleFollowUser(FollowUserEvent followUserEvent, String authId);

    void handleFollowUserRequest(FollowRequestUserEvent followRequestUserEvent, String authId);
}
