package org.teta.service.impl;

import org.teta.broker.producer.PinTweetProducer;
import org.teta.broker.producer.UpdateUserProducer;
import org.teta.client.TagClient;
import org.teta.client.TweetClient;
import exception.ApiRequestException;
import org.teta.constants.UserErrorMessage;
import org.teta.dto.request.SearchTermsRequest;
import org.teta.model.User;
import org.teta.repository.UserRepository;
import org.teta.repository.projection.*;
import org.teta.service.AuthenticationService;
import org.teta.service.UserService;
import org.teta.service.util.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserServiceHelper userServiceHelper;
    private final UpdateUserProducer updateUserProducer;
    private final PinTweetProducer pinTweetProducer;
    private final TweetClient tweetClient;
    private final TagClient tagClient;

    @Override
    public UserProfileProjection getUserById(Long userId) {
        return getUserById(userId, UserProfileProjection.class);
    }

    @Override
    public Page<UserProjection> getUsers(Pageable pageable) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return userRepository.findByActiveTrueAndIdNot(authUserId, pageable);
    }

    @Override
    public List<UserProjection> getRelevantUsers() {
        return userRepository.findTop5ByActiveTrue();
    }

    @Override
    public <T> Page<T> searchUsersByUsername(String text, Pageable pageable, Class<T> type) {
        return userRepository.searchUsersByUsername(text, pageable, type);
    }

    @Override
    public Map<String, Object> searchByText(String text) {
        Long tweetCount = tweetClient.getTweetCountByText(text);
        List<String> tags = tagClient.getTagsByText(text);
        List<CommonUserProjection> users = userRepository.searchUserByText(text);
        return Map.of("tweetCount", tweetCount, "tags", tags, "users", users);
    }

    @Override
    public List<CommonUserProjection> getSearchResults(SearchTermsRequest request) {
        return userRepository.getUsersByIds(request.getUsers(), CommonUserProjection.class);
    }

    @Override
    @Transactional
    public Boolean startUseTwitter() {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userRepository.updateProfileStarted(authUserId);
        return true;
    }

    @Override
    @Transactional
    public AuthUserProjection updateUserProfile(User userInfo) {
        if (userInfo.getFullName().length() == 0 || userInfo.getFullName().length() > 50) {
            throw new ApiRequestException(UserErrorMessage.INCORRECT_USERNAME_LENGTH, HttpStatus.BAD_REQUEST);
        }
        User user = authenticationService.getAuthenticatedUser();

        if (userInfo.getAvatar() != null) {
            user.setAvatar(userInfo.getAvatar());
        }
        if (userInfo.getWallpaper() != null) {
            user.setWallpaper(userInfo.getWallpaper());
        }
        user.setFullName(userInfo.getFullName());
        user.setAbout(userInfo.getAbout());
        user.setLocation(userInfo.getLocation());
        user.setWebsite(userInfo.getWebsite());
        user.setProfileCustomized(true);
        updateUserProducer.sendUpdateUserEvent(user);
        return userRepository.getUserById(user.getId(), AuthUserProjection.class).get();
    }

    @Override
    @Transactional
    public Boolean processSubscribeToNotifications(Long userId) {
        userServiceHelper.checkIsUserExistOrMyProfileBlocked(userId);
        Long authUserId = authenticationService.getAuthenticatedUserId();

        if (userRepository.isUserSubscribed(userId, authUserId)) {
            userRepository.unsubscribe(authUserId, userId);
            return false;
        } else {
            userRepository.subscribe(authUserId, userId);
            return true;
        }
    }

    @Override
    @Transactional
    public User processPinTweet(Long tweetId) {
        if (!tweetClient.isTweetExists(tweetId)) {
            throw new ApiRequestException(UserErrorMessage.TWEET_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        User authUser = authenticationService.getAuthenticatedUser();
        Long pinnedTweetId;
        if (authUser.getPinnedTweetId() == null || !authUser.getPinnedTweetId().equals(tweetId)) {
            authUser.setPinnedTweetId(tweetId);
            pinnedTweetId = tweetId;
        } else {
            authUser.setPinnedTweetId(null);
            pinnedTweetId = null;
        }
        pinTweetProducer.sendPinTweetEvent(pinnedTweetId, authUser.getId());
        return authUser;
    }

    @Override
    public UserDetailProjection getUserDetails(Long userId) {
        userServiceHelper.checkIsUserExistOrMyProfileBlocked(userId);
        return getUserById(userId, UserDetailProjection.class);
    }

    private <T> T getUserById(Long userId, Class<T> type) {
        return userRepository.getUserById(userId, type)
                .orElseThrow(() -> new ApiRequestException(UserErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}
