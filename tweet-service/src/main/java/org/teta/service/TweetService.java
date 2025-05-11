package org.teta.service;

import dto.response.tweet.TweetResponse;
import enums.ReplyType;
import org.teta.model.Tweet;
import org.teta.model.TweetImage;
import org.teta.repository.projection.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.teta.repository.projection.*;

import java.util.List;
import java.util.Optional;

public interface TweetService {

    Page<TweetProjection> getTweets(Pageable pageable);

    TweetProjection getTweetById(Long tweetId);

    Optional<TweetUserProjection> getPinnedTweetByUserId(Long userId);

    Page<TweetUserProjection> getUserTweets(Long userId, Pageable pageable);

    Page<TweetProjection> getUserMediaTweets(Long userId, Pageable pageable);

    List<ProfileTweetImageProjection> getUserTweetImages(Long userId);

    TweetAdditionalInfoProjection getTweetAdditionalInfoById(Long tweetId);

    List<TweetProjection> getRepliesByTweetId(Long tweetId);

    Page<TweetProjection> getQuotesByTweetId(Long tweetId, Pageable pageable);

    Page<TweetProjection> getMediaTweets(Pageable pageable);

    Page<TweetProjection> getTweetsWithVideo(Pageable pageable);

    Page<TweetProjection> getFollowersTweets(Pageable pageable);

    TweetImage uploadTweetImage(MultipartFile file);

    Page<UserProjection> getTaggedImageUsers(Long tweetId, Pageable pageable);

    TweetResponse createNewTweet(Tweet tweet);

    String deleteTweet(Long tweetId);

    Page<TweetProjection> searchTweets(String text, Pageable pageable);

    TweetResponse replyTweet(Long addressedUserId, Long tweetId, Tweet reply);

    TweetResponse quoteTweet(Long tweetId, Tweet quote);

    TweetProjection changeTweetReplyType(Long tweetId, ReplyType replyType);
}
