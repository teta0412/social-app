package org.teta.service.impl;

import org.teta.broker.producer.BlockUserProducer;
import org.teta.model.User;
import org.teta.repository.BlockUserRepository;
import org.teta.repository.FollowerUserRepository;
import org.teta.repository.projection.BlockedUserProjection;
import org.teta.service.AuthenticationService;
import org.teta.service.BlockUserService;
import org.teta.service.util.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlockUserServiceImpl implements BlockUserService {

    private final AuthenticationService authenticationService;
    private final BlockUserRepository blockUserRepository;
    private final UserServiceHelper userServiceHelper;
    private final FollowerUserRepository followerUserRepository;
    private final BlockUserProducer blockUserProducer;

    @Override
    public Page<BlockedUserProjection> getBlockList(Pageable pageable) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        return blockUserRepository.getUserBlockListById(authUserId, pageable);
    }

    @Override
    @Transactional
    public Boolean processBlockList(Long userId) {
        User user = userServiceHelper.getUserById(userId);
        Long authUserId = authenticationService.getAuthenticatedUserId();
        boolean hasUserBlocked;

        if (blockUserRepository.isUserBlocked(authUserId, userId)) {
            blockUserRepository.unblockUser(authUserId, userId);
            hasUserBlocked = false;
        } else {
            blockUserRepository.blockUser(authUserId, userId);
            followerUserRepository.unfollow(authUserId, userId);
            followerUserRepository.unfollow(userId, authUserId);
            followerUserRepository.updateFollowersCount(false, userId);
            followerUserRepository.updateFollowingCount(false, authUserId);
            hasUserBlocked = true;
        }
        blockUserProducer.sendBlockUserEvent(user, authUserId, hasUserBlocked);
        return hasUserBlocked;
    }
}
