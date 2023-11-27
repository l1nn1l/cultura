package com.cultura;
import java.io.Serializable;
import java.sql.Timestamp;

public class TweetComments  implements Serializable {
    
    private int id;
    private int tweetId;
    private String username;
    private String commentText;
    private Timestamp timestamp;


    public TweetComments(int tweetId, String username, String commentText) {
        this(0, tweetId, username, commentText, null);
    }

    public TweetComments(int id, int tweetId, String username, String commentText) {
        this(id, tweetId, username, commentText, null);
    }
    public TweetComments(int id, int tweetId, String username, String commentText, Timestamp timestamp) {
        this.id = id;
        this.tweetId = tweetId;
        this.username = username;
        this.commentText = commentText;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getTweetId() {
        return tweetId;
    }
    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public String getUsername() {
        return username;
    }

    public String getCommentText() {
        return commentText;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    // toString method

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", tweetId=" + tweetId +
                ", username='" + username + '\'' +
                ", commentText='" + commentText + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

    