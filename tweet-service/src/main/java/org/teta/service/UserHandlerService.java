package org.teta.service;

import event.*;
import org.teta.model.User;

public interface UserHandlerService {

    User handleNewOrUpdateUser(UpdateUserEvent userEvent);

    void handleBlockUser(BlockUserEvent userEvent, String authId);

    void handleMuteUser(MuteUserEvent userEvent, String authId);

    void handleFollowUser(FollowUserEvent userEvent, String authId);

    void handleFollowUserRequest(FollowRequestUserEvent userEvent, String authId);

    void handlePinTweet(PinTweetEvent pinTweetEvent, String authId);
}
