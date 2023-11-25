package com.cultura;

import com.cultura.Requests.PostRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import java.io.IOException;

public class TimelineController {

    @FXML
    private Button postMessageButton;

    @FXML
    private Button exitWriteModeButton;

    @FXML
    private HBox WriteMode;

    @FXML
    private TextField PostTextArea;

    @FXML
    private Button PostMessageButton;

    Client client;
    public void setClient(Client client){
        this.client = client;
        System.out.println("client was set " + client);
    }

    public void RevealTextArea(ActionEvent event) {
        WriteMode.setVisible(true);
    }

    public void HideTextArea(ActionEvent event) {
        PostTextArea.setText("");
        WriteMode.setVisible(false);
    }

    public void SendMessageToServer(ActionEvent event) {
        //TBD
    }

    @FXML
    public void handleLogoutButton(){
        try {
        App.setRoot("login");
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Logout Failed");
            alert.setContentText("Something went wrong with the login. Please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handlePostButton(){
        if (client == null){
            ClientManager clientManager = ClientManager.getInstance();
            client = clientManager.getClient();
        }
        System.out.println("inside the post button");
        String postTextArea = PostTextArea.getText();
        String username = client.username;
        PostRequest postRequest = new PostRequest(username, postTextArea);
        System.out.println("created the post request!");
        try {
        String response = client.sendRequest(postRequest);
        if (response.equals("Posted successfully")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Post Added Successfully");
            alert.setContentText("Post Added Successfully!");
            alert.showAndWait();
            PostTextArea.setText("");
            WriteMode.setVisible(false);
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Post Failed");
            alert.setContentText("Something went wrong with the request. Please try again.");
            alert.showAndWait();
        }
        } catch (IOException | ClassNotFoundException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Request Failed");
            alert.setContentText("Something went wrong with the request. Please try again.");
            alert.showAndWait();
        }
    }
    // @FXML
    // private VBox childContainer;

    // @FXML
    // private void initialize() {
    //     try {
    //         FXMLLoader childLoader = new FXMLLoader(getClass().getResource("post.fxml"));
    //         VBox childNode = childLoader.load();
    //         PostController childController = childLoader.getController();
    //         // Do something with the child node and controller
    //         childContainer.getChildren().add(childNode);
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
    
}
