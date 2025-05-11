package org.teta.service;

import org.teta.model.User;
import org.teta.repository.projection.UserChatProjection;
import org.teta.repository.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User getAuthUser();

    User getUserById(Long userId);

    UserProjection getUserProjectionById(Long userId);

    Page<UserChatProjection> searchUsersByUsername(String username, Pageable pageable);

    List<User> getNotBlockedUsers(List<Long> usersIds);

    void isParticipantBlocked(Long authUserId, Long userId);

    boolean isUserBlockedByMyProfile(Long userId);

    boolean isMyProfileBlockedByUser(Long userId);

    boolean isMyProfileWaitingForApprove(Long userId);

    boolean isUserFollowByOtherUser(Long userId);
}
