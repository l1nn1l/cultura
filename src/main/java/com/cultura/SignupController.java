package  com.cultura;

import com.cultura.Requests.SignupRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

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

        System.out.println("setting client here ");
        this.client = client;
        System.out.println(client == null);
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
        System.out.println("inside handle sign up button");
        if (client == null){
            System.out.println("client null in signup");
        }
        try {
            String username = usernameTextField.getText();
            String name = nameTextField.getText();
            String password = passwordField.getText();
            String email = emailTextField.getText();
            SignupRequest signupRequest = new SignupRequest(username, name, password, email);
          if (client == null){
              ClientManager clientManager = ClientManager.getInstance();
              client = clientManager.getClient();
          }
            String response = (String) client.sendRequest(signupRequest);
          if (response.equals("Signup successful")){
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Signup successful");
              alert.setContentText("Sign up successful. Please login...");
              alert.showAndWait();
              App.setRoot("login");
          }
          else {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Signup Failed");
              alert.setContentText("Something went wrong with the signup. Please try again.");
              alert.showAndWait();
          }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}