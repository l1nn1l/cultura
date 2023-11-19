package com.cultura;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SignupController {
    @FXML
    private void goToLogin(ActionEvent event) {
        try {
            App.setRoot("login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
