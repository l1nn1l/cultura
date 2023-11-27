package com.cultura.Requests;

import com.cultura.Follow;

import java.io.Serializable;

public class FollowRequest implements Serializable {
    public String requestName;
    public Follow follow;

    public FollowRequest(Follow follow){
        this.follow = follow;
        this.requestName = "FollowRequest";
    }
}