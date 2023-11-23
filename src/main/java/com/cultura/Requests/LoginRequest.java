package com.cultura.Requests;

public class LoginRequest {
    public String requestName, username, password;

    public LoginRequest(String username,String password){
        this.username = username;
        this.password = password;
        this.requestName = "loginRequest";
    }
}
