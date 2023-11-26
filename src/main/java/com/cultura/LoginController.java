package com.cultura;

import java.io.IOException;
import com.cultura.Requests.LoginRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        System.out.println("inside handle login button");
        if (client == null){
            System.out.println("client null in login");
        }
        try {
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            LoginRequest loginRequest = new LoginRequest(username, password);
            if (client == null){
                ClientManager clientManager = ClientManager.getInstance();
                client = clientManager.getClient();
            }
            String response = (String) client.sendRequest(loginRequest);
            if (response.equals("Login successful")){
                System.out.println("logged in!!!!!");
                client.username = username;
                System.out.println("client is in login : " );
                App.setRoot("timeline");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setContentText("Something went wrong with the login. Please try again.");
                alert.showAndWait();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
