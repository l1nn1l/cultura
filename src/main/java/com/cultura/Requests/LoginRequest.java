package com.cultura.Requests;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    public String requestName, username, password;

    public LoginRequest(String username,String password){
        this.username = username;
        this.password = password;
        this.requestName = "loginRequest";
    }
}
