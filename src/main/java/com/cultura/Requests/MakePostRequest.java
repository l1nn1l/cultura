package com.cultura.Requests;

import java.io.Serializable;

public class MakePostRequest implements Serializable {
    public String requestName, username, postText;

    public MakePostRequest(String username, String postText){
        this.username = username;
        this.postText = postText;
        this.requestName = "signupRequest";
    }
}
