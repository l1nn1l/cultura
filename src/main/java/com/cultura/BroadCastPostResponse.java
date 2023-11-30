package com.cultura;

import com.cultura.Requests.MakePostRequest;

import java.io.Serializable;

public class BroadCastPostResponse implements Serializable {
    public String responseType;
    public Tweet tweet;

    public BroadCastPostResponse(Tweet tweet){
        this.tweet = tweet;
    }
    public BroadCastPostResponse(MakePostRequest postRequest){
        this.tweet = new Tweet(postRequest.postText, postRequest.username);
    }
}
