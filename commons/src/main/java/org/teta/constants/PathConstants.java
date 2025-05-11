package org.teta.constants;

public class PathConstants {

    public static final String API_V1 = "/api/v1";
    public static final String UI_V1 = "/ui/v1";

    public static final String AUTH = "/auth";
    public static final String UI_V1_AUTH = UI_V1 + AUTH;
    public static final String LOGIN = "/login";
    public static final String REGISTRATION_CHECK = "/registration/check";
    public static final String REGISTRATION_CODE = "/registration/code";
    public static final String REGISTRATION_ACTIVATE_CODE = "/registration/activate/{code}";
    public static final String REGISTRATION_CONFIRM = "/registration/confirm";
    public static final String FORGOT = "/forgot";
    public static final String FORGOT_EMAIL = FORGOT + "/email";
    public static final String RESET = "/reset";
    public static final String RESET_CODE = RESET + "/{code}";
    public static final String RESET_CURRENT = RESET + "/current";

    public static final String USER = "/user";
    public static final String API_V1_USER = API_V1 + USER;
    public static final String UI_V1_USER = UI_V1 + USER;
    public static final String BLOCKED = "/blocked";
    public static final String BLOCKED_USER_ID = BLOCKED + "/{userId}";
    public static final String FOLLOWERS_USER_ID = "/followers/{userId}";
    public static final String FOLLOWING_USER_ID = "/following/{userId}";
    public static final String FOLLOWER_REQUESTS = "/follower-requests";
    public static final String FOLLOW_USER_ID = "/follow/{userId}";
    public static final String FOLLOW_OVERALL = "/follow/overall/{userId}";
    public static final String FOLLOW_PRIVATE = "/follow/private/{userId}";
    public static final String FOLLOW_ACCEPT = "/follow/accept/{userId}";
    public static final String FOLLOW_DECLINE = "/follow/decline/{userId}";
    public static final String MUTED = "/muted";
    public static final String MUTED_USER_ID = MUTED + "/{userId}";
    public static final String TOKEN = "/token";
    public static final String USER_ID = "/{userId}";
    public static final String ALL = "/all";
    public static final String RELEVANT = "/relevant";
    public static final String SEARCH_USERNAME = "/items/search/{username}";
    public static final String START = "/start";
    public static final String SUBSCRIBE_USER_ID = "/subscribe/{userId}";
    public static final String PIN_TWEET_ID = "/pin/tweet/{tweetId}";
    public static final String DETAILS_USER_ID = "/details/{userId}";
    public static final String BATCH_USERS = "/batch/users";

    public static final String UI_V1_USER_SETTINGS_UPDATE = UI_V1_USER + "/settings/update";
    public static final String USERNAME = "/username";
    public static final String EMAIL = "/email";
    public static final String PHONE = "/phone";
    public static final String COUNTRY = "/country";
    public static final String GENDER = "/gender";
    public static final String LANGUAGE = "/language";
    public static final String DIRECT = "/direct";
    public static final String PRIVATE = "/private";
    public static final String COLOR_SCHEME = "/color_scheme";
    public static final String BACKGROUND_COLOR = "/background_color";

    public static final String API_V1_AUTH = API_V1 + AUTH;
    public static final String USER_EMAIL = "/user/{email}";
    public static final String IDS = "/ids";
    public static final String SUBSCRIBERS = "/subscribers";
    public static final String SUBSCRIBERS_IDS = SUBSCRIBERS + "/ids";

    public static final String TAGS = "/tags";
    public static final String UI_V1_TAGS = UI_V1 + TAGS;
    public static final String TRENDS = "/trends";
    public static final String SEARCH = "/search";
    public static final String API_V1_TAGS = API_V1 + TAGS;

    public static final String TWEETS = "/tweets";
    public static final String API_V1_TWEETS = API_V1 + TWEETS;
    public static final String USER_IDS = "/user/ids";
    public static final String TWEET_ID = "/{tweetId}";
    public static final String ID_TWEET_ID = "/id/{tweetId}";
    public static final String COUNT_TEXT = "/count/{text}";
    public static final String CHAT_TWEET_ID = "/chat/{tweetId}";
    public static final String UI_V1_TWEETS = UI_V1 + TWEETS;
    public static final String USER_BOOKMARKS = "/user/bookmarks";
    public static final String USER_BOOKMARKS_TWEET_ID = USER_BOOKMARKS + "/{tweetId}";
    public static final String TWEET_ID_BOOKMARKED = "/{tweetId}/bookmarked";
    public static final String LIKED_USER_USER_ID = "/liked/user/{userId}";
    public static final String TWEET_ID_LIKED_USERS = "/{tweetId}/liked-users";
    public static final String LIKE_USER_ID_TWEET_ID = "/like/{userId}/{tweetId}";
    public static final String POLL = "/poll";
    public static final String VOTE = "/vote";
    public static final String REPLIES_USER_ID = "/replies/user/{userId}";
    public static final String TWEET_ID_RETWEETED_USERS = "/{tweetId}/retweeted-users";
    public static final String RETWEET_USER_ID_TWEET_ID = "/retweet/{userId}/{tweetId}";
    public static final String USER_USER_ID = "/user/{userId}";
    public static final String PINNED_TWEET_USER_ID = "/pinned/user/{userId}";
    public static final String MEDIA_USER_USER_ID = "/media/user/{userId}";
    public static final String IMAGES_USER_ID = "/images/{userId}";
    public static final String TWEET_ID_INFO = "/{tweetId}/info";
    public static final String TWEET_ID_REPLIES = "/{tweetId}/replies";
    public static final String TWEET_ID_QUOTES = "/{tweetId}/quotes";
    public static final String MEDIA = "/media";
    public static final String VIDEO = "/video";
    public static final String FOLLOWER = "/follower";
    public static final String SCHEDULE = "/schedule";
    public static final String IMAGE_TAGGED = "/image/tagged/{tweetId}";
    public static final String SEARCH_TEXT = "/search/{text}";
    public static final String SEARCH_RESULTS = "/search/results";
    public static final String REPLY_USER_ID_TWEET_ID = "/reply/{userId}/{tweetId}";
    public static final String QUOTE_USER_ID_TWEET_ID = "/quote/{userId}/{tweetId}";
    public static final String REPLY_CHANGE_USER_ID_TWEET_ID = "/reply/change/{userId}/{tweetId}";
    public static final String BATCH_TWEETS = "/batch/tweets";

    public static final String API_V1_IMAGE = API_V1 + "/image";
    public static final String UPLOAD = "/upload";

    public static final String API_V1_EMAIL = API_V1 + "/email";
    public static final String SUGGESTED = "/suggested";

    public static final String TOPICS = "/topics";
    public static final String UI_V1_TOPICS = UI_V1 + TOPICS;
    public static final String API_V1_TOPICS = API_V1 + TOPICS;
    public static final String CATEGORY = "/category";
    public static final String FOLLOWED = "/followed";
    public static final String FOLLOWED_USER_ID = "/followed/{userId}";
    public static final String NOT_INTERESTED = "/not_interested";
    public static final String NOT_INTERESTED_TOPIC_ID = NOT_INTERESTED + "/{topicId}";
    public static final String FOLLOW_TOPIC_ID = "/follow/{topicId}";

    public static final String LISTS = "/lists";
    public static final String API_V1_LISTS = API_V1 + LISTS;
    public static final String UI_V1_LISTS = UI_V1 + LISTS;
    public static final String USER_CONSIST = "/user/consist";
    public static final String PINED = "/pined";
    public static final String LIST_ID = "/{listId}";
    public static final String TWEET_LIST_ID = "/tweet/{listId}";
    public static final String FOLLOW_LIST_ID = "/follow/{listId}";
    public static final String PIN_LIST_ID = "/pin/{listId}";
    public static final String ADD_USER_USER_ID = "/add/user/{userId}";
    public static final String ADD_USER = "/add/user";
    public static final String ADD_USER_LIST_ID = "/add/user/{userId}/{listId}";
    public static final String LIST_ID_TWEETS = "/{listId}/tweets";
    public static final String LIST_ID_DETAILS = "/{listId}/details";
    public static final String LIST_ID_FOLLOWERS = "/{listId}/{listOwnerId}/followers";
    public static final String LIST_ID_MEMBERS = "/{listId}/{listOwnerId}/members";
    public static final String SEARCH_LIST_ID = "/search/{listId}/{username}";

    public static final String CHAT = "/chat";
    public static final String UI_V1_CHAT = UI_V1 + CHAT;
    public static final String API_V1_CHAT = API_V1 + CHAT;
    public static final String CHAT_ID = "/{chatId}";
    public static final String USERS = "/users";
    public static final String CREATE_USER_ID = "/create/{userId}";
    public static final String CHAT_ID_MESSAGES = "/{chatId}/messages";
    public static final String CHAT_ID_READ_MESSAGES = "/{chatId}/read/messages";
    public static final String ADD_MESSAGE = "/add/message";
    public static final String ADD_MESSAGE_TWEET = ADD_MESSAGE + "/tweet";
    public static final String PARTICIPANT_CHAT_ID = "/participant/{participantId}/{chatId}";
    public static final String LEAVE_CHAT_ID = "/leave/{participantId}/{chatId}";

    public static final String NOTIFICATION = "/notification";
    public static final String TWEET = "/tweet";
    public static final String UI_V1_NOTIFICATION = UI_V1 + NOTIFICATION;
    public static final String SUBSCRIBES = "/subscribes";
    public static final String MENTIONS = "/mentions";
    public static final String NOTIFICATION_ID = "/{notificationId}";
    public static final String TIMELINE = "/timeline";

    public static final String LOCALIZATION = "/localization";
    public static final String UI_V1_LOCALIZATION = UI_V1 + LOCALIZATION;
    public static final String API_V1_LOCALIZATION = API_V1 + LOCALIZATION;
    public static final String COUNTRY_CODES = "/country/codes";
    public static final String GIF_IMAGES = "/gif/images";
    public static final String LANGUAGES = "/languages";
    public static final String WALLPAPERS = "/wallpapers";
    public static final String PHONE_CODE = "/phone/{code}";
    public static final String WALLPAPER = "/wallpaper";
    public static final String TRANSLATION = "/translation";
    public static final String TRANSLATION_KEY = "/{translationKey}";
    public static final String TRANSLATIONS = "/translations";

    public static final String SCHEDULER = "/scheduler";
    public static final String API_V1_SCHEDULER = API_V1 + SCHEDULER;
    public static final String JOBS = "/jobs";
    public static final String JOBS_NAME_GROUP_NAME = JOBS + "/{jobName}/{groupName}";
    public static final String JOBS_TRIGGERS_NAME_GROUP_NAME = JOBS + "/triggers/{jobName}/{groupName}";
    public static final String JOBS_CREATE = JOBS + "/create";
    public static final String JOBS_UPDATE = JOBS + "/update";
    public static final String JOBS_DELETE = JOBS + "/delete/{jobName}/{groupName}";
    public static final String USER_BATCH_JOB = "/user/batch/job";

    public static final String API_V1_WEBSOCKET = API_V1 + "/websocket";
}
