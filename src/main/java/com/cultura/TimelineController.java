package com.cultura;

import com.cultura.Requests.*;
import com.cultura.objects.Post;
import com.cultura.objects.Reactions;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class TimelineController {

    @FXML
    private Button postMessageButton;

    @FXML
    private Button exitWriteModeButton;

    @FXML
    private HBox WriteMode;

    @FXML
    private TextField PostTextArea, search_bar;

    @FXML
    private VBox postsContainer, postsFollowers, postsEverybody;
    @FXML
    private Pane userListPane;

    @FXML
    private ListView<String> userListView;
    private Timeline UserPostsTimeline, FollowerPostsTimeline;

    Client client;

    public void setClient(Client client) {
        this.client = client;
        System.out.println("client was set " + client);
    }

    public void RevealTextArea() {
        WriteMode.setVisible(true);
    }

    public void HideTextArea() {
        PostTextArea.setText("");
        WriteMode.setVisible(false);
    }

    @FXML
    public void handleLogoutButton() {
        try {
            stopTimelines();
            System.out.println("pressed logout!!");
            App.setRoot("login");
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Logout Failed");
            alert.setContentText("Something went wrong with the login. Please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    public synchronized void handlePostButton() {
        if (client == null) {
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
            if (response.equals("Posted successfully")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Post Added Successfully");
                alert.setContentText("Post Added Successfully!");
                alert.showAndWait();
                PostTextArea.setText("");
                WriteMode.setVisible(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Post Failed");
                alert.setContentText("Something went wrong with the request. Please try again.");
                alert.showAndWait();
            }
        } catch (IOException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Request Failed");
            alert.setContentText("Something went wrong with the request. Please try again.");
            alert.showAndWait();
        }
    }

    @FXML
    private synchronized void initialize() throws IOException {
        if (client == null) {
            ClientManager clientManager = ClientManager.getInstance();
            client = clientManager.getClient();
            System.out.println("username in time line " + clientManager.getClient().username);
        }
        System.out.println("username in time line " + client.username);

         updateUsersPosts();
         displayPosts();
/*
        // uncomment to allow for regular client-server architecture
        UserPostsTimeline = new Timeline(new KeyFrame(Duration.seconds(8), event -> updateUsersPosts()));
        UserPostsTimeline.setCycleCount(Timeline.INDEFINITE);
        UserPostsTimeline.play();

        FollowerPostsTimeline = new Timeline(new KeyFrame(Duration.seconds(8), event -> displayPosts()));
        FollowerPostsTimeline.setCycleCount(Timeline.INDEFINITE);
        FollowerPostsTimeline.play();
*/

      //  ReadInputTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> readInputFromServer()));
      //  ReadInputTimeline.setCycleCount(Timeline.INDEFINITE);
      //  ReadInputTimeline.play();

        // Set the cell factory for userListView
        userListView.setCellFactory(lv -> new UserListCell(client));

        try {
            GetAllUsersRequest getAllUsersRequest = new GetAllUsersRequest();
            System.out.println("created getallusers request");
            ArrayList<String> usernames = (ArrayList<String>) client.sendRequest(getAllUsersRequest);
            usernames.remove(client.username);
            System.out.println("received usernames were " + usernames);
            userListView.getItems().addAll(usernames);
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        // Add listener to search_bar
        search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
            filterUserList(newValue);

        });
    }

    private synchronized void filterUserList(String query) {
        if (query == null || query.isEmpty()) {
            userListView.setVisible(false);
            userListPane.setVisible(false);
            return;
        }
        userListView.setVisible(true);
        userListPane.setVisible(true);
        userListView.getItems().clear();
        try {
            ArrayList<String> usernames = (ArrayList<String>) client.sendRequest(new GetAllUsersRequest());
            usernames.remove(client.username);
            userListView.getItems().addAll(usernames); // Reset the list
            userListView.getItems().removeIf(username -> !username.toLowerCase().contains(query.toLowerCase()));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private synchronized ArrayList<Tweet> getUsersPosts() {

        GetUsersPostsRequest getUsersPostsRequest = new GetUsersPostsRequest(client.username);
        System.out.println("get user posts username : " + client.username);
        try {
            Object response = client.sendRequest(getUsersPostsRequest);
            if (response instanceof ArrayList)
                return (ArrayList<Tweet>) response;
            System.out.println("response was actually " + response);
            return null;
        } catch (ClassNotFoundException | IOException e) {
            return new ArrayList<>();
        }
    }

    private synchronized void updateUsersPosts() {
        // Clear existing children
        Platform.runLater(() -> postsContainer.getChildren().clear());

        ArrayList<Tweet> posts = getUsersPosts();
        int size = posts.size();

        System.out.println("posts returned successfully" + posts.size());

        for (int i = size - 1; i >= Math.max(size - 3, 0); i--) {
            FXMLLoader childLoader = new FXMLLoader(getClass().getResource("post.fxml"));
            VBox childNode;
            try {
                childNode = childLoader.load();
                PostController childController = childLoader.getController();
                // get the initial reactions
                GetReactionsForPostRequest getReactionsForPostRequest = new GetReactionsForPostRequest(posts.get(i).getTweetId());
                ArrayList<Reactions> reactions = ( ArrayList<Reactions>) client.sendRequest(getReactionsForPostRequest);
                System.out.println("I got the initial reactions " + reactions);
                childController.setData(posts.get(i), reactions);
                // Add the child on the JavaFX Application Thread
                Platform.runLater(() -> postsContainer.getChildren().add(childNode));
            } catch (IOException | ClassNotFoundException e) {
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

    private synchronized ArrayList<Tweet> loadPosts() {
        GetFollowersPostRequest getFollowersPostsRequest = new GetFollowersPostRequest(client.username);
        System.out.println("get user's followers posts username : " + client.username);
        try {
            Object response = client.sendRequest(getFollowersPostsRequest);
            if (response instanceof ArrayList) {
                ArrayList<Tweet> tweets = (ArrayList<Tweet>) response;
                return tweets;
            }
            else {
                System.out.println("while loading " + (String) response);
                return new ArrayList<>();
            }
        } catch (ClassNotFoundException | IOException e) {
            return new ArrayList<>();
        }
    }

    public synchronized void addBroadCastedPost(Tweet tweet) {
        Platform.runLater(() -> {
            try {

                ArrayList<String> followers = (ArrayList<String>) client.sendRequest(new GetFollowersRequest(client.username));
                if (!(tweet.getUsername().equals(client.username) || followers.contains(tweet.getUsername()))){
                    return;
                }
                boolean personal = tweet.getUsername().equals(client.username);
                ObservableList<Node> children = personal ? postsContainer.getChildren()
                        : postsFollowers.getChildren();
                if (!children.isEmpty() && children.size() > 2) {
                    Node earliestChild = children.get(2);
                    children.remove(earliestChild);
                }

                FXMLLoader childLoader = new FXMLLoader(getClass().getResource("post.fxml"));
                VBox childNode = childLoader.load();
                PostController childController = childLoader.getController();

                // get the initial reactions
                System.out.println("getting the reactions");
                GetReactionsForPostRequest getReactionsForPostRequest = new GetReactionsForPostRequest(tweet.getTweetId());
                ArrayList<Reactions> reactions = (ArrayList<Reactions>) client.sendRequest(getReactionsForPostRequest);
                System.out.println("got the reactions " + reactions);
                childController.setData(tweet, reactions);
                System.out.println("set the data after reactions");
                if (personal)
                    postsContainer.getChildren().add(0,childNode);
                else
                    postsFollowers.getChildren().add(0, childNode);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private synchronized void displayPosts() {

        Platform.runLater(() -> postsFollowers.getChildren().clear());
        ArrayList<Tweet> posts = loadPosts();
        int size = posts.size();
        for (int i = size - 1; i >= Math.max(size - 3, 0); i--) {
            FXMLLoader childLoad = new FXMLLoader(getClass().getResource("post.fxml"));
            VBox child;
            try {
                child = childLoad.load();
                PostController childController = childLoad.getController();
                // get the initial reactions
                GetReactionsForPostRequest getReactionsForPostRequest = new GetReactionsForPostRequest(posts.get(i).getTweetId());
                ArrayList<Reactions> reactions = ( ArrayList<Reactions>) client.sendRequest(getReactionsForPostRequest);
                childController.setData(posts.get(i), reactions);
                Platform.runLater(() -> postsFollowers.getChildren().add(child));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
