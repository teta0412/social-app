package org.teta.mapper;

import org.teta.dto.HeaderResponse;
import org.teta.dto.response.notification.NotificationTweetResponse;
import org.teta.dto.response.tweet.TweetResponse;
import org.teta.dto.response.user.UserResponse;
import org.teta.model.Tweet;
import org.teta.repository.projection.TweetUserProjection;
import org.teta.repository.projection.UserProjection;
import org.teta.service.RetweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetweetMapper {

    private final BasicMapper basicMapper;
    private final RetweetService retweetService;

    public HeaderResponse<TweetResponse> getUserRetweetsAndReplies(Long userId, Pageable pageable) {
        Page<TweetUserProjection> tweets = retweetService.getUserRetweetsAndReplies(userId, pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public HeaderResponse<UserResponse> getRetweetedUsersByTweetId(Long tweetId, Pageable pageable) {
        Page<UserProjection> users = retweetService.getRetweetedUsersByTweetId(tweetId, pageable);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }

    public NotificationTweetResponse retweet(Long tweetId) {
        Tweet tweet = retweetService.retweet(tweetId);
        return basicMapper.convertToResponse(tweet, NotificationTweetResponse.class);
    }
}
