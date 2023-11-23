package com.cultura;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static Server.UserFunctions.loginUser;

public class LoginController {

    Client client;
    public void setClient(Client client){
        this.client = client;
    }

    @FXML
    private void goToSignup() throws IOException {
        App.setRoot("signup");
    }

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLoginButton(){
        System.out.println("inside the login button");
       String username = usernameTextField.getText();
       String password = passwordField.getText();
       loginUser(username, password);
    }
    
}
