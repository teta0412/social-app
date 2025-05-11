package org.teta.service;

import org.teta.model.Tweet;
import org.teta.repository.projection.LikeTweetProjection;
import org.teta.repository.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikeTweetService {

    Page<LikeTweetProjection> getUserLikedTweets(Long userId, Pageable pageable);

    Page<UserProjection> getLikedUsersByTweetId(Long tweetId, Pageable pageable);

    Tweet likeTweet(Long tweetId);
}
