package org.teta.controller.rest;

import constants.PathConstants;
import enums.BackgroundColorType;
import enums.ColorSchemeType;
import org.teta.dto.request.SettingsRequest;
import org.teta.dto.response.AuthenticationResponse;
import org.teta.dto.response.UserPhoneResponse;
import org.teta.mapper.UserSettingsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(PathConstants.UI_V1_USER_SETTINGS_UPDATE)
public class UserSettingsController {

    private final UserSettingsMapper userSettingsMapper;

    @PutMapping(PathConstants.USERNAME)
    public ResponseEntity<String> updateUsername(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateUsername(request));
    }

    @PutMapping(PathConstants.EMAIL)
    public ResponseEntity<AuthenticationResponse> updateEmail(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateEmail(request));
    }

    @PutMapping(PathConstants.PHONE)
    public ResponseEntity<UserPhoneResponse> updatePhoneNumber(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updatePhoneNumber(request));
    }

    @DeleteMapping(PathConstants.PHONE)
    public ResponseEntity<String> deletePhoneNumber() {
        return ResponseEntity.ok(userSettingsMapper.deletePhoneNumber());
    }

    @PutMapping(PathConstants.COUNTRY)
    public ResponseEntity<String> updateCountry(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateCountry(request));
    }

    @PutMapping(PathConstants.GENDER)
    public ResponseEntity<String> updateGender(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateGender(request));
    }

    @PutMapping(PathConstants.LANGUAGE)
    public ResponseEntity<String> updateLanguage(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateLanguage(request));
    }

    @PutMapping(PathConstants.DIRECT)
    public ResponseEntity<Boolean> updateDirectMessageRequests(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateDirectMessageRequests(request));
    }

    @PutMapping(PathConstants.PRIVATE)
    public ResponseEntity<Boolean> updatePrivateProfile(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updatePrivateProfile(request));
    }

    @PutMapping(PathConstants.COLOR_SCHEME)
    public ResponseEntity<ColorSchemeType> updateColorScheme(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateColorScheme(request));
    }

    @PutMapping(PathConstants.BACKGROUND_COLOR)
    public ResponseEntity<BackgroundColorType> updateBackgroundColor(@RequestBody SettingsRequest request) {
        return ResponseEntity.ok(userSettingsMapper.updateBackgroundColor(request));
    }
}
