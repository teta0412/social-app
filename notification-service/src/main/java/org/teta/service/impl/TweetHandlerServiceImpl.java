package org.teta.service.impl;

import org.teta.event.TweetNotificationDto;
import org.teta.event.UpdateTweetEvent;
import org.teta.model.Tweet;
import org.teta.model.User;
import org.teta.repository.TweetRepository;
import org.teta.service.TweetHandlerService;
import org.teta.service.UserHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TweetHandlerServiceImpl implements TweetHandlerService {

    private final TweetRepository tweetRepository;
    private final UserHandlerService userHandlerService;

    @Override
    @Transactional
    public void handleUpdateTweet(UpdateTweetEvent tweetEvent) {
        tweetRepository.findById(tweetEvent.getId())
                .map(tweet -> {
                    tweet.setText(tweetEvent.getText());
                    return tweet;
                });
    }

    @Override
    @Transactional
    public Tweet getOrCreateTweet(TweetNotificationDto tweet) {
        User author = userHandlerService.getOrCreateUser(tweet.getAuthor());
        return tweetRepository.findById(tweet.getId())
                .orElseGet(() -> {
                    Tweet newTweet = new Tweet();
                    newTweet.setId(tweet.getId());
                    newTweet.setText(tweet.getText());
                    newTweet.setTweetType(tweet.getTweetType());
                    newTweet.setAuthor(author);
                    return tweetRepository.save(newTweet);
                });
    }
}
