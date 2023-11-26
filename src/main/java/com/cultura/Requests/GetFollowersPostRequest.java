package com.cultura.Requests;

import java.io.Serializable;

public class GetFollowersPostRequest implements Serializable {
    public String requestName, username;

    public GetFollowersPostRequest(String username){
        this.username = username;
        this.requestName = "GetFollowersPostRequest";
    }
}
