package org.teta.mapper;

import org.teta.dto.HeaderResponse;
import org.teta.dto.response.user.UserResponse;
import org.teta.dto.response.FollowerUserResponse;
import org.teta.dto.response.UserProfileResponse;
import org.teta.repository.projection.BaseUserProjection;
import org.teta.repository.projection.FollowerUserProjection;
import org.teta.repository.projection.UserProfileProjection;
import org.teta.repository.projection.UserProjection;
import org.teta.service.FollowerUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FollowerUserMapper {

    private final BasicMapper basicMapper;
    private final FollowerUserService followerUserService;

    public HeaderResponse<UserResponse> getFollowers(Long userId, Pageable pageable) {
        Page<UserProjection> users = followerUserService.getFollowers(userId, pageable);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }

    public HeaderResponse<UserResponse> getFollowing(Long userId, Pageable pageable) {
        Page<UserProjection> users = followerUserService.getFollowing(userId, pageable);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }

    public HeaderResponse<FollowerUserResponse> getFollowerRequests(Pageable pageable) {
        Page<FollowerUserProjection> followers = followerUserService.getFollowerRequests(pageable);
        return basicMapper.getHeaderResponse(followers, FollowerUserResponse.class);
    }

    public Boolean processFollow(Long userId) {
        return followerUserService.processFollow(userId);
    }

    public List<UserResponse> overallFollowers(Long userId) {
        List<BaseUserProjection> users = followerUserService.overallFollowers(userId);
        return basicMapper.convertToResponseList(users, UserResponse.class);
    }

    public UserProfileResponse processFollowRequestToPrivateProfile(Long userId) {
        UserProfileProjection user = followerUserService.processFollowRequestToPrivateProfile(userId);
        return basicMapper.convertToResponse(user, UserProfileResponse.class);
    }

    public String acceptFollowRequest(Long userId) {
        return followerUserService.acceptFollowRequest(userId);
    }

    public String declineFollowRequest(Long userId) {
        return followerUserService.declineFollowRequest(userId);
    }
}
