package org.teta.mapper;

import org.teta.dto.HeaderResponse;
import org.teta.dto.response.user.CommonUserResponse;
import org.teta.dto.response.user.UserResponse;
import org.teta.dto.request.SearchTermsRequest;
import org.teta.dto.request.UserRequest;
import org.teta.dto.response.*;
import org.teta.model.User;
import org.teta.repository.projection.*;
import org.teta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final BasicMapper basicMapper;
    private final UserService userService;

    public UserProfileResponse getUserById(Long userId) {
        UserProfileProjection user = userService.getUserById(userId);
        return basicMapper.convertToResponse(user, UserProfileResponse.class);
    }

    public HeaderResponse<UserResponse> getUsers(Pageable pageable) {
        Page<UserProjection> users = userService.getUsers(pageable);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }

    public List<UserResponse> getRelevantUsers() {
        List<UserProjection> users = userService.getRelevantUsers();
        return basicMapper.convertToResponseList(users, UserResponse.class);
    }

    public HeaderResponse<UserResponse> searchUsersByUsername(String username, Pageable pageable) {
        Page<UserProjection> users = userService.searchUsersByUsername(username, pageable, UserProjection.class);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }

    public SearchResultResponse searchByText(String text) {
        Map<String, Object> searchResult = userService.searchByText(text);
        SearchResultResponse searchResultResponse = new SearchResultResponse();
        searchResultResponse.setText(text);
        searchResultResponse.setTweetCount((Long) searchResult.get("tweetCount"));
        searchResultResponse.setTags((List<String>) searchResult.get("tags"));
        List<CommonUserResponse> users = basicMapper.convertToResponseList(
                (List<CommonUserProjection>) searchResult.get("users"), CommonUserResponse.class);
        searchResultResponse.setUsers(users);
        return searchResultResponse;
    }

    public List<CommonUserResponse> getSearchResults(SearchTermsRequest request) {
        List<CommonUserProjection> users = userService.getSearchResults(request);
        return basicMapper.convertToResponseList(users, CommonUserResponse.class);
    }

    public Boolean startUseTwitter() {
        return userService.startUseTwitter();
    }

    public AuthUserResponse updateUserProfile(UserRequest userRequest) {
        User user = basicMapper.convertToResponse(userRequest, User.class);
        AuthUserProjection authUserProjection = userService.updateUserProfile(user);
        return basicMapper.convertToResponse(authUserProjection, AuthUserResponse.class);
    }

    public Boolean processSubscribeToNotifications(Long userId) {
        return userService.processSubscribeToNotifications(userId);
    }

    public UserPintTweetResponse processPinTweet(Long tweetId) {
        User user = userService.processPinTweet(tweetId);
        return new UserPintTweetResponse(user.getId(), user.getPinnedTweetId());
    }

    public UserDetailResponse getUserDetails(Long userId) {
        UserDetailProjection userDetails = userService.getUserDetails(userId);
        return basicMapper.convertToResponse(userDetails, UserDetailResponse.class);
    }
}
