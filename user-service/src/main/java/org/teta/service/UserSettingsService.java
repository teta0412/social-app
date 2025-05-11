package org.teta.service;

import enums.BackgroundColorType;
import enums.ColorSchemeType;

import java.util.Map;

public interface UserSettingsService {

    String updateUsername(String username);

    Map<String, Object> updateEmail(String email);

    Map<String, Object> updatePhoneNumber(String phoneCode, Long phoneNumber);

    String deletePhoneNumber();

    String updateCountry(String country);

    String updateGender(String gender);

    String updateLanguage(String language);

    boolean updateDirectMessageRequests(boolean mutedDirectMessages);

    boolean updatePrivateProfile(boolean privateProfile);

    ColorSchemeType updateColorScheme(ColorSchemeType colorSchemeType);

    BackgroundColorType updateBackgroundColor(BackgroundColorType backgroundColorType);
}
