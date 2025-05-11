package org.teta.service.impl;

import constants.ErrorMessage;
import exception.ApiRequestException;
import util.AuthUtil;
import org.teta.constants.ChatErrorMessage;
import org.teta.model.User;
import org.teta.repository.UserRepository;
import org.teta.repository.projection.UserChatProjection;
import org.teta.repository.projection.UserProjection;
import org.teta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public UserProjection getUserProjectionById(Long userId) {
        return userRepository.getUserById(userId)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<UserChatProjection> searchUsersByUsername(String username, Pageable pageable) {
        return userRepository.searchUsersByUsername(username, pageable, UserChatProjection.class);
    }

    @Override
    public List<User> getNotBlockedUsers(List<Long> usersIds) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.getNotBlockedUsers(usersIds, authUserId);
    }

    @Override
    public void isParticipantBlocked(Long authUserId, Long userId) {
        if (isUserBlockedByMyProfile(authUserId) || isMyProfileBlockedByUser(userId)) {
            throw new ApiRequestException(ChatErrorMessage.CHAT_PARTICIPANT_BLOCKED, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public boolean isUserBlockedByMyProfile(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.isUserBlocked(authUserId, userId);
    }

    @Override
    public boolean isMyProfileBlockedByUser(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.isUserBlocked(userId, authUserId);
    }

    @Override
    public boolean isMyProfileWaitingForApprove(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.isMyProfileWaitingForApprove(userId, authUserId);
    }

    @Override
    public boolean isUserFollowByOtherUser(Long userId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return userRepository.isUserFollowByOtherUser(authUserId, userId);
    }
}
