package org.teta.mapper;

import org.teta.dto.HeaderResponse;
import org.teta.dto.response.tweet.TweetResponse;
import org.teta.dto.request.TweetRequest;
import org.teta.dto.response.user.UserResponse;
import org.teta.enums.NotificationType;
import org.teta.enums.ReplyType;
import org.teta.dto.response.NotificationReplyResponse;
import org.teta.dto.response.ProfileTweetImageResponse;
import org.teta.dto.response.TweetAdditionalInfoResponse;
import org.teta.dto.response.TweetImageResponse;
import org.teta.model.Tweet;
import org.teta.model.TweetImage;
import org.teta.repository.projection.*;
import org.teta.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TweetMapper {

    private final BasicMapper basicMapper;
    private final TweetService tweetService;

    public HeaderResponse<TweetResponse> getTweets(Pageable pageable) {
        Page<TweetProjection> tweets = tweetService.getTweets(pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public TweetResponse getTweetById(Long tweetId) {
        TweetProjection tweet = tweetService.getTweetById(tweetId);
        return basicMapper.convertToResponse(tweet, TweetResponse.class);
    }

    public TweetResponse getPinnedTweetByUserId(Long userId) {
        return tweetService.getPinnedTweetByUserId(userId)
                .map(tweet -> basicMapper.convertToResponse(tweet, TweetResponse.class))
                .orElse(null);
    }

    public HeaderResponse<TweetResponse> getUserTweets(Long userId, Pageable pageable) {
        Page<TweetUserProjection> tweets = tweetService.getUserTweets(userId, pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public HeaderResponse<TweetResponse> getUserMediaTweets(Long userId, Pageable pageable) {
        Page<TweetProjection> tweets = tweetService.getUserMediaTweets(userId, pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public List<ProfileTweetImageResponse> getUserTweetImages(Long userId) {
        List<ProfileTweetImageProjection> tweets = tweetService.getUserTweetImages(userId);
        return basicMapper.convertToResponseList(tweets, ProfileTweetImageResponse.class);
    }

    public TweetAdditionalInfoResponse getTweetAdditionalInfoById(Long tweetId) {
        TweetAdditionalInfoProjection additionalInfo = tweetService.getTweetAdditionalInfoById(tweetId);
        return basicMapper.convertToResponse(additionalInfo, TweetAdditionalInfoResponse.class);
    }

    public List<TweetResponse> getRepliesByTweetId(Long tweetId) {
        List<TweetProjection> tweets = tweetService.getRepliesByTweetId(tweetId);
        return basicMapper.convertToResponseList(tweets, TweetResponse.class);
    }

    public HeaderResponse<TweetResponse> getQuotesByTweetId(Long tweetId, Pageable pageable) {
        Page<TweetProjection> tweets = tweetService.getQuotesByTweetId(tweetId, pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public HeaderResponse<TweetResponse> getMediaTweets(Pageable pageable) {
        Page<TweetProjection> tweets = tweetService.getMediaTweets(pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public HeaderResponse<TweetResponse> getTweetsWithVideo(Pageable pageable) {
        Page<TweetProjection> tweets = tweetService.getTweetsWithVideo(pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public HeaderResponse<TweetResponse> getFollowersTweets(Pageable pageable) {
        Page<TweetProjection> tweets = tweetService.getFollowersTweets(pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public TweetImageResponse uploadTweetImage(MultipartFile file) {
        TweetImage tweetImage = tweetService.uploadTweetImage(file);
        return basicMapper.convertToResponse(tweetImage, TweetImageResponse.class);
    }

    public HeaderResponse<UserResponse> getTaggedImageUsers(Long tweetId, Pageable pageable) {
        Page<UserProjection> users = tweetService.getTaggedImageUsers(tweetId, pageable);
        return basicMapper.getHeaderResponse(users, UserResponse.class);
    }

    public TweetResponse createTweet(TweetRequest tweetRequest) {
        return tweetService.createNewTweet(basicMapper.convertToResponse(tweetRequest, Tweet.class));
    }

    public String deleteTweet(Long tweetId) {
        return tweetService.deleteTweet(tweetId);
    }

    public HeaderResponse<TweetResponse> searchTweets(String text, Pageable pageable) {
        Page<TweetProjection> tweets = tweetService.searchTweets(text, pageable);
        return basicMapper.getHeaderResponse(tweets, TweetResponse.class);
    }

    public NotificationReplyResponse replyTweet(Long tweetId, TweetRequest tweetRequest) {
        Tweet reply = basicMapper.convertToResponse(tweetRequest, Tweet.class);
        TweetResponse replyTweet = tweetService.replyTweet(tweetRequest.getAddressedId(), tweetId, reply);
        return new NotificationReplyResponse(tweetId, NotificationType.REPLY, replyTweet);
    }

    public TweetResponse quoteTweet(Long tweetId, TweetRequest tweetRequest) {
        return tweetService.quoteTweet(tweetId, basicMapper.convertToResponse(tweetRequest, Tweet.class));
    }

    public TweetResponse changeTweetReplyType(Long tweetId, ReplyType replyType) {
        TweetProjection tweet = tweetService.changeTweetReplyType(tweetId, replyType);
        return basicMapper.convertToResponse(tweet, TweetResponse.class);
    }
}
