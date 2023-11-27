package com.cultura;
import java.io.Serializable;
import java.sql.Timestamp;

public class Tweet implements Serializable {

    private int tweetId; 
    private String text;
    private Timestamp timestamp;
    private String username;
    private int like_count;

    // Constructor
    public Tweet( String text, String username) {
        this( text, null, username, 0);
    }

    // Constructor
    public Tweet( String text, String username, int like_count) {
        this( text, null, username, like_count);
    }

    public Tweet( String text, Timestamp timestamp, String username, int like_count) {
        this.text = text;
        this.timestamp = timestamp;
        this.username = username;
        this.like_count = like_count;
    }

    public Tweet(int id, String text, Timestamp timestamp, String username, int like_count) {
        this.tweetId = id;
        this.text = text;
        this.timestamp = timestamp;
        this.username = username;
        this.like_count = like_count;
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String toString() {
        return "Tweet{" +
                " tweetId=" + tweetId + '\'' +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", username='" + username + '\'' +
                ", like_count=" + like_count +
                '}';
    }

}