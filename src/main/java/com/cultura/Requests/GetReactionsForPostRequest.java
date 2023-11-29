package com.cultura.Requests;

import java.io.Serializable;

public class GetReactionsForPostRequest implements Serializable {
    public String requestName;
    public int tweetId;

    public GetReactionsForPostRequest(Integer tweetId){
        this.tweetId = tweetId;
        this.requestName = "getReactionsForPostRequest";
    }
}
