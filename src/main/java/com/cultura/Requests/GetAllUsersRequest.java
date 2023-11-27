package com.cultura.Requests;

import java.io.Serializable;

public class GetAllUsersRequest implements Serializable {
    public String requestName;

    public GetAllUsersRequest(){
        this.requestName = "GetAllUsersRequest";
    }
}
