package org.teta.repository.projection;

import java.time.LocalDateTime;

public interface RetweetProjection {
    LocalDateTime getRetweetDate();
    TweetUserProjection getTweet();
}
