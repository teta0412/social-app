package org.teta.repository;

import org.teta.model.User;
import org.teta.repository.projection.FollowerUserProjection;
import org.teta.repository.projection.UserProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowerUserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT user FROM User user
            LEFT JOIN user.following following
            WHERE following.id = :userId
            """)
    Page<UserProjection> getFollowersById(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT user FROM User user
            LEFT JOIN user.followers follower
            WHERE follower.id = :userId
            """)
    Page<UserProjection> getFollowingById(@Param("userId") Long userId, Pageable pageable);

    @Query(value = """
            SELECT *, users.full_name as fullName FROM users
            LEFT JOIN user_follower_requests ufr ON users.id = ufr.follower_id
            WHERE ufr.user_id = :userId
            """, nativeQuery = true)
    Page<FollowerUserProjection> getFollowerRequests(@Param("userId") Long userId, Pageable pageable);

    @Query("""
            SELECT CASE WHEN count(follower) > 0 THEN true ELSE false END
            FROM User user
            LEFT JOIN user.followers follower
            WHERE user.id = :authUserId
            AND follower.id = :userId
            """)
    boolean isUserFollowByOtherUser(@Param("authUserId") Long authUserId, @Param("userId") Long userId);

    @Query("""
            SELECT CASE WHEN count(user) > 0 THEN true ELSE false END FROM User user
            LEFT JOIN user.followers follower
            WHERE follower.id = :userId
            AND user.id = :authUserId
            """)
    boolean isFollower(@Param("authUserId") Long authUserId, @Param("userId") Long userId);

    @Modifying
    @Query(value = "INSERT INTO user_subscriptions (user_id, subscriber_id) VALUES (?1, ?2)", nativeQuery = true)
    void follow(@Param("authUserId") Long authUserId, @Param("userId") Long userId);

    @Modifying
    @Query(value = "DELETE FROM user_subscriptions WHERE user_id = ?1 AND subscriber_id = ?2", nativeQuery = true)
    void unfollow(@Param("authUserId") Long authUserId, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User user SET user.followersCount =
                CASE WHEN :increaseCount = true
                THEN (user.followersCount + 1)
                ELSE (user.followersCount - 1)
                END
            WHERE user.id = :userId
            """)
    void updateFollowersCount(@Param("increaseCount") boolean increaseCount, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User user SET user.followingCount =
                CASE WHEN :increaseCount = true
                THEN (user.followingCount + 1)
                ELSE (user.followingCount - 1)
                END
            WHERE user.id = :userId
            """)
    void updateFollowingCount(@Param("increaseCount") boolean increaseCount, @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE User user SET user.followerRequestsCount =
                CASE WHEN :increaseCount = true
                THEN (user.followerRequestsCount + 1)
                ELSE (user.followerRequestsCount - 1)
                END
            WHERE user.id = :userId
            """)
    void updateFollowerRequestsCount(@Param("increaseCount") boolean increaseCount, @Param("userId") Long userId);

    @Modifying
    @Query(value = """
            INSERT INTO user_follower_requests (follower_id, user_id)
            SELECT * FROM (SELECT ?1, ?2) AS tmp
            WHERE NOT EXISTS (
               SELECT follower_id FROM user_follower_requests WHERE follower_id = ?1
            ) LIMIT 1
            """, nativeQuery = true)
    void addFollowerRequest(@Param("authUserId") Long authUserId, @Param("userId") Long userId);

    @Modifying
    @Query(value = "DELETE FROM user_follower_requests WHERE follower_id = ?1 AND user_id = ?2", nativeQuery = true)
    void removeFollowerRequest(@Param("authUserId") Long authUserId, @Param("userId") Long userId);

    @Query(value = """
            SELECT users.id as id, users.full_name as fullName, users.username as username, users.about as about,
            users.private_profile as privateProfile, users.avatar as avatar
            FROM users
            WHERE users.id IN (
               SELECT user_subscriptions.subscriber_id FROM users
               JOIN user_subscriptions ON users.id = user_subscriptions.user_id
               WHERE users.id = ?1)
            INTERSECT
            SELECT users.id as id, users.full_name as fullName, users.username as username, users.about as about,
            users.private_profile as isPrivateProfile, users.avatar as avatar
            FROM users
            WHERE users.id IN (
            SELECT user_subscriptions.subscriber_id FROM users
            JOIN user_subscriptions ON users.id = user_subscriptions.user_id
            WHERE users.id = ?2)
            """, nativeQuery = true)
    <T> List<T> getSameFollowers(@Param("userId") Long userId, @Param("authUserId") Long authUserId, Class<T> type);

    @Query("""
            SELECT CASE WHEN count(user) > 0 THEN true ELSE false END FROM User user
            LEFT JOIN user.followerRequests followerRequest
            WHERE user.id = :userId
            AND followerRequest.id = :authUserId
            """)
    boolean isFollowerRequest(@Param("userId") Long userId, @Param("authUserId") Long authUserId);
}
