package org.teta.dto.response.tweet;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teta.enums.LinkCoverSize;
import org.teta.enums.TweetType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuoteTweetResponse {
    private Long id;
    private String text;
    private TweetType tweetType;
    private LocalDateTime createdAt;
    private String link;
    private String linkTitle;
    private String linkDescription;
    private String linkCover;
    private LinkCoverSize linkCoverSize;
    private TweetAuthorResponse author;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
}
