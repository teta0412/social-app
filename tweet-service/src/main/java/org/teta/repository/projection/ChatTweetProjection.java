package org.teta.repository.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface ChatTweetProjection {

    @Value("#{target.isDeleted ? null : target.id}")
    Long getId();

    @Value("#{target.isDeleted ? null : target.text}")
    String getText();

    @Value("#{target.isDeleted ? null : target.createdAt}")
    LocalDateTime getCreatedAt();

    ChatTweetUserProjection getAuthor();

    boolean isDeleted();

    interface ChatTweetUserProjection {
        Long getId();
        String getFullName();
        String getUsername();
        String getAvatar();
    }
}
