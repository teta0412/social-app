package org.teta.service.util;

import dto.response.tweet.TweetListResponse;
import org.teta.client.ListsClient;
import org.teta.repository.BookmarkRepository;
import org.teta.repository.LikeTweetRepository;
import org.teta.repository.RetweetRepository;
import util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TweetProjectionHelper {

    private final LikeTweetRepository likeTweetRepository;
    private final RetweetRepository retweetRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ListsClient listsClient;

    public boolean isUserLikedTweet(Long tweetId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return likeTweetRepository.isUserLikedTweet(authUserId, tweetId);
    }

    public boolean isUserRetweetedTweet(Long tweetId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return retweetRepository.isUserRetweetedTweet(authUserId, tweetId);
    }

    public boolean isUserBookmarkedTweet(Long tweetId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return bookmarkRepository.isUserBookmarkedTweet(authUserId, tweetId);
    }

    public TweetListResponse getTweetList(Long listId) {
        return listId != null ? listsClient.getTweetList(listId) : null;
    }
}
