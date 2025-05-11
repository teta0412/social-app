package org.teta.service;

import org.teta.event.UpdateTweetCountEvent;

public interface UserUpdateTweetCountService {

    void handleUpdateTweetCount(UpdateTweetCountEvent tweetCountEvent, String authId);

    void handleUpdateLikeTweetCount(UpdateTweetCountEvent tweetCountEvent, String authId);

    void handleUpdateMediaTweetCount(UpdateTweetCountEvent tweetCountEvent, String authId);
}
