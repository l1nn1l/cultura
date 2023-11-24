package com.cultura;

public class Follow {
    
    private String followed_by_username;   
    private String following_username;

    public Follow(String followed_by_username, String following_username) {
        this.followed_by_username = followed_by_username;
        this.following_username = following_username;
    }

    public String getFollowed_by_username() {
        return followed_by_username;
    }

    public void setFollowed_by_username(String followed_by_username) {
        this.followed_by_username = followed_by_username;
    }

    public String getFollowing_username() {
        return following_username;
    }

    public void setFollowing_user_id(String following_username) {
        this.following_username = following_username;
    }  

    public String toString() {
        return "Follow{" +
                "followed_by_username='" + followed_by_username + '\'' +
                ", following_username='" + following_username + '\'' +
                '}';
    }
}