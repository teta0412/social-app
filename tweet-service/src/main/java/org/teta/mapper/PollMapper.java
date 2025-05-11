package org.teta.mapper;

import dto.response.tweet.TweetResponse;
import mapper.BasicMapper;
import org.teta.dto.request.TweetRequest;
import org.teta.dto.request.VoteRequest;
import org.teta.model.Tweet;
import org.teta.repository.projection.TweetProjection;
import org.teta.service.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PollMapper {

    private final BasicMapper basicMapper;
    private final PollService pollService;

    public TweetResponse createPoll(TweetRequest tweetRequest) {
        Tweet tweet = basicMapper.convertToResponse(tweetRequest, Tweet.class);
        return pollService.createPoll(tweetRequest.getPollDateTime(), tweetRequest.getChoices(), tweet);
    }

    public TweetResponse voteInPoll(VoteRequest voteRequest) {
        TweetProjection tweet = pollService.voteInPoll(voteRequest.getTweetId(), voteRequest.getPollId(),
                voteRequest.getPollChoiceId());
        return basicMapper.convertToResponse(tweet, TweetResponse.class);
    }
}
