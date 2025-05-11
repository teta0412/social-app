package org.teta.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import dto.response.tweet.TweetAdditionalInfoUserResponse;
import enums.ReplyType;
import lombok.Data;

@Data
public class TweetAdditionalInfoResponse {
    private String text;
    private ReplyType replyType;
    private Long addressedTweetId;
    private TweetAdditionalInfoUserResponse author;
    @JsonProperty("isDeleted")
    private boolean isDeleted;
}
