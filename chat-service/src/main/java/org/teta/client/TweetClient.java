package org.teta.client;

import org.teta.configuration.FeignConfiguration;
import org.teta.constants.FeignConstants;
import org.teta.constants.PathConstants;
import org.teta.dto.response.chat.ChatTweetResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CircuitBreaker(name = FeignConstants.TWEET_SERVICE)
@FeignClient(name = FeignConstants.TWEET_SERVICE, path = PathConstants.API_V1_TWEETS, configuration = FeignConfiguration.class)
public interface TweetClient {

    @GetMapping(PathConstants.ID_TWEET_ID)
    Boolean isTweetExists(@PathVariable("tweetId") Long tweetId);

    @GetMapping(PathConstants.CHAT_TWEET_ID)
    ChatTweetResponse getChatTweet(@PathVariable("tweetId") Long tweetId);
}
