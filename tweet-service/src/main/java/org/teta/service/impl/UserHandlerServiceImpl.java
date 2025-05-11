package org.teta.service.impl;

import event.*;
import org.teta.model.Tweet;
import org.teta.model.User;
import org.teta.repository.TweetRepository;
import org.teta.repository.UserRepository;
import org.teta.service.UserHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserHandlerServiceImpl implements UserHandlerService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    @Override
    @Transactional
    public User handleNewOrUpdateUser(UpdateUserEvent updateUserEvent) {
        return userRepository.findById(updateUserEvent.getId())
                .map(user -> {
                    user.setUsername(updateUserEvent.getUsername());
                    user.setFullName(updateUserEvent.getFullName());
                    user.setAbout(updateUserEvent.getAbout());
                    user.setAvatar(updateUserEvent.getAvatar());
                    user.setPrivateProfile(updateUserEvent.isPrivateProfile());
                    user.setActive(updateUserEvent.isActive());
                    user.setMutedDirectMessages(updateUserEvent.isMutedDirectMessages());
                    return user;
                })
                .orElseGet(() -> createUser(updateUserEvent));
    }

    @Override
    @Transactional
    public void handleBlockUser(BlockUserEvent blockUserEvent, String authId) {
        User user = userRepository.findById(blockUserEvent.getId())
                .orElseGet(() -> createUser(blockUserEvent));
        User authUser = userRepository.findById(Long.parseLong(authId)).get();

        if (blockUserEvent.isUserBlocked()) {
            authUser.getUserBlockedList().add(user);
            authUser.getFollowers().remove(user);
            authUser.getFollowing().remove(user);
        } else {
            authUser.getUserBlockedList().remove(user);
        }
    }

    @Override
    @Transactional
    public void handleMuteUser(MuteUserEvent muteUserEvent, String authId) {
        User user = userRepository.findById(muteUserEvent.getId())
                .orElseGet(() -> createUser(muteUserEvent));
        User authUser = userRepository.findById(Long.parseLong(authId)).get();

        if (muteUserEvent.isUserMuted()) {
            authUser.getUserMutedList().add(user);
        } else {
            authUser.getUserMutedList().remove(user);
        }
    }

    @Override
    @Transactional
    public void handleFollowUser(FollowUserEvent followUserEvent, String authId) {
        User user = userRepository.findById(followUserEvent.getId())
                .orElseGet(() -> createUser(followUserEvent));
        User authUser = userRepository.findById(Long.parseLong(authId)).get();

        if (followUserEvent.isUserFollow()) {
            authUser.getFollowers().add(user);
        } else {
            authUser.getFollowers().remove(user);
        }
    }

    @Override
    @Transactional
    public void handleFollowUserRequest(FollowRequestUserEvent followRequestUserEvent, String authId) {
        User user = userRepository.findById(followRequestUserEvent.getId())
                .orElseGet(() -> createUser(followRequestUserEvent));
        User authUser = userRepository.findById(Long.parseLong(authId)).get();

        if (followRequestUserEvent.isUserFollowRequest()) {
            user.getFollowerRequests().add(authUser);
        } else {
            user.getFollowerRequests().remove(authUser);
        }
    }

    @Override
    @Transactional
    public void handlePinTweet(PinTweetEvent pinTweetEvent, String authId) {
        User authUser = userRepository.findById(Long.parseLong(authId)).get();
        if (pinTweetEvent.getTweetId() == null) {
            authUser.setPinnedTweet(null);
        } else {
            Tweet pinnedTweet = tweetRepository.findById(pinTweetEvent.getTweetId()).orElse(null);
            authUser.setPinnedTweet(pinnedTweet);
        }
    }

    private User createUser(UserEvent userEvent) {
        User newUser = new User();
        newUser.setId(userEvent.getId());
        newUser.setUsername(userEvent.getUsername());
        newUser.setFullName(userEvent.getFullName());
        newUser.setAbout(userEvent.getAbout());
        newUser.setAvatar(userEvent.getAvatar());
        newUser.setPrivateProfile(userEvent.isPrivateProfile());
        newUser.setActive(userEvent.isActive());
        newUser.setMutedDirectMessages(userEvent.isActive());
        return userRepository.save(newUser);
    }
}
