package com.cultura;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

import com.cultura.objects.Account;
import com.cultura.objects.Post;
import com.cultura.objects.Reactions;

public class PostController implements Initializable {

    @FXML
    private Label username;

    @FXML
    private Label date;

    @FXML
    private TextFlow caption;

    @FXML
    private Label nbReactions;

    @FXML
    private Label nbComments;

    @FXML
    private HBox reactionsContainer;

    @FXML
    private ImageView imgLike;

    @FXML
    private ImageView imgLove;

    @FXML
    private ImageView imgSmile;

    @FXML
    private ImageView imgParty;

    @FXML
    private ImageView imgWow;

    @FXML
    private ImageView imgReaction;

    private Reactions currentReaction;
    private Post post;

    Client client;
    public void setClient(Client client){
        this.client = client;
        System.out.println("client was set " + client);
    }

    @FXML
    public void onReactionImgPressed(MouseEvent me){
        switch (((ImageView) me.getSource()).getId()){
            case "imgLove":
                setReaction(Reactions.LOVE);
                break;
            case "imgSmile":
                setReaction(Reactions.SMILE);
                break;
            case "imgParty":
                setReaction(Reactions.PARTY);
                break;
            case "imgWow":
                setReaction(Reactions.WOW);
                break;
            default:
                setReaction(Reactions.LIKE);
                break;
        }
    }

    public void setReaction(Reactions reaction){
        Image image = new Image(getClass().getResourceAsStream(reaction.getImgSrc()));
        imgReaction.setImage(image);

        if(currentReaction == Reactions.NON){
            post.setTotalReactions(post.getTotalReactions() + 1);
        }

        currentReaction = reaction;

        if(currentReaction == Reactions.NON){
            post.setTotalReactions(post.getTotalReactions() - 1);
        }

        nbReactions.setText(String.valueOf(post.getTotalReactions()));
    }

    public void setData(Post post){
        this.post = post;
        username.setText(post.getAccount().getName());

        date.setText(post.getDate());

        String captionText = post.getCaption();
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

            nbReactions.setText(String.valueOf(post.getTotalReactions()));
            nbComments.setText(post.getNbComments() + " comments");

            currentReaction = Reactions.NON;
    }

    private Post getPost(){
        Post post = new Post();
        Account account = new Account();
        account.setName("Jane Doe");
        post.setAccount(account);
        post.setDate("Feb 13, 2023 at 12:00 PM");
        post.setCaption("Lorem ipsum dolor sit amet,consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.");
        post.setTotalReactions(9);
        post.setNbComments(2);

        return post;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("inside the init function!!");
        setData(getPost());
    }
}
