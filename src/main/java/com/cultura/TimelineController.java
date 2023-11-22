package com.cultura;

import javafx.event.ActionEvent;

// import java.io.IOException;

import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

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
