package org.teta.repository.projection;

import org.teta.enums.NotificationType;

import java.time.LocalDateTime;

public interface NotificationProjection {
    Long getId();
    LocalDateTime getDate();
    NotificationType getNotificationType();
    UserProjection getUser();
    UserProjection getUserToFollow();
    TweetProjection getTweet();
    ListsProjection getList();

    interface UserProjection {
        Long getId();
        String getUsername();
        String getAvatar();
    }

    interface TweetProjection {
        Long getId();
        String getText();
        UserProjection getAuthor();
    }

    interface ListsProjection {
        Long getId();
        String getListName();
    }
}
