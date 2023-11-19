package com.cultura;

import java.io.IOException;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private void goToSignup() throws IOException {
        App.setRoot("signup");
    }
    
}
