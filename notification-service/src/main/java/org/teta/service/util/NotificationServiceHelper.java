package org.teta.service.util;

import org.teta.client.TweetClient;
import org.teta.client.UserClient;
import org.teta.dto.response.tweet.TweetResponse;
import org.teta.dto.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationServiceHelper {

    private final UserClient userClient;
    private final TweetClient tweetClient;

    public UserResponse getUserById(Long userId) {
        return userClient.getUserById(userId);
    }

    public TweetResponse getTweetById(Long tweetId) {
        return tweetClient.getTweetById(tweetId);
    }
}
