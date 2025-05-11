package org.teta.service;

import dto.response.tweet.TweetResponse;
import org.teta.model.Tweet;
import org.teta.repository.projection.TweetProjection;

import java.util.List;

public interface PollService {

    TweetResponse createPoll(Long pollDateTime, List<String> choices, Tweet tweet);

    TweetProjection voteInPoll(Long tweetId, Long pollId, Long pollChoiceId);
}
