package org.teta.service.impl;

import org.teta.broker.producer.UpdateUserProducer;
import org.teta.client.LocalizationClient;
import enums.BackgroundColorType;
import enums.ColorSchemeType;
import exception.ApiRequestException;
import security.JwtProvider;
import org.teta.constants.UserErrorMessage;
import org.teta.constants.UserSuccessMessage;
import org.teta.model.User;
import org.teta.model.UserRole;
import org.teta.repository.UserRepository;
import org.teta.repository.UserSettingsRepository;
import org.teta.repository.projection.AuthUserProjection;
import org.teta.service.AuthenticationService;
import org.teta.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserSettingsServiceImpl implements UserSettingsService {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final JwtProvider jwtProvider;
    private final UpdateUserProducer updateUserProducer;
    private final LocalizationClient localizationClient;

    @Override
    @Transactional
    public String updateUsername(String username) {
        if (username.length() == 0 || username.length() > 50) {
            throw new ApiRequestException(UserErrorMessage.INCORRECT_USERNAME_LENGTH, HttpStatus.BAD_REQUEST);
        }
        User user = authenticationService.getAuthenticatedUser();
        user.setUsername(username);
        updateUserProducer.sendUpdateUserEvent(user);
        return username;
    }

    @Override
    @Transactional
    public Map<String, Object> updateEmail(String email) {
        Long authUserId = authenticationService.getAuthenticatedUserId();

        if (!userSettingsRepository.isEmailExist(authUserId, email)) {
            userSettingsRepository.updateEmail(email, authUserId);
            String token = jwtProvider.createToken(email, UserRole.USER.name());
            AuthUserProjection user = userRepository.getUserById(authUserId, AuthUserProjection.class).get();
            return Map.of("user", user, "token", token);
        }
        throw new ApiRequestException(UserErrorMessage.EMAIL_HAS_ALREADY_BEEN_TAKEN, HttpStatus.FORBIDDEN);
    }

    @Override
    @Transactional
    public Map<String, Object> updatePhoneNumber(String phoneCode, Long phoneNumber) {
        int phoneLength = String.valueOf(phoneNumber).length();

        if (phoneLength < 6 || phoneLength > 10) {
            throw new ApiRequestException(UserErrorMessage.INVALID_PHONE_NUMBER, HttpStatus.BAD_REQUEST);
        }
        if (!localizationClient.isPhoneCodeExists(phoneCode)) {
            throw new ApiRequestException(UserErrorMessage.PHONE_CODE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updatePhoneNumber(phoneCode, phoneNumber, authUserId);
        return Map.of("phoneCode", phoneCode, "phoneNumber", phoneNumber);
    }

    @Override
    @Transactional
    public String deletePhoneNumber() {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updatePhoneNumber(null, null, authUserId);
        return UserSuccessMessage.PHONE_NUMBER_DELETED;
    }

    @Override
    @Transactional
    public String updateCountry(String country) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateCountry(country, authUserId);
        return country;
    }

    @Override
    @Transactional
    public String updateGender(String gender) {
        if (gender.length() == 0 || gender.length() > 30) {
            throw new ApiRequestException(UserErrorMessage.INVALID_GENDER_LENGTH, HttpStatus.BAD_REQUEST);
        }
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateGender(gender, authUserId);
        return gender;
    }

    @Override
    @Transactional
    public String updateLanguage(String language) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateLanguage(language, authUserId);
        return language;
    }

    @Override
    @Transactional
    public boolean updateDirectMessageRequests(boolean mutedDirectMessages) {
        User user = authenticationService.getAuthenticatedUser();
        user.setMutedDirectMessages(mutedDirectMessages);
        updateUserProducer.sendUpdateUserEvent(user);
        return mutedDirectMessages;
    }

    @Override
    @Transactional
    public boolean updatePrivateProfile(boolean privateProfile) {
        User user = authenticationService.getAuthenticatedUser();
        user.setPrivateProfile(privateProfile);
        updateUserProducer.sendUpdateUserEvent(user);
        return privateProfile;
    }

    @Override
    @Transactional
    public ColorSchemeType updateColorScheme(ColorSchemeType colorSchemeType) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateColorScheme(colorSchemeType, authUserId);
        return colorSchemeType;
    }

    @Override
    @Transactional
    public BackgroundColorType updateBackgroundColor(BackgroundColorType backgroundColorType) {
        Long authUserId = authenticationService.getAuthenticatedUserId();
        userSettingsRepository.updateBackgroundColor(backgroundColorType, authUserId);
        return backgroundColorType;
    }
}
