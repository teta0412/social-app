package org.teta.dto.response;

import dto.response.tweet.TweetResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TweetUserResponse extends TweetResponse {
    private List<Long> retweetsUserIds;
}
