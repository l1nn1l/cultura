package com.cultura.Requests;

import java.io.Serializable;

public class GetFollowersRequest implements Serializable {
    public String requestName, username;

    public GetFollowersRequest(String username){
        this.username = username;
        this.requestName = "GetFollowersRequest";
    }
}
