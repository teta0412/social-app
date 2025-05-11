package org.teta.service.impl;

import org.teta.broker.producer.FollowRequestUserProducer;
import org.teta.broker.producer.FollowUserNotificationProducer;
import org.teta.broker.producer.FollowUserProducer;
import org.teta.constants.UserSuccessMessage;
import org.teta.model.User;
import org.teta.repository.FollowerUserRepository;
import org.teta.repository.UserRepository;
import org.teta.repository.projection.BaseUserProjection;
import org.teta.repository.projection.FollowerUserProjection;
import org.teta.repository.projection.UserProfileProjection;
import org.teta.repository.projection.UserProjection;
import org.teta.service.AuthenticationService;
import org.teta.service.FollowerUserService;
import org.teta.service.util.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerUserServiceImpl implements FollowerUserService {

    private final UserRepository userRepository;
    private final FollowerUserRepository followerUserRepository;
    private final AuthenticationService authenticationService;
    private final UserServiceHelper userServiceHelper;
    private final FollowUserProducer followUserProducer;
    private final FollowRequestUserProducer followRequestUserProducer;
    private final FollowUserNotificationProducer followUserNotificationProducer;

    @Override
    public Page<UserProjection> getFollowers(Long userId, Pageable pageable) {
        userServiceHelper.validateUserProfile(userId);
        return followerUserRepository.getFollowersById(userId, pageable);
    }

    @Override
    public Page<UserProjection> getFollowing(Long userId, Pageable pageable) {
        userServiceHelper.validateUserProfile(userId);
        return followerUserRepository.getFollowingById(userId, pageable);
    }

    @Override
    public Page<FollowerUserProjection> getFollowerRequests(Pageable pageable) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return followerUserRepository.getFollowerRequests(authUserId, pageable);
    }

    @Override
    @Transactional
    public Boolean processFollow(Long userId) {
        User user = userServiceHelper.getUserById(userId);
        User authUser = authenticationService.getAuthenticatedUser();
        userServiceHelper.checkIsUserBlocked(user.getId(), authUser.getId());

        if (followerUserRepository.isFollower(authUser.getId(), user.getId())) {
            followerUserRepository.unfollow(authUser.getId(), user.getId());
            followerUserRepository.updateFollowingCount(false, user.getId());
            followerUserRepository.updateFollowersCount(false, authUser.getId());
            userRepository.unsubscribe(authUser.getId(), user.getId());
            followUserProducer.sendFollowUserEvent(user, authUser.getId(), false);
        } else {
            if (!user.isPrivateProfile()) {
                followerUserRepository.follow(authUser.getId(), user.getId());
                followerUserRepository.updateFollowingCount(true, user.getId());
                followerUserRepository.updateFollowersCount(true, authUser.getId());
                followUserNotificationProducer.sendFollowUserNotificationEvent(authUser, user);
                followUserProducer.sendFollowUserEvent(user, authUser.getId(), true);
                return true;
            } else {
                followerUserRepository.addFollowerRequest(authUser.getId(), user.getId());
                followerUserRepository.updateFollowerRequestsCount(true, user.getId());
                followRequestUserProducer.sendFollowRequestUserEvent(user, authUser.getId(), true);
            }
        }
        return false;
    }

    @Override
    public List<BaseUserProjection> overallFollowers(Long userId) {
        userServiceHelper.validateUserProfile(userId);
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return followerUserRepository.getSameFollowers(userId, authUserId, BaseUserProjection.class);
    }

    @Override
    @Transactional
    public UserProfileProjection processFollowRequestToPrivateProfile(Long userId) {
        User user = userServiceHelper.getUserById(userId);
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userServiceHelper.checkIsUserBlocked(user.getId(), authUserId);
        boolean hasUserFollowRequest;

        if (followerUserRepository.isFollowerRequest(user.getId(), authUserId)) {
            followerUserRepository.removeFollowerRequest(authUserId, user.getId());
            hasUserFollowRequest = false;
        } else {
            followerUserRepository.addFollowerRequest(authUserId, user.getId());
            hasUserFollowRequest = true;
        }
        followerUserRepository.updateFollowerRequestsCount(hasUserFollowRequest, user.getId());
        followRequestUserProducer.sendFollowRequestUserEvent(user, authUserId, hasUserFollowRequest);
        return userRepository.getUserById(user.getId(), UserProfileProjection.class).get();
    }

    @Override
    @Transactional
    public String acceptFollowRequest(Long userId) {
        User user = userServiceHelper.getUserById(userId);
        User authUser = authenticationService.getAuthenticatedUser();
        followerUserRepository.removeFollowerRequest(user.getId(), authUser.getId());
        followerUserRepository.updateFollowerRequestsCount(false, user.getId());
        followerUserRepository.follow(user.getId(), authUser.getId());
        followerUserRepository.updateFollowingCount(true, user.getId());
        followerUserRepository.updateFollowersCount(true, authUser.getId());
        followRequestUserProducer.sendFollowRequestUserEvent(user, authUser.getId(), false);
        followUserProducer.sendFollowUserEvent(user, authUser.getId(), true);
        return String.format(UserSuccessMessage.USER_ACCEPTED, userId);
    }

    @Override
    @Transactional
    public String declineFollowRequest(Long userId) {
        User user = userServiceHelper.getUserById(userId);
        User authUser = authenticationService.getAuthenticatedUser();
        followerUserRepository.removeFollowerRequest(user.getId(), authUser.getId());
        followerUserRepository.updateFollowerRequestsCount(false, user.getId());
        followRequestUserProducer.sendFollowRequestUserEvent(user, authUser.getId(), false);
        return String.format(UserSuccessMessage.USER_DECLINED, userId);
    }
}
