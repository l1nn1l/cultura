package  com.cultura;

import com.cultura.Requests.SignupRequest;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.SQLOutput;

public class SignupController {

    private Client client;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;

    public void setClient(Client client){

        System.out.println("setting client here");
        this.client = client;
    }

    @FXML
    private void goToLogin() {
        try {
            App.setRoot("login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSignUpButton(){
        try {
            String username = usernameTextField.getText();
            String name = nameTextField.getText();
            String password = passwordField.getText();
            String email = emailTextField.getText();
            SignupRequest signupRequest = new SignupRequest(username, name, password, email);
            client.sendRequest(signupRequest);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}