CREATE SEQUENCE tweets_seq START 100 INCREMENT 1;
CREATE SEQUENCE tweet_image_seq START 100 INCREMENT 1;
CREATE SEQUENCE retweets_seq START 100 INCREMENT 1;
CREATE SEQUENCE liked_tweets_seq START 100 INCREMENT 1;
CREATE SEQUENCE bookmarks_seq START 100 INCREMENT 1;
CREATE SEQUENCE gif_image_seq START 100 INCREMENT 1;

CREATE TABLE gif_image
(
    id     INT8         NOT NULL,
    height INT8,
    url    VARCHAR(255) NOT NULL,
    width  INT8,
    PRIMARY KEY (id)
);

CREATE TABLE tweets
(
    id                 INT8         NOT NULL,
    tweet_text         VARCHAR(255) NOT NULL,
    tweet_type         VARCHAR(255) NOT NULL DEFAULT 'TWEET',
    created_at         TIMESTAMP    NOT NULL,
    updated_at         TIMESTAMP,
    scheduled_date     TIMESTAMP,
    reply_type         VARCHAR(255) NOT NULL DEFAULT 'EVERYONE',
    link               VARCHAR(255),
    link_title         VARCHAR(255),
    link_description   VARCHAR(255),
    link_cover         VARCHAR(255),
    image_description  VARCHAR(255),
    deleted            BOOLEAN      NOT NULL DEFAULT FALSE,
    link_cover_size    VARCHAR(255),
    retweets_count     INT8         NOT NULL DEFAULT 0,
    replies_count      INT8         NOT NULL DEFAULT 0,
    likes_count        INT8         NOT NULL DEFAULT 0,
    quotes_count       INT8         NOT NULL DEFAULT 0,
    author_id          INT8         NOT NULL REFERENCES users,
    list_id            INT8,
    addressed_user_id  INT8 REFERENCES users,
    addressed_tweet_id INT8 REFERENCES tweets,
    poll_id            INT8 REFERENCES polls,
    gif_image_id       INT8 REFERENCES gif_image,
    PRIMARY KEY (id)
);
CREATE INDEX tweets_author_id_idx ON tweets (author_id);

CREATE TABLE tweet_images
(
    id  INT8         NOT NULL,
    src VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tweets_images
(
    tweet_id INT8        NOT NULL REFERENCES tweets,
    image_id INT8 UNIQUE NOT NULL REFERENCES tweet_images
);

CREATE TABLE tweet_quotes
(
    quote_tweet_id INT8 REFERENCES tweets,
    tweet_id       INT8 NOT NULL REFERENCES tweets,
    PRIMARY KEY (tweet_id)
);

CREATE TABLE retweets
(
    retweet_id INT8 NOT NULL REFERENCES tweets,
    tweet_id   INT8 NOT NULL REFERENCES tweets
);

CREATE TABLE replies
(
    tweet_id INT8 NOT NULL REFERENCES tweets,
    reply_id INT8 NOT NULL REFERENCES tweets
);

CREATE TABLE quotes
(
    tweet_id INT8 NOT NULL REFERENCES tweets,
    quote_id INT8 NOT NULL UNIQUE REFERENCES tweets
);

CREATE TABLE liked_tweets
(
    tweet_id         INT8      NOT NULL REFERENCES tweets,
    user_id          INT8      NOT NULL REFERENCES users,
    liked_tweet_date TIMESTAMP NOT NULL DEFAULT current_timestamp
);
CREATE INDEX liked_tweets_tweet_id_idx ON liked_tweets (tweet_id);
CREATE INDEX liked_tweets_user_id_idx ON liked_tweets (user_id);

CREATE TABLE tagged_image_users
(
    tweet_id             INT8 NOT NULL REFERENCES tweets,
    tagged_image_user_id INT8 NOT NULL REFERENCES users
);

CREATE TABLE bookmarks
(
    tweet_id      INT8      NOT NULL REFERENCES tweets,
    user_id       INT8      NOT NULL REFERENCES users,
    bookmark_date TIMESTAMP NOT NULL DEFAULT current_timestamp
);
CREATE INDEX bookmarks_tweet_id_idx ON bookmarks (tweet_id);
CREATE INDEX bookmarks_user_id_idx ON bookmarks (user_id);
