package org.teta.controller.api;

import constants.PathConstants;
import dto.HeaderResponse;
import dto.request.IdsRequest;
import dto.response.chat.ChatTweetResponse;
import dto.response.tweet.TweetResponse;
import event.UpdateTweetEvent;
import org.teta.mapper.TweetClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.API_V1_TWEETS)
public class TweetApiController {

    private final TweetClientMapper tweetClientMapper;

    @PostMapping(PathConstants.IDS)
    public List<TweetResponse> getTweetsByIds(@RequestBody IdsRequest requests) {
        return tweetClientMapper.getTweetsByIds(requests);
    }

    @PostMapping(PathConstants.USER_IDS)
    public HeaderResponse<TweetResponse> getTweetsByUserIds(@RequestBody IdsRequest request,
                                                            @SpringQueryMap Pageable pageable) {
        return tweetClientMapper.getTweetsByUserIds(request, pageable);
    }

    @GetMapping(PathConstants.TWEET_ID)
    public TweetResponse getTweetById(@PathVariable("tweetId") Long tweetId) {
        return tweetClientMapper.getTweetById(tweetId);
    }

    @GetMapping(PathConstants.ID_TWEET_ID)
    public Boolean isTweetExists(@PathVariable("tweetId") Long tweetId) {
        return tweetClientMapper.isTweetExists(tweetId);
    }

    @GetMapping(PathConstants.COUNT_TEXT)
    public Long getTweetCountByText(@PathVariable("text") String text) {
        return tweetClientMapper.getTweetCountByText(text);
    }

    @GetMapping(PathConstants.CHAT_TWEET_ID)
    public ChatTweetResponse getChatTweet(@PathVariable("tweetId") Long tweetId) {
        return tweetClientMapper.getChatTweet(tweetId);
    }

    @GetMapping(PathConstants.BATCH_TWEETS)
    public List<UpdateTweetEvent> getBatchTweets(@RequestParam("period") Integer period,
                                                 @RequestParam("page") Integer page,
                                                 @RequestParam("limit") Integer limit) {
        return tweetClientMapper.getBatchTweets(period, page, limit);
    }
}
