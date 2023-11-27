package com.cultura;

import com.cultura.Requests.GetFollowersPostRequest;
import com.cultura.Requests.GetUsersPostsRequest;
import com.cultura.Requests.MakePostRequest;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;

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
    private VBox postsContainer, postsFollowers;

    private Timeline UserPostsTimeline, FollowerPostsTimeline;

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

    @FXML
    public void handleLogoutButton(){
        try {
            stopTimelines();
            System.out.println("pressed logout!!");
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
        MakePostRequest postRequest = new MakePostRequest(username, postTextArea);
        System.out.println("created the post request!");
        try {
        String response = (String) client.sendRequest(postRequest);
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

     @FXML
     private void initialize() {
         if (client == null) {
             ClientManager clientManager = ClientManager.getInstance();
             client = clientManager.getClient();
             System.out.println("username in time line " + clientManager.getClient().username);
         }
         System.out.println("username in time line " + client.username);

         updateUsersPosts();
         displayPosts();

         UserPostsTimeline = new Timeline(new KeyFrame(Duration.seconds(8), event -> updateUsersPosts()));
         UserPostsTimeline.setCycleCount(Timeline.INDEFINITE);
         UserPostsTimeline.play();

         FollowerPostsTimeline = new Timeline(new KeyFrame(Duration.seconds(8), event -> displayPosts()));
         FollowerPostsTimeline.setCycleCount(Timeline.INDEFINITE);
         FollowerPostsTimeline.play();
     }

    private ArrayList<Tweet> getUsersPosts() {

         GetUsersPostsRequest getUsersPostsRequest = new GetUsersPostsRequest(client.username);
         System.out.println("get user posts username : " + client.username);
         try {
            return (ArrayList<Tweet>) client.sendRequest(getUsersPostsRequest);
         } catch (ClassNotFoundException | IOException e){
             return new ArrayList<>();
         }
     }

    private void updateUsersPosts() {
        // Clear existing children
        System.out.println("the one updating " + UserPostsTimeline);
        Platform.runLater(() -> postsContainer.getChildren().clear());

        ArrayList<Tweet> posts = getUsersPosts();
        System.out.println(posts);
        int size = posts.size();

        System.out.println("posts returned successfully" + posts.size());

        for (int i = size-1; i >= Math.max(size-3, 0); i--) {
            FXMLLoader childLoader = new FXMLLoader(getClass().getResource("post.fxml"));
            VBox childNode;
            try {
                childNode = childLoader.load();
                PostController childController = childLoader.getController();
                childController.setData(posts.get(i));

                // Add the child on the JavaFX Application Thread
                Platform.runLater(() -> postsContainer.getChildren().add(childNode));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopTimelines() {
        if (UserPostsTimeline != null) {
            UserPostsTimeline.pause();
        }
        if (FollowerPostsTimeline != null) {
            FollowerPostsTimeline.pause();
        }
    }

    private ArrayList<Tweet> loadPosts() {
        GetFollowersPostRequest GetFollowersPostsRequest = new GetFollowersPostRequest(client.username);
        System.out.println("get user's followers posts username : " + client.username);
        try {
           return (ArrayList<Tweet>) client.sendRequest(GetFollowersPostsRequest);
        } catch (ClassNotFoundException | IOException e){
            return new ArrayList<>();
        }
    }

    private void displayPosts(){

        Platform.runLater(() -> postsFollowers.getChildren().clear());
       ArrayList<Tweet> posts = loadPosts();
       System.out.println(posts);
       int size = posts.size();
       for (int i = size-1; i >= Math.max(size-3, 0); i--) {
           FXMLLoader childLoad = new FXMLLoader(getClass().getResource("post.fxml"));
           VBox child;
           try {
               child = childLoad.load();
               PostController childController = childLoad.getController();
               childController.setData(posts.get(i));
               Platform.runLater(() -> postsFollowers.getChildren().add(child));
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }

}
