package org.teta.service.impl;

import org.teta.event.UpdateUserEvent;
import org.teta.event.UserNotificationDto;
import org.teta.model.User;
import org.teta.repository.UserRepository;
import org.teta.service.UserHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserHandlerServiceImpl implements UserHandlerService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User handleNewOrUpdateUser(UpdateUserEvent userEvent) {
        return userRepository.findById(userEvent.getId())
                .map(user -> {
                    user.setUsername(userEvent.getUsername());
                    user.setAvatar(userEvent.getAvatar());
                    return user;
                })
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setId(userEvent.getId());
                    newUser.setUsername(userEvent.getUsername());
                    newUser.setAvatar(userEvent.getAvatar());
                    return userRepository.save(newUser);
                });
    }

    @Override
    @Transactional
    public User getOrCreateUser(UserNotificationDto user) {
        return userRepository.findById(user.getId())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setId(user.getId());
                    newUser.setUsername(user.getUsername());
                    newUser.setAvatar(user.getAvatar());
                    return userRepository.save(newUser);
                });
    }
}
