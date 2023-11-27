package com.cultura.objects;

public class Reactions {
    private int id;
    private String username;
    private int tweetId;
    private String reactionType;

    // Constructors

    public Reactions() {
    }

    public Reactions(int tweetId, String username, String reactionType) {
        this.username = username;
        this.tweetId = tweetId;
        this.reactionType = reactionType;
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
