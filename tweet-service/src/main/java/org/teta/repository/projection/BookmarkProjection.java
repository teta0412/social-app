package org.teta.repository.projection;

import java.time.LocalDateTime;

public interface BookmarkProjection {
    LocalDateTime getBookmarkDate();
    TweetProjection getTweet();
}
