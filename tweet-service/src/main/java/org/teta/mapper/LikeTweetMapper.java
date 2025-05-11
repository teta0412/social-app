package org.teta.mapper;

import org.teta.dto.HeaderResponse;
import org.teta.dto.response.notification.NotificationTweetResponse;
import org.teta.dto.response.tweet.TweetResponse;
import org.teta.dto.response.user.UserResponse;
import org.teta.model.Tweet;
import org.teta.repository.projection.LikeTweetProjection;
import org.teta.repository.projection.TweetProjection;
import org.teta.repository.projection.UserProjection;
import org.teta.service.LikeTweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeTweetMapper {

    private final BasicMapper basicMapper;
    private final LikeTweetService likeTweetService;

    public HeaderResponse<TweetResponse> getUserLikedTweets(Long userId, Pageable pageable) {
        Page<LikeTweetProjection> userLikedTweets = likeTweetService.getUserLikedTweets(userId, pageable);
        List<TweetProjection> tweets = new ArrayList<>();
        userLikedTweets.getContent().forEach(likeTweet -> tweets.add(likeTweet.getTweet()));
        return basicMapper.getHeaderResponse(tweets, userLikedTweets.getTotalPages(), TweetResponse.class);
    }

    public HeaderResponse<UserResponse> getLikedUsersByTweetId(Long tweetId, Pageable pageable) {
        Page<UserProjection> users = likeTweetService.getLikedUsersByTweetId(tweetId, pageable);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }

    public NotificationTweetResponse likeTweet(Long tweetId) {
        Tweet tweet = likeTweetService.likeTweet(tweetId);
        return basicMapper.convertToResponse(tweet, NotificationTweetResponse.class);
    }
}
