package com.cultura.Requests;

import java.io.Serializable;

public class PostRequest implements Serializable {
    public String requestName, username, postText;

    public PostRequest(String username, String postText){
        this.username = username;
        this.postText = postText;
        this.requestName = "signupRequest";
    }
}
