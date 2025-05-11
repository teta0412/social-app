package org.teta.mapper;

import org.teta.enums.BackgroundColorType;
import org.teta.enums.ColorSchemeType;
import org.teta.dto.request.SettingsRequest;
import org.teta.dto.response.AuthenticationResponse;
import org.teta.dto.response.UserPhoneResponse;
import org.teta.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserSettingsMapper {

    private final AuthenticationMapper authenticationMapper;
    private final UserSettingsService userSettingsService;

    public String updateUsername(SettingsRequest request) {
        return userSettingsService.updateUsername(request.getUsername());
    }

    public AuthenticationResponse updateEmail(SettingsRequest request) {
        Map<String, Object> stringObjectMap = userSettingsService.updateEmail(request.getEmail());
        AuthenticationResponse authenticationResponse = authenticationMapper.getAuthenticationResponse(stringObjectMap);
        authenticationResponse.getUser().setEmail(request.getEmail());
        return authenticationResponse;
    }

    public UserPhoneResponse updatePhoneNumber(SettingsRequest request) {
        Map<String, Object> phoneParams = userSettingsService.updatePhoneNumber(request.getPhoneCode(), request.getPhoneNumber());
        return new UserPhoneResponse((String) phoneParams.get("phoneCode"), (Long) phoneParams.get("phoneNumber"));
    }

    public String deletePhoneNumber() {
        return userSettingsService.deletePhoneNumber();
    }

    public String updateCountry(SettingsRequest request) {
        return userSettingsService.updateCountry(request.getCountry());
    }

    public String updateGender(SettingsRequest request) {
        return userSettingsService.updateGender(request.getGender());
    }

    public String updateLanguage(SettingsRequest request) {
        return userSettingsService.updateLanguage(request.getLanguage());
    }

    public boolean updateDirectMessageRequests(SettingsRequest request) {
        return userSettingsService.updateDirectMessageRequests(request.isMutedDirectMessages());
    }

    public boolean updatePrivateProfile(SettingsRequest request) {
        return userSettingsService.updatePrivateProfile(request.isPrivateProfile());
    }

    public ColorSchemeType updateColorScheme(SettingsRequest request) {
        return userSettingsService.updateColorScheme(request.getColorScheme());
    }

    public BackgroundColorType updateBackgroundColor(SettingsRequest request) {
        return userSettingsService.updateBackgroundColor(request.getBackgroundColor());
    }
}
