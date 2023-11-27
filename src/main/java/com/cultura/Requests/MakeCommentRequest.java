package com.cultura.Requests;

import java.io.Serializable;

public class MakeCommentRequest implements Serializable {
        public String requestName, username, commentText;
        public int tweetId;

        public MakeCommentRequest (String username, String commentText, int tweetId){
            this.username = username;
            this.commentText = commentText;
            this.tweetId = tweetId;
            this.requestName = "MakeCommentRequest";
        }
    
}
