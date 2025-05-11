package org.teta.repository;

import org.teta.enums.BackgroundColorType;
import org.teta.enums.ColorSchemeType;
import org.teta.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSettingsRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT CASE WHEN count(user) > 0 THEN true ELSE false END FROM User user
            WHERE user.id <> :userId
            AND user.email = :email
            """)
    boolean isEmailExist(@Param("userId") Long userId, @Param("email") String email);

    @Modifying
    @Query("UPDATE User user SET user.email = :email WHERE user.id = :userId")
    void updateEmail(@Param("email") String email, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User user SET user.phoneCode = :phoneCode, user.phoneNumber = :phoneNumber WHERE user.id = :userId")
    void updatePhoneNumber(@Param("phoneCode") String phoneCode, @Param("phoneNumber") Long phoneNumber, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User user SET user.country = :country WHERE user.id = :userId")
    void updateCountry(@Param("country") String country, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User user SET user.gender = :gender WHERE user.id = :userId")
    void updateGender(@Param("gender") String gender, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User user SET user.language = :language WHERE user.id = :userId")
    void updateLanguage(@Param("language") String language, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User user SET user.colorScheme = :colorSchemeType WHERE user.id = :userId")
    void updateColorScheme(@Param("colorSchemeType") ColorSchemeType colorSchemeType, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User user SET user.backgroundColor = :backgroundColor WHERE user.id = :userId")
    void updateBackgroundColor(@Param("backgroundColor") BackgroundColorType backgroundColorType, @Param("userId") Long userId);
}
