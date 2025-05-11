package dto.response.tweet;

import com.fasterxml.jackson.annotation.JsonProperty;
import dto.response.user.CommonUserResponse;
import lombok.Data;

@Data
public class TweetListResponse {
    private Long id;
    private String listName;
    private String altWallpaper;
    private String wallpaper;
    private CommonUserResponse listOwner;
    private Long membersSize;
    @JsonProperty("isPrivate")
    private boolean isPrivate;
}
