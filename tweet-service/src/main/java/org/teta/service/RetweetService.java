package org.teta.service;

import org.teta.model.Tweet;
import org.teta.repository.projection.TweetUserProjection;
import org.teta.repository.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RetweetService {

    Page<TweetUserProjection> getUserRetweetsAndReplies(Long userId, Pageable pageable);

    Page<UserProjection> getRetweetedUsersByTweetId(Long tweetId, Pageable pageable);

    Tweet retweet(Long tweetId);
}
