package com.cultura;

import com.cultura.Requests.FollowRequest;
import com.cultura.Requests.GetFollowersRequest;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class UserListCell extends ListCell<String> {

    private HBox hbox = new HBox();
    private Label usernameLabel = new Label();
    private Button followButton = new Button();
    private Region spacer = new Region();
    private String lastItem;
    private Client client;

    public UserListCell(Client client) {
        super();
        this.client = client;
        hbox.getChildren().addAll(usernameLabel, spacer, followButton);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        followButton.setStyle("-fx-background-color: #42a1a7; -fx-text-fill: white;");
        followButton.setOnAction(event -> handleFollowButtonClicked(lastItem));
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        lastItem = item;

        if (item == null || empty) {
            setGraphic(null);
        } else {
            usernameLabel.setText(item);

            // Check if the user is already followed
            boolean isFollowed = checkIfFollowed(item);

            if (isFollowed) {
                followButton.setText("Followed");
                followButton.setStyle("-fx-background-color: #1b757a; -fx-text-fill: white;");
                followButton.setDisable(true); // Make the button not clickable
            } else {
                followButton.setText("Follow");
                followButton.setStyle("-fx-background-color: #42a1a7; -fx-text-fill: white;");
                followButton.setDisable(false); // Make the button clickable
            }

            setGraphic(hbox);
        }
    }

    private void handleFollowButtonClicked(String username) {
        System.out.println("Follow button clicked for " + username);

        try {
            Follow follow = new Follow(client.username, username);
            FollowRequest followRequest = new FollowRequest(follow);
            String response = (String) client.sendRequest(followRequest);

            if (response.equals("Follow added successfully")) {
                followingSet.add(username);
                followButton.setText("Followed");
                followButton.setStyle("-fx-background-color: #1b757a; -fx-text-fill: white;");
                followButton.setDisable(true); // Make the button not clickable
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Follow Added Successfully");
                alert.setContentText("Follow Added Successfully!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Follow Failed");
                alert.setContentText("Something went wrong with the request. Please try again.");
                alert.showAndWait();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    HashSet<String> followingSet;
    private boolean checkIfFollowed(String username) {
        try {
            GetFollowersRequest getFollowersRequest = new GetFollowersRequest(client.username);
            followingSet = new HashSet<>((ArrayList<String>) client.sendRequest(getFollowersRequest));
        } catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
        }
        return followingSet.contains(username);
    }
}
