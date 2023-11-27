package com.cultura;

import javafx.scene.control.Button;

import javafx.scene.control.ListCell;

import javafx.scene.layout.HBox;

import javafx.scene.control.Label;

import javafx.scene.layout.Priority;

import javafx.scene.layout.Region;

public class UserListCell extends ListCell<String> {

    private HBox hbox = new HBox();

    private Label usernameLabel = new Label(); // Add a Label to display the username

    private Button followButton = new Button("Follow");

    private Region spacer = new Region();

    private String lastItem;

    public UserListCell() {

        super();

        hbox.getChildren().addAll(usernameLabel, spacer, followButton); // Change the order to display username first

        HBox.setHgrow(spacer, Priority.ALWAYS);

        followButton.setOnAction(event -> handleFollowButtonClicked(lastItem));

        followButton.setStyle("-fx-background-color: #42a1a7; -fx-text-fill: white;");

    }

    @Override

    protected void updateItem(String item, boolean empty) {

        super.updateItem(item, empty);

        lastItem = item;

        if (item == null || empty) {

            setGraphic(null);

        } else {

            usernameLabel.setText(item);

            setGraphic(hbox);

        }

    }

    private void handleFollowButtonClicked(String username) {

        System.out.println("Follow button clicked for " + username);

        // Implement your follow action here

        followButton.setText("Followed");

        followButton.setStyle("-fx-background-color: #1b757a; -fx-text-fill: white;");

    }

}
