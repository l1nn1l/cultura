package com.cultura.Requests;

import java.io.Serializable;

public class GetExplorePostsRequest implements Serializable {
    public String requestName;

    public GetExplorePostsRequest(){
        this.requestName = "GetFollowersPostRequest";
    }
}
