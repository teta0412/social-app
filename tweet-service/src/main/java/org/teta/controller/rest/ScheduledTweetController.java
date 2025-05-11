package org.teta.controller.rest;

import constants.PathConstants;
import dto.HeaderResponse;
import org.teta.dto.request.TweetDeleteRequest;
import org.teta.dto.request.TweetRequest;
import dto.response.tweet.TweetResponse;
import org.teta.mapper.ScheduledTweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.UI_V1_TWEETS)
public class ScheduledTweetController {

    private final ScheduledTweetMapper scheduledTweetMapper;

    @GetMapping(PathConstants.SCHEDULE)
    public ResponseEntity<List<TweetResponse>> getScheduledTweets(@PageableDefault(size = 15) Pageable pageable) {
        HeaderResponse<TweetResponse> response = scheduledTweetMapper.getScheduledTweets(pageable);
        return ResponseEntity.ok().headers(response.getHeaders()).body(response.getItems());
    }

    @PostMapping(PathConstants.SCHEDULE)
    public ResponseEntity<TweetResponse> createScheduledTweet(@RequestBody TweetRequest tweetRequest) {
        return ResponseEntity.ok(scheduledTweetMapper.createScheduledTweet(tweetRequest));
    }

    @PutMapping(PathConstants.SCHEDULE)
    public ResponseEntity<TweetResponse> updateScheduledTweet(@RequestBody TweetRequest tweetRequest) {
        return ResponseEntity.ok(scheduledTweetMapper.updateScheduledTweet(tweetRequest));
    }

    @DeleteMapping(PathConstants.SCHEDULE)
    public ResponseEntity<String> deleteScheduledTweets(@RequestBody TweetDeleteRequest tweetRequest) {
        return ResponseEntity.ok(scheduledTweetMapper.deleteScheduledTweets(tweetRequest));
    }
}
