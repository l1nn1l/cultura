package com.cultura.Requests;

import java.io.Serializable;

public class GetUsersPostsRequest implements Serializable {
    public String requestName, username;

    public GetUsersPostsRequest(String username){
        this.username = username;
        this.requestName = "getUsersPostsRequest";
    }
}
