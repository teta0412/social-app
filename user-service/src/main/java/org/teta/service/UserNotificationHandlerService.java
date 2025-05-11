package org.teta.service;

import org.teta.event.TweetSubscriberNotificationEvent;

public interface UserNotificationHandlerService {

    void increaseNotificationsCount(Long notifiedUserEventId);

    void increaseMentionsCount(Long notifiedUserEventId);

    void resetNotificationCount(Long notifiedUserEventId);

    void resetMentionCount(Long notifiedUserEventId);

    void processSubscriberNotificationListener(TweetSubscriberNotificationEvent event);
}
