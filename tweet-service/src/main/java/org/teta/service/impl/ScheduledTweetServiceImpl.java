package org.teta.service.impl;

import exception.ApiRequestException;
import org.teta.constants.TweetErrorMessage;
import org.teta.constants.TweetSuccessMessage;
import org.teta.model.Tweet;
import org.teta.model.User;
import org.teta.repository.TweetRepository;
import org.teta.repository.projection.TweetProjection;
import org.teta.service.ScheduledTweetService;
import org.teta.service.TweetService;
import org.teta.service.UserService;
import org.teta.service.util.TweetServiceHelper;
import org.teta.service.util.TweetValidationHelper;
import util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledTweetServiceImpl implements ScheduledTweetService {

    private final TweetRepository tweetRepository;
    private final TweetService tweetService;
    private final TweetServiceHelper tweetServiceHelper;
    private final TweetValidationHelper tweetValidationHelper;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Page<TweetProjection> getScheduledTweets(Pageable pageable) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return tweetRepository.getScheduledTweets(authUserId, pageable);
    }

    @Override
    @Transactional
    public TweetProjection createScheduledTweet(Tweet tweet) {
        tweetValidationHelper.checkTweetTextLength(tweet.getText());
        User authUser = userService.getAuthUser();
        tweet.setAuthor(authUser);
        tweetServiceHelper.parseMetadataFromURL(tweet);
        tweetRepository.save(tweet);
        return tweetService.getTweetById(tweet.getId());
    }

    @Override
    @Transactional
    public TweetProjection updateScheduledTweet(Tweet tweetInfo) {
        Tweet tweet = tweetRepository.findById(tweetInfo.getId())
                .orElseThrow(() -> new ApiRequestException(TweetErrorMessage.TWEET_NOT_FOUND, HttpStatus.NOT_FOUND));
        tweetValidationHelper.checkTweetTextLength(tweetInfo.getText());
        tweet.setText(tweetInfo.getText());
        tweet.setImages(tweetInfo.getImages());
        return tweetService.getTweetById(tweet.getId());
    }

    @Override
    @Transactional
    public String deleteScheduledTweets(List<Long> tweetsIds) {
        tweetsIds.forEach(tweetService::deleteTweet);
        return TweetSuccessMessage.SCHEDULED_TWEETS_DELETED;
    }
}
