package org.teta.service.impl;

import org.teta.constants.ErrorMessage;
import org.teta.exception.ApiRequestException;
import org.teta.model.Tweet;
import org.teta.model.User;
import org.teta.repository.UserRepository;
import org.teta.repository.projection.UserProjection;
import org.teta.service.UserService;
import org.teta.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getAuthUser() {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.findById(authUserId)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.USER_NOT_FOUND, HttpStatus.UNAUTHORIZED));
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> getUserIdByUsername(String username) {
        return userRepository.getUserIdByUsername(username.substring(1));
    }

    @Override
    public boolean isUserHavePrivateProfile(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return !userRepository.isUserHavePrivateProfile(userId, authUserId);
    }

    @Override
    public boolean isMyProfileBlockedByUser(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.isUserBlocked(userId, authUserId);
    }

    @Override
    public Page<UserProjection> getLikedUsersByTweet(Tweet tweet, Pageable pageable) {
        return userRepository.getLikedUsersByTweet(tweet, pageable);
    }

    @Override
    public Page<UserProjection> getRetweetedUsersByTweet(Tweet tweet, Pageable pageable) {
        return userRepository.getRetweetedUsersByTweet(tweet, pageable);
    }

    @Override
    public Page<UserProjection> getTaggedImageUsers(Tweet tweet, Pageable pageable) {
        return userRepository.getTaggedImageUsers(tweet, pageable);
    }

    public boolean isUserMutedByMyProfile(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.isUserMuted(authUserId, userId);
    }

    public boolean isUserBlockedByMyProfile(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.isUserBlocked(authUserId, userId);
    }

    public boolean isMyProfileWaitingForApprove(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.isMyProfileWaitingForApprove(userId, authUserId);
    }

    public boolean isUserFollowByOtherUser(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.isUserFollowByOtherUser(authUserId, userId);
    }
}
