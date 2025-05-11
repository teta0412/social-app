package org.teta.service;

import org.teta.event.TweetNotificationDto;
import org.teta.event.UpdateTweetEvent;
import org.teta.model.Tweet;

public interface TweetHandlerService {

    void handleUpdateTweet(UpdateTweetEvent tweetEvent);

    Tweet getOrCreateTweet(TweetNotificationDto tweet);
}
