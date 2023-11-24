package com.cultura.Requests;

import java.io.Serializable;

public class SignupRequest implements Serializable {
    public String requestName, username, name, password, email;

    public SignupRequest(String username, String name, String password, String email){
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.requestName = "signupRequest";
    }
}
