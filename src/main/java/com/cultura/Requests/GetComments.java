package com.cultura.Requests;

import java.io.Serializable;

public class GetComments  implements Serializable {
    public String requestName;
    public int tweetId;

    public GetComments (int tweetId){
        this.tweetId = tweetId;
        this.requestName = "MakeCommentRequest";
    }
}
