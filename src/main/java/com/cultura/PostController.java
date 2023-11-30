package com.cultura;

import com.cultura.Requests.UpdateReactionsRequest;
import com.cultura.objects.Reactions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.cultura.Requests.GetComments;
import com.cultura.Requests.MakeCommentRequest;
import com.cultura.objects.Account;
import com.cultura.objects.Comment;
import com.cultura.objects.Post;

public class PostController implements Initializable {

    @FXML
    private Label username;

    @FXML
    private Label date;

    @FXML
    private TextFlow caption;

    @FXML
    private Label nbComments;

    @FXML
    private HBox reactionsContainer, likeContainer;

    @FXML
    private HBox WriteMode;

    @FXML
    private ImageView imgLike, imgLove, imgSmile, imgParty, imgWow;

    private ImageView currentReactionImage = null; //user's selected reaction

    private int userReactionCount = 1; //how many reactions a user is allowed to give

    private Post post;

    private Tweet tweet;
    private int selectedTweetId;
    private List<Tweet> tweets = new ArrayList<>();  // Store the Tweet objects


    Client client;
    public void setClient(Client client){
        this.client = client;
        System.out.println("client was set " + client);
    }

    @FXML
    private TextArea existingCommentsArea;

    public void HideCommentArea(ActionEvent event) {
        existingCommentsArea.setText("");
        WriteMode.setVisible(false);
    }


    @FXML
private synchronized void onCommentClicked(MouseEvent event) {
    // custom dialog

    if (client == null) {
        ClientManager clientManager = ClientManager.getInstance();
        client = clientManager.getClient();
    }
    System.out.println("inside the comment dialog");
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Comments");

    // Initialize existingCommentsArea if it is null
    if (existingCommentsArea == null) {
        existingCommentsArea = new TextArea();
    }

    existingCommentsArea.setEditable(false);
    existingCommentsArea.setWrapText(true);
    existingCommentsArea.clear();
    updateCommentsUI(); // Load comments every time the dialog is opened

    TextField newCommentField = new TextField();
    newCommentField.setPromptText("Add a new comment...");
    GridPane grid = new GridPane();
    grid.add(existingCommentsArea, 0, 0);
    grid.add(newCommentField, 0, 1);
    dialog.getDialogPane().setContent(grid);
    GridPane.setMargin(newCommentField, new Insets(12, 0, 0, 0));
    ButtonType addCommentButton = new ButtonType("Add Comment", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(addCommentButton, ButtonType.CANCEL);

    Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == addCommentButton) {
        String newCommentText = newCommentField.getText();
        MakeCommentRequest commentRequest = new MakeCommentRequest(client.username, newCommentText, tweet.getTweetId());
        System.out.println("created the comment request!");
        try {
            String response = (String) client.sendRequest(commentRequest);
            if (response.equals("Comment posted successfully")) {
                // Update comments UI only after successful posting
                updateCommentsUI();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Comment Added Successfully");
                alert.setContentText("Comment Added Successfully!");
                alert.showAndWait();
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
}


    private synchronized ArrayList<TweetComments> loadComments() {
        int tweet_id = tweet.getTweetId();
        GetComments GetComments = new GetComments(tweet_id);
        System.out.println("get comments of tweet : " + tweet_id);
        try {
           return (ArrayList<TweetComments>) client.sendRequest(GetComments);
        } catch (ClassNotFoundException | IOException e){
            return new ArrayList<>();
        }
    }


    private void updateCommentsUI() {
        // Load and append comments to existingCommentsArea
        ArrayList<TweetComments> commentsList = loadComments();
        for (TweetComments comment : commentsList) {
            System.out.println("<<<<<<<<<<<<<<<<<<" + comment.getUsername() + ": " + comment.getCommentText() + "\n");
            existingCommentsArea.appendText(comment.getUsername() + ": " + comment.getCommentText() + "\n");
        }
    }

//     @FXML
//     private void onCommentClicked(MouseEvent event) {
//         // custom dialog
//         Dialog<ButtonType> dialog = new Dialog<>();
//         dialog.setTitle("Comments");

//         existingCommentsArea = new TextArea();
//         existingCommentsArea.setEditable(false);
//         existingCommentsArea.setWrapText(true);

//         // Populate the existing comments
//         for (Comment comment : post.getComments()) {
//             existingCommentsArea.appendText(comment.getCommenterUsername() + ": " + comment.getCommentText() + "\n");
//         }

//         TextField newCommentField = new TextField();
//         newCommentField.setPromptText("Add a new comment...");

//         // Set up the layout of the dialog
//         GridPane grid = new GridPane();
//         grid.add(existingCommentsArea, 0, 0);
//         grid.add(newCommentField, 0, 1);
//         dialog.getDialogPane().setContent(grid);
//         GridPane.setMargin(newCommentField, new Insets(12, 0, 0, 0));

//         ButtonType addCommentButton = new ButtonType("Add Comment", ButtonBar.ButtonData.OK_DONE);
//         dialog.getDialogPane().getButtonTypes().addAll(addCommentButton, ButtonType.CANCEL);

//    //     dialog.getDialogPane().getStylesheets().add(getClass().getResource("objects/dialogStyles.css").toExternalForm());

//         Optional<ButtonType> result = dialog.showAndWait();

//         if (result.isPresent() && result.get() == addCommentButton) {
//             // User clicked "Add Comment" button
//             String newCommentText = newCommentField.getText();

//             if (!newCommentText.isEmpty()) {
//                 Comment comment = new Comment("User", newCommentText);
//                 post.addComment(comment);
//                 updateCommentsUI(comment);
//             }
//         }
//     }



    private void updateCommentsUI(Comment comment) {
        // Append the new comment to the existing comments
        existingCommentsArea.appendText(comment.getCommenterUsername() + ": " + comment.getCommentText() + "\n");
    }


    @FXML
    private void onReactionImgClicked(MouseEvent event) {
        ImageView clickedImageView = (ImageView) event.getSource();
        System.out.println( "clicked image was " + clickedImageView.getImage().getUrl() + " " + currentReactionImage);
        if (currentReactionImage != null) {
            // decrement user's previously selected reaction
            decrementReactionCount(currentReactionImage);
        }
        System.out.println(" the current count is " + userReactionCount + " " + clickedImageView.getImage().getUrl());
        currentReactionImage = clickedImageView;
        addClickedReaction(clickedImageView.getImage());

    }

    private void decrementReactionCount(ImageView imageView) {
        System.out.println("decrementing " + imageView.getImage().getUrl());
        HBox existingBox = findReactionBox(imageView.getImage());
        if (existingBox != null) {
            // Decrement the count in the existing label
            Label countLabel = (Label) existingBox.getChildren().get(1);
            int currentCount = Integer.parseInt(countLabel.getText());
            if (currentCount > 0) {
                int newCount = currentCount - 1;
                countLabel.setText(String.valueOf(newCount));
                // remove the reaction of the user
                if (newCount == 0){
                    likeContainer.getChildren().remove(existingBox);
                }
            }

        }
        System.out.println("done decrementing");
        userReactionCount=1;
    }

    private HBox findReactionBox(Image reactionImage) {
        for (Node node : likeContainer.getChildren()) {
            if (node instanceof HBox) {
                HBox existingBox = (HBox) node;
                ImageView existingImageView = (ImageView) existingBox.getChildren().get(0);

                if (existingImageView.getImage().equals(reactionImage)) {
                    // Reaction is found
                    return existingBox;
                }
            }
        }
        return null; // Reaction not found
    }


    private void addClickedReaction(Image reactionImage) {
        ImageView clickedImageView = new ImageView(reactionImage);
        clickedImageView.setFitHeight(30.0);
        clickedImageView.setFitWidth(30.0);
        Reactions reactions = new Reactions(tweet.getTweetId(), client.username, reactionImage.getUrl());
        try {
            client.sendRequest(new UpdateReactionsRequest(reactions));
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        // Check if the reaction is already present
        for (Node node : likeContainer.getChildren()) {
            if (node instanceof HBox) {
                HBox existingBox = (HBox) node;
                ImageView existingImageView = (ImageView) existingBox.getChildren().get(0);
                if (existingImageView.getImage().getUrl().equals(reactionImage.getUrl()) && userReactionCount == 1) {
                    // Reaction is already present, update the label count
                    Label countLabel = (Label) existingBox.getChildren().get(1);
                    int currentCount = Integer.parseInt(countLabel.getText());
                    countLabel.setText(String.valueOf(currentCount + 1));
                    userReactionCount--;
                    return;
                }
            }
        }

        if(userReactionCount == 1){
            // If the reaction is not present, create a new label for the count
            Label countLabel = new Label("1");
            countLabel.setPrefHeight(23);
            countLabel.setPrefWidth(23);
            countLabel.setTextFill(Color.web("#606266"));
            countLabel.setFont(new Font("Segoe UI Historic", 18.0));

            HBox reactionBox = new HBox(clickedImageView, countLabel);
            reactionBox.setAlignment(Pos.CENTER);
            reactionBox.setSpacing(4.0);

            System.out.println("inside 2 if statement");
            // Add the new reaction to likeContainer
            likeContainer.getChildren().add(reactionBox);
            userReactionCount = 0;

        }

    }


    public void setData(Post post){
        this.post = post;
        username.setText(post.getAccount().getName());

        date.setText(post.getDate());

        String captionText = post.getCaption();
        if (captionText != null && !captionText.isEmpty()) {

            Text captionTextNode = new Text(captionText);
            Font font = new Font(15.0);
            captionTextNode.setFont(font);
            caption.getChildren().setAll(captionTextNode);
            caption.setVisible(true);
            caption.setManaged(true);
        } else {
            caption.getChildren().clear();
            caption.setVisible(false);
            caption.setManaged(false);
        }

            nbComments.setText(post.getNbComments() + " comments");

            setInitialReactions(new ArrayList<Reactions>());
    }

    public void setData(Tweet tweet, ArrayList<Reactions> reactions){

        this.tweet = tweet;
        username.setText(tweet.getUsername());

        if (tweet.getTimestamp() != null)
            date.setText(tweet.getTimestamp().toString());

        String captionText = tweet.getText();
        if (captionText != null && !captionText.isEmpty()) {
            Text captionTextNode = new Text(captionText);
            caption.getChildren().setAll(captionTextNode);
            caption.setVisible(true);
            caption.setManaged(true);
        } else {
            caption.getChildren().clear();
            caption.setVisible(false);
            caption.setManaged(false);
        }

            // nbReactions.setText(String.valueOf(post.getTotalReactions()));
            nbComments.setText(post.getNbComments() + " comments");
            setInitialReactions(reactions);
            String currentReaction = "none";
    }

    public Post getPost(){
        Post post = new Post();
        Account account = new Account();
        account.setName("Jane Doe");
        post.setAccount(account);
        post.setDate("Feb 13, 2023 at 12:00 PM");
        post.setCaption("Lorem ipsum dolor sit amet,consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.");
        post.setNbLoveReactions(1);
        post.setNbLikeReactions(2);
        post.setNbPartyReactions(3);
        post.setNbComments(2);

        //post.addComment(new Comment("User1", "Great post!"));
        //post.addComment(new Comment("User2", "I agree!"));
        //post.addComment(new Comment("User3", "Nice content!"));

        return post;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       System.out.println("inside the init post function!!");
       if (client == null) {
            ClientManager clientManager = ClientManager.getInstance();
            client = clientManager.getClient();
        }
       setData(getPost());
    }

    private void setInitialReactions(ArrayList<Reactions> reactions) {
        int like = 0, love = 0, smile = 0, party = 0, wow = 0;
        String currentReaction = "none";
        for (Reactions reaction : reactions){
            switch (reaction.getReactionType()) {
                case "like":
                    like++;
                    break;
                case "love":
                    love++;
                    break;
                case "smile":
                    smile++;
                    break;
                case "party":
                    party++;
                    break;
                case "wow":
                    wow++;
                    break;
            }
            if (reaction.getUsername().equals(client.username)){
                currentReaction = reaction.getReactionType();
                String url = reaction.getUrl();
                if (url.contains("thumbs-up") || url.contains("like")){
                    currentReactionImage = imgLike;
                }else if (url.contains("pink-heart") || url.contains("love")){
                    currentReactionImage = imgLove;
                }else if (url.contains("smiling-face") || url.contains("smile")){
                    currentReactionImage = imgSmile;
                }else if (url.contains("partying-face") || url.contains("party")){
                    currentReactionImage = imgParty;
                }else if (url.contains("hushed-face") || url.contains("wow")){
                    currentReactionImage = imgWow;
                }
                System.out.println(currentReactionImage + " " + reaction.getUrl() + " " + imgWow.getImage().getUrl());
                System.out.println("current reaction is " + currentReaction + "!");
            }
        }
        // Check each reaction count in the Post object and add it to likeContainer
        addInitialReaction(imgLike.getImage(), like);
        addInitialReaction(imgLove.getImage(),love);
        addInitialReaction(imgSmile.getImage(), smile);
        addInitialReaction(imgParty.getImage(), party);
        addInitialReaction(imgWow.getImage(), wow);
    }

    private void addInitialReaction(Image reactionImage, int count) {

        if(count > 0) {

            Label countLabel = new Label(String.valueOf(count));
            countLabel.setPrefHeight(23);
            countLabel.setPrefWidth(23);
            countLabel.setTextFill(Color.web("#606266"));
            countLabel.setFont(new Font("Segoe UI Historic", 18.0));

            ImageView reactionImageView = new ImageView(reactionImage);
            reactionImageView.setFitHeight(30.0);
            reactionImageView.setFitWidth(30.0);

            HBox reactionBox = new HBox(reactionImageView, countLabel);
            reactionBox.setAlignment(Pos.CENTER);
            reactionBox.setSpacing(4.0);

            likeContainer.getChildren().add(reactionBox);
        }

        return;

    }

}
