package org.teta.repository;

import org.teta.model.Tweet;
import org.teta.model.User;
import org.teta.repository.projection.ProfileTweetImageProjection;
import org.teta.repository.projection.TweetProjection;
import org.teta.repository.projection.TweetUserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.id = :tweetId
            """)
    <T> Optional<T> getTweetById(@Param("tweetId") Long tweetId, Class<T> type);

    @Query("SELECT tweet FROM Tweet tweet WHERE tweet = :tweet")
    Optional<TweetUserProjection> getPinnedTweetById(@Param("tweet") Tweet tweet);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType = 'TWEET'
            AND tweet.author.id IN :userIds
            AND tweet.addressedUser IS NULL
            AND tweet.scheduledDate IS NULL
            AND tweet.deleted = false
            ORDER BY tweet.createdAt DESC
            """)
    Page<TweetProjection> getTweetsByAuthorIds(@Param("userIds") List<Long> userIds, Pageable pageable);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType = 'TWEET'
            AND tweet.author.id NOT IN (
                    SELECT user.id FROM User user
                    JOIN user.userBlockedList blockedUser
                    WHERE user.id IN (
                        SELECT DISTINCT tweet.author.id FROM Tweet tweet
                        WHERE tweet.addressedUser IS NULL
                        AND tweet.scheduledDate IS NULL
                        AND tweet.deleted = false
                    )
                    AND blockedUser.id = :userId
            )
            AND tweet.author.id IN (
                SELECT user.id FROM User user
                LEFT JOIN user.following following
                WHERE user.id IN (
                    SELECT DISTINCT tweet.author.id FROM Tweet tweet
                    WHERE tweet.addressedUser IS NULL
                    AND tweet.scheduledDate IS NULL
                    AND tweet.deleted = false
                )
                AND (user.privateProfile = false
                OR (user.privateProfile = true AND (following.id = :userId OR user.id = :userId))
                AND user.active = true)
            )
            AND tweet.addressedUser IS NULL
            AND tweet.scheduledDate IS NULL
            AND tweet.deleted = false
            ORDER BY tweet.createdAt DESC
            """)
    Page<TweetProjection> getTweetsByAuthors(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.author = :user
            AND tweet.tweetType <> 'REPLY'
            AND (tweet <> :pinnedTweet OR tweet.author.pinnedTweet IS NULL)
            AND tweet.addressedUser IS NULL
            AND tweet.scheduledDate IS NULL
            AND tweet.deleted = FALSE
            ORDER BY tweet.createdAt DESC
            """)
    Page<TweetUserProjection> getTweetsByUserId(@Param("user") User user,
                                                @Param("pinnedTweet") Tweet pinnedTweet,
                                                Pageable pageable);

    @Query("""
            SELECT tweet FROM Tweet tweet
            LEFT JOIN tweet.images image
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.scheduledDate IS NULL AND tweet.deleted = false
            AND (tweet.author.id = :userId AND image.id IS NOT NULL)
            OR tweet.scheduledDate IS NULL AND tweet.deleted = false
            AND (tweet.author.id = :userId AND tweet.text LIKE CONCAT('%','youtu','%'))
            ORDER BY tweet.createdAt DESC
            """)
    Page<TweetProjection> getUserMediaTweets(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType <> 'TWEET'
            AND tweet.author.id = :userId
            AND tweet.addressedUser IS NOT NULL
            AND tweet.scheduledDate IS NULL
            AND tweet.deleted = false
            ORDER BY tweet.createdAt DESC
            """)
    Page<TweetUserProjection> getRetweetsAndRepliesByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType = 'REPLY'
            AND tweet.addressedTweet.id = :tweetId
            AND tweet.deleted = false
            ORDER BY tweet.createdAt DESC
            """)
    List<TweetProjection> getRepliesByTweetId(@Param("tweetId") Long tweetId);

    @Query("""
            SELECT tweet FROM Tweet tweet
            LEFT JOIN tweet.quoteTweet quoteTweet
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.author.id NOT IN (
                    SELECT user.id FROM User user
                    JOIN user.userBlockedList blockedUser
                    WHERE blockedUser.id = :userId
            )
            AND tweet.author.id IN (
                SELECT user.id FROM User user
                LEFT JOIN user.following following
                WHERE (user.privateProfile = false
                OR (user.privateProfile = true AND (following.id = :userId OR user.id = :userId))
                AND user.active = true)
            )
            AND quoteTweet.id = :tweetId
            AND quoteTweet.deleted = false
            """)
    Page<TweetProjection> getQuotesByTweet(@Param("userId") Long userId, @Param("tweetId") Long tweetId, Pageable pageable);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.author.id NOT IN (
                    SELECT user.id FROM User user
                    JOIN user.userBlockedList blockedUser
                    WHERE blockedUser.id = :userId
            )
            AND tweet.author.id IN (
                SELECT user.id FROM User user
                LEFT JOIN user.following following
                WHERE (user.privateProfile = false
                OR (user.privateProfile = true AND (following.id = :userId OR user.id = :userId))
                AND user.active = true)
            )
            AND tweet.scheduledDate IS NULL
            AND SIZE(tweet.images) <> 0
            AND tweet.deleted = false
            ORDER BY tweet.createdAt DESC
            """)
    Page<TweetProjection> getMediaTweets(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.author.id NOT IN (
                    SELECT user.id FROM User user
                    JOIN user.userBlockedList blockedUser
                    WHERE blockedUser.id = :userId
            )
            AND tweet.author.id IN (
                SELECT user.id FROM User user
                LEFT JOIN user.following following
                WHERE (user.privateProfile = false
                OR (user.privateProfile = true AND (following.id = :userId OR user.id = :userId))
                AND user.active = true)
            )
            AND tweet.scheduledDate IS NULL
            AND tweet.deleted = false
            AND tweet.text LIKE CONCAT('%','youtu','%')
            """)
    Page<TweetProjection> getTweetsWithVideo(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.author.id IN (
                SELECT follower.id FROM User user
                JOIN user.followers follower
                WHERE user.id = :userId
            )
            AND tweet.addressedUser IS NULL
            AND tweet.deleted = false
            ORDER BY tweet.createdAt DESC
            """)
    Page<TweetProjection> getFollowersTweets(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.author.id = :userId
            AND tweet.scheduledDate IS NOT NULL
            AND tweet.deleted = false
            ORDER BY tweet.scheduledDate DESC
            """)
    Page<TweetProjection> getScheduledTweets(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.author.id = :userId
            AND tweet.id = :tweetId
            """)
    Optional<Tweet> getTweetByUserId(@Param("userId") Long userId, @Param("tweetId") Long tweetId);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.scheduledDate IS NULL
            AND tweet.deleted = false
            AND tweet.text LIKE CONCAT('%',:text,'%')
            AND tweet.author.id NOT IN (
                SELECT user.id FROM User user
                JOIN user.userBlockedList blockedUser
                WHERE blockedUser.id = :userId
            )
            AND tweet.author.id IN (
                SELECT user.id FROM User user
                LEFT JOIN user.following following
                WHERE (user.privateProfile = false
                OR (user.privateProfile = true AND (following.id = :userId OR user.id = :userId))
                AND user.active = true)
            )
            ORDER BY tweet.createdAt DESC
            """)
    Page<TweetProjection> searchTweets(@Param("text") String text, @Param("userId") Long userId, Pageable pageable);

    @Modifying
    @Query(value = "INSERT INTO replies (tweet_id, reply_id) VALUES (?1, ?2)", nativeQuery = true)
    void addReply(@Param("tweetId") Long tweetId, @Param("replyId") Long replyId);

    @Modifying
    @Query(value = "INSERT INTO quotes (tweet_id, quote_id) VALUES (?1, ?2)", nativeQuery = true)
    void addQuote(@Param("tweetId") Long tweetId, @Param("quoteTweetId") Long quoteTweetId);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.id = :tweetId
            AND tweet.author.id = :userId
            """)
    Optional<Tweet> getTweetByAuthorIdAndTweetId(@Param("tweetId") Long tweetId, @Param("userId") Long userId);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.id = :tweetId
            AND tweet.poll.id = :pollId
            """)
    Optional<Tweet> getTweetByPollIdAndTweetId(@Param("tweetId") Long tweetId, @Param("pollId") Long pollId);

    @Query("""
            SELECT CASE WHEN count(tweet) > 0 THEN true ELSE false END FROM Tweet tweet
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.id = :tweetId
            """)
    boolean isTweetExists(@Param("tweetId") Long tweetId);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.id IN :tweetIds
            AND tweet.deleted = false
            ORDER BY tweet.createdAt DESC
            """)
    List<TweetProjection> getTweetListsByIds(@Param("tweetIds") List<Long> tweetIds);

    @Query("""
            SELECT tweet.id AS tweetId, image.id AS imageId, image.src AS src FROM Tweet tweet
            LEFT JOIN tweet.images image
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.scheduledDate IS NULL
            AND tweet.author.id = :userId
            AND image.id IS NOT NULL
            AND tweet.deleted = false
            ORDER BY tweet.createdAt DESC
            """)
    List<ProfileTweetImageProjection> getUserTweetImages(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT COUNT(tweet) FROM Tweet tweet
            WHERE tweet.tweetType <> 'RETWEET'
            AND tweet.scheduledDate IS NULL
            AND tweet.deleted = false
            AND UPPER(tweet.text) LIKE UPPER(CONCAT('%',:text,'%'))
            """)
    Long getTweetCountByText(@Param("text") String text);

    List<Tweet> findAllByScheduledDate(LocalDateTime now);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.author = :user
            AND tweet.retweet = :tweet
            """)
    Optional<Tweet> getTweetRetweeted(@Param("user") User user, @Param("tweet") Tweet tweet);

    @Modifying
    @Query("""
            UPDATE Tweet tweet SET tweet.likesCount =
                CASE WHEN :isTweetLiked = true
                THEN (tweet.likesCount + 1)
                ELSE (tweet.likesCount - 1)
                END
            WHERE tweet = :tweet
            """)
    void updateLikesCount(@Param("isTweetLiked") boolean isTweetLiked, @Param("tweet") Tweet tweet);

    @Modifying
    @Query("""
            UPDATE Tweet tweet SET tweet.retweetsCount =
                CASE WHEN :isRetweeted = true
                THEN (tweet.retweetsCount + 1)
                ELSE (tweet.retweetsCount - 1)
                END
            WHERE tweet = :tweet
            """)
    void updateRetweetsCount(@Param("isRetweeted") boolean isTweetLiked, @Param("tweet") Tweet tweet);

    @Modifying
    @Query("UPDATE Tweet tweet SET tweet.repliesCount = tweet.repliesCount + 1 WHERE tweet = :tweet")
    void updateRepliesCount(@Param("tweet") Tweet tweet);

    @Modifying
    @Query("UPDATE Tweet tweet SET tweet.quotesCount = tweet.quotesCount + 1 WHERE tweet = :tweet")
    void updateQuotesCount(@Param("tweet") Tweet tweet);

    @Query("""
            SELECT tweet FROM Tweet tweet
            WHERE tweet.createdAt >= :sinceDate
            OR tweet.updatedAt >= :sinceDate
            """)
    List<Tweet> findByCreationAndUpdatedDate(LocalDateTime sinceDate, PageRequest pageable);
}
