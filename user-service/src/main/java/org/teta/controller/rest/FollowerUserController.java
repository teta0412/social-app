package org.teta.controller.rest;

import constants.PathConstants;
import dto.HeaderResponse;
import dto.response.user.UserResponse;
import org.teta.dto.response.FollowerUserResponse;
import org.teta.dto.response.UserProfileResponse;
import org.teta.mapper.FollowerUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.UI_V1_USER)
public class FollowerUserController {

    private final FollowerUserMapper followerUserMapper;

    @GetMapping(PathConstants.FOLLOWERS_USER_ID)
    public ResponseEntity<List<UserResponse>> getFollowers(@PathVariable("userId") Long userId,
                                                           @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = followerUserMapper.getFollowers(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(PathConstants.FOLLOWING_USER_ID)
    public ResponseEntity<List<UserResponse>> getFollowing(@PathVariable("userId") Long userId,
                                                           @PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<UserResponse> response = followerUserMapper.getFollowing(userId, pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(PathConstants.FOLLOWER_REQUESTS)
    public ResponseEntity<List<FollowerUserResponse>> getFollowerRequests(@PageableDefault(size = 10) Pageable pageable) {
        HeaderResponse<FollowerUserResponse> response = followerUserMapper.getFollowerRequests(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @GetMapping(PathConstants.FOLLOW_USER_ID)
    public ResponseEntity<Boolean> processFollow(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(followerUserMapper.processFollow(userId));
    }

    @GetMapping(PathConstants.FOLLOW_OVERALL) // TODO add pagination
    public ResponseEntity<List<UserResponse>> overallFollowers(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(followerUserMapper.overallFollowers(userId));
    }

    @GetMapping(PathConstants.FOLLOW_PRIVATE)
    public ResponseEntity<UserProfileResponse> processFollowRequestToPrivateProfile(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(followerUserMapper.processFollowRequestToPrivateProfile(userId));
    }

    @GetMapping(PathConstants.FOLLOW_ACCEPT)
    public ResponseEntity<String> acceptFollowRequest(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(followerUserMapper.acceptFollowRequest(userId));
    }

    @GetMapping(PathConstants.FOLLOW_DECLINE)
    public ResponseEntity<String> declineFollowRequest(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(followerUserMapper.declineFollowRequest(userId));
    }
}
