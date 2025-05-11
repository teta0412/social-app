package org.teta.repository.projection;

import org.springframework.beans.factory.annotation.Value;

public interface TweetAuthorProjection {
    Long getId();
    String getFullName();
    String getUsername();
    String getAvatar();
    boolean getPrivateProfile();

    @Value("#{target.pinnedTweet?.id}")
    Long getPinnedTweetId();

    @Value("#{@userServiceImpl.isUserMutedByMyProfile(target.id)}")
    boolean getIsUserMuted();

    @Value("#{@userServiceImpl.isUserBlockedByMyProfile(target.id)}")
    boolean getIsUserBlocked();

    @Value("#{@userServiceImpl.isMyProfileBlockedByUser(target.id)}")
    boolean getIsMyProfileBlocked();

    @Value("#{@userServiceImpl.isMyProfileWaitingForApprove(target.id)}")
    boolean getIsWaitingForApprove();

    @Value("#{@userServiceImpl.isUserFollowByOtherUser(target.id)}")
    boolean getIsFollower();
}
