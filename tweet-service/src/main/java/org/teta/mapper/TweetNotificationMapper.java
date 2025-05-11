package org.teta.mapper;

import org.teta.dto.response.tweet.TweetResponse;
import org.teta.enums.NotificationType;

import org.teta.event.*;
import org.teta.model.Tweet;
import org.teta.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TweetNotificationMapper {

    private final BasicMapper basicMapper;

    public TweetNotificationEvent toTweetNotificationEvent(NotificationType type, Tweet tweet, User authUser, boolean condition) {
        return TweetNotificationEvent.builder()
                .notificationType(type)
                .notificationCondition(condition)
                .notifiedUser(toUserDto(tweet.getAuthor()))
                .user(toUserDto(authUser))
                .tweet(toTweetDto(tweet))
                .build();
    }

    public TweetSubscriberNotificationEvent toTweetSubscriberNotificationEvent(Tweet tweet, User authUser) {
        return TweetSubscriberNotificationEvent.builder()
                .tweet(toTweetDto(tweet))
                .user(toUserDto(authUser))
                .build();
    }

    public TweetMentionNotificationEvent toTweetMentionNotificationEvent(NotificationType notificationType, Tweet tweet,
                                                                         User user, User authUser) {
        return TweetMentionNotificationEvent.builder()
                .notificationType(notificationType)
                .notifiedUser(toUserDto(user))
                .user(toUserDto(authUser))
                .tweet(toTweetDto(tweet))
                .tweetResponse(toTweetResponse(tweet))
                .build();
    }

    private UserNotificationDto toUserDto(User user) {
        return basicMapper.convertToResponse(user, UserNotificationDto.class);
    }

    private TweetNotificationDto toTweetDto(Tweet tweet) {
        return basicMapper.convertToResponse(tweet, TweetNotificationDto.class);
    }

    private TweetResponse toTweetResponse(Tweet tweet) {
        return basicMapper.convertToResponse(tweet, TweetResponse.class);
    }
}
