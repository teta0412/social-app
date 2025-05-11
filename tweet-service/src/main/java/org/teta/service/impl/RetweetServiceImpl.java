package org.teta.service.impl;

import org.teta.broker.producer.TweetNotificationProducer;
import enums.NotificationType;
import enums.TweetType;
import org.teta.model.Tweet;
import org.teta.model.User;
import org.teta.broker.producer.UpdateTweetCountProducer;
import org.teta.repository.TweetRepository;
import org.teta.repository.projection.TweetUserProjection;
import org.teta.repository.projection.UserProjection;
import org.teta.service.RetweetService;
import org.teta.service.UserService;
import org.teta.service.util.TweetValidationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RetweetServiceImpl implements RetweetService {

    private final TweetRepository tweetRepository;
    private final TweetValidationHelper tweetValidationHelper;
    private final UpdateTweetCountProducer updateTweetCountProducer;
    private final TweetNotificationProducer tweetNotificationProducer;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Page<TweetUserProjection> getUserRetweetsAndReplies(Long userId, Pageable pageable) {
        tweetValidationHelper.validateUserProfile(userId);
        return tweetRepository.getRetweetsAndRepliesByUserId(userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserProjection> getRetweetedUsersByTweetId(Long tweetId, Pageable pageable) {
        Tweet tweet = tweetValidationHelper.checkValidTweet(tweetId);
        return userService.getRetweetedUsersByTweet(tweet, pageable);
    }

    @Override
    @Transactional
    public Tweet retweet(Long tweetId) {
        Tweet tweet = tweetValidationHelper.checkValidTweet(tweetId);
        User authUser = userService.getAuthUser();
        Optional<Tweet> tweetRetweeted = tweetRepository.getTweetRetweeted(authUser, tweet);
        boolean isRetweeted;

        if (tweetRetweeted.isPresent()) {
            tweetRepository.delete(tweetRetweeted.get());
            isRetweeted = false;
        } else {
            Tweet newRetweet = new Tweet();
            newRetweet.setText(String.format("RT: %s", tweet.getText()));
            newRetweet.setTweetType(TweetType.RETWEET);
            newRetweet.setAuthor(authUser);
            newRetweet.setRetweet(tweet);
            tweetRepository.save(newRetweet);
            isRetweeted = true;
        }
        tweetRepository.updateRetweetsCount(isRetweeted, tweet);
        updateTweetCountProducer.sendUpdateTweetCountEvent(authUser.getId(), isRetweeted);
        tweetNotificationProducer.sendTweetNotificationEvent(NotificationType.RETWEET, tweet, authUser, isRetweeted);
        return tweet;
    }
}
