package com.cultura.Requests;

public class SignupRequest {
    public String requestName, username, name, password, email;

    public SignupRequest(String username, String name, String password, String email){
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.requestName = "signupRequest";
    }
}
