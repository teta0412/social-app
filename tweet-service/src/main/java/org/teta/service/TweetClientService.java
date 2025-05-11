package org.teta.service;

import org.teta.dto.request.IdsRequest;
import org.teta.model.Tweet;
import org.teta.repository.projection.ChatTweetProjection;
import org.teta.repository.projection.TweetProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TweetClientService {

    List<TweetProjection> getTweetsByIds(IdsRequest requests);

    Page<TweetProjection> getTweetsByUserIds(IdsRequest request, Pageable pageable);

    TweetProjection getTweetById(Long tweetId);

    Boolean isTweetExists(Long tweetId);

    Long getTweetCountByText(String text);

    ChatTweetProjection getChatTweet(Long tweetId);

    List<Tweet> getBatchTweets(Integer period, Integer page, Integer limit);
}
