package org.teta.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teta.dto.response.tweet.TweetAdditionalInfoUserResponse;
import org.teta.enums.ReplyType;
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
