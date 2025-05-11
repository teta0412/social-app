package org.teta.service.impl;

import org.teta.model.Bookmark;
import org.teta.model.Tweet;
import org.teta.model.User;
import org.teta.repository.BookmarkRepository;
import org.teta.repository.projection.BookmarkProjection;
import org.teta.service.BookmarkService;
import org.teta.service.UserService;
import org.teta.service.util.TweetValidationHelper;
import org.teta.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final TweetValidationHelper tweetValidationHelper;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Page<BookmarkProjection> getUserBookmarks(Pageable pageable) {
        User authUser = userService.getAuthUser();
        return bookmarkRepository.getUserBookmarks(authUser, pageable);
    }

    @Override
    @Transactional
    public Boolean processUserBookmarks(Long tweetId) {
        Tweet tweet = tweetValidationHelper.checkValidTweet(tweetId);
        User authUser = userService.getAuthUser();
        Bookmark bookmark = bookmarkRepository.getUserBookmark(authUser, tweet);

        if (bookmark != null) {
            bookmarkRepository.delete(bookmark);
            return false;
        } else {
            Bookmark newBookmark = new Bookmark(authUser, tweet);
            bookmarkRepository.save(newBookmark);
            return true;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean getIsTweetBookmarked(Long tweetId) {
        Long authUserId = AuthUtil.getAuthenticatedUserId();
        return bookmarkRepository.isUserBookmarkedTweet(authUserId, tweetId);
    }
}
