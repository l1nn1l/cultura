package com.cultura.objects;

import java.sql.Timestamp;

public class Comment {
    private String commenterUsername; 
    private Timestamp timestamp;
    private String commentText;


    public Comment( String commenterUsername, Timestamp timestamp, String commentText) {
        this.commentText = commentText;
        this.timestamp = timestamp;
        this.commenterUsername = commenterUsername;
    }

    public Comment( String commenterUsername, String commentText) {
        this.commentText = commentText;
        this.commenterUsername = commenterUsername;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText){
        this.commentText = commentText;

    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
    }

    public String getCommenterUsername() {
        return commenterUsername;
    }

    public void setCommenterUsername(String commenterUsername){
        this.commenterUsername = commenterUsername;
    }

    public String toString() {
        return "Comment{" +
                "text='" + commentText + '\'' +
                ", timestamp=" + timestamp +
                ", username='" + commenterUsername + '\'' +
                '}';
    }
}
