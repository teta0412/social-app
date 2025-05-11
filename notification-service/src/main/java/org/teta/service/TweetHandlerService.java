package org.teta.service;

import event.TweetNotificationDto;
import event.UpdateTweetEvent;
import org.teta.model.Tweet;

public interface TweetHandlerService {

    void handleUpdateTweet(UpdateTweetEvent tweetEvent);

    Tweet getOrCreateTweet(TweetNotificationDto tweet);
}
