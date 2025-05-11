package org.teta.mapper;

import dto.HeaderResponse;
import mapper.BasicMapper;
import org.teta.dto.request.TweetDeleteRequest;
import org.teta.dto.request.TweetRequest;
import dto.response.tweet.TweetResponse;
import org.teta.model.Tweet;
import org.teta.repository.projection.TweetProjection;
import org.teta.service.ScheduledTweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTweetMapper {

    private final BasicMapper basicMapper;
    private final ScheduledTweetService scheduledTweetService;

    public HeaderResponse<TweetResponse> getScheduledTweets(Pageable pageable) {
        Page<TweetProjection> tweets = scheduledTweetService.getScheduledTweets(pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public TweetResponse createScheduledTweet(TweetRequest tweetRequest) {
        TweetProjection tweet = scheduledTweetService.createScheduledTweet(basicMapper.convertToResponse(tweetRequest, Tweet.class));
        return basicMapper.convertToResponse(tweet, TweetResponse.class);
    }

    public TweetResponse updateScheduledTweet(TweetRequest tweetRequest) {
        TweetProjection tweet = scheduledTweetService.updateScheduledTweet(basicMapper.convertToResponse(tweetRequest, Tweet.class));
        return basicMapper.convertToResponse(tweet, TweetResponse.class);
    }

    public String deleteScheduledTweets(TweetDeleteRequest tweetRequest) {
        return scheduledTweetService.deleteScheduledTweets(tweetRequest.getTweetsIds());
    }
}
