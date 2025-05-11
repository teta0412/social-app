package org.teta.repository;

import org.teta.model.LikeTweet;
import org.teta.model.Tweet;
import org.teta.model.User;
import org.teta.repository.projection.LikeTweetProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeTweetRepository extends JpaRepository<LikeTweet, LikeTweet.TweetUserId> {

    @Query("""
            SELECT likeTweet FROM LikeTweet likeTweet
            WHERE likeTweet.user.id = :userId
            ORDER BY likeTweet.likeTweetDate DESC
            """)
    Page<LikeTweetProjection> getUserLikedTweets(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT CASE WHEN count(likeTweet) > 0 THEN true ELSE false END
            FROM LikeTweet likeTweet
            WHERE likeTweet.user.id = :userId
            AND likeTweet.tweet.id = :tweetId
            """)
    boolean isUserLikedTweet(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

    @Query("""
            SELECT likeTweet FROM LikeTweet likeTweet
            WHERE likeTweet.user = :user
            AND likeTweet.tweet = :tweet
            """)
    LikeTweet getLikedTweet(@Param("user") User user, @Param("tweet") Tweet tweet);
}
