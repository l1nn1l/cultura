package com.cultura.objects;

import java.io.Serializable;

public class Reactions implements Serializable {
    private int id;
    private String username;
    private int tweetId;
    private String reactionType;
    private String url;

    // Constructors

    public Reactions() {
    }

    public Reactions(int tweetId, String username, String url) {
        this.username = username;
        this.tweetId = tweetId;
        this.reactionType = "none";
        if (url.contains("thumbs-up") || url.contains("like")){
            this.reactionType = "like";
        } else if (url.contains("pink-heart") || url.contains("love"))
            this.reactionType = "love";
        else if (url.contains("smiling-face") || url.contains("smile")){
            this.reactionType = "smile";
        } else if (url.contains("partying-face") || url.contains("party")){
            this.reactionType = "party";
        } else if (url.contains("hushed-face") || url.contains("wow")){
            this.reactionType = "wow";
        }
        this.url = url;
    }

    public int getReactionId() {
        return id;
    }

    public void setReactionId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }

    @Override
    public String toString() {
        return "Reactions{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", tweetId=" + tweetId +
                ", reactionType='" + reactionType + '\'' +
                '}';
    }
}
