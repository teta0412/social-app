package org.teta.service.util;

import org.teta.constants.ErrorMessage;
import org.teta.exception.ApiRequestException;
import org.teta.constants.TweetErrorMessage;
import org.teta.model.Tweet;
import org.teta.model.User;
import org.teta.repository.TweetRepository;
import org.teta.service.UserService;
import org.teta.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TweetValidationHelper {

    private final TweetRepository tweetRepository;
    private final UserService userService;

    public Tweet checkValidTweet(Long tweetId) {
        Tweet tweet = tweetRepository.getTweetById(tweetId, Tweet.class)
                .orElseThrow(() -> new ApiRequestException(TweetErrorMessage.TWEET_NOT_FOUND, HttpStatus.NOT_FOUND));
        validateTweet(tweet.isDeleted(), tweet.getAuthor().getId());
        return tweet;
    }

    public void validateTweet(boolean isDeleted, Long tweetAuthorId) {
        if (isDeleted) {
            throw new ApiRequestException(TweetErrorMessage.TWEET_DELETED, HttpStatus.BAD_REQUEST);
        }
        checkIsValidUserProfile(tweetAuthorId);
    }

    public User validateUserProfile(Long userId) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new ApiRequestException(String.format(ErrorMessage.USER_ID_NOT_FOUND, userId), HttpStatus.NOT_FOUND));
        checkIsValidUserProfile(userId);
        return user;
    }

    public void checkIsValidUserProfile(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();

        if (!userId.equals(authUserId)) {
            if (userService.isUserHavePrivateProfile(userId)) {
                throw new ApiRequestException(ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
            if (userService.isMyProfileBlockedByUser(userId)) {
                throw new ApiRequestException(ErrorMessage.USER_PROFILE_BLOCKED, HttpStatus.BAD_REQUEST);
            }
        }
    }

    public void checkTweetTextLength(String text) {
        if (text.length() == 0 || text.length() > 280) {
            throw new ApiRequestException(TweetErrorMessage.INCORRECT_TWEET_TEXT_LENGTH, HttpStatus.BAD_REQUEST);
        }
    }
}
