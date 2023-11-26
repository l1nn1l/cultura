package com.cultura;

import com.cultura.objects.Post;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    private static TimelineController timelineController; // Declare a static instance

    @Override
    public void start(Stage stage) throws IOException {

        // Initialize the Client
        ClientManager clientManager = ClientManager.getInstance();
        clientManager.initClient();
        System.out.println(clientManager.getClient());

        // Load the FXML for SignupController
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/cultura/signup.fxml"));
        Parent signupRoot = fxmlLoader.load();
        SignupController signupController = fxmlLoader.getController();
        signupController.setClient(clientManager.getClient());

        // Load the FXML for LoginController
        FXMLLoader fxmlLoader1 = new FXMLLoader(App.class.getResource("/com/cultura/login.fxml"));
        Parent loginRoot = fxmlLoader1.load();
        LoginController loginController = fxmlLoader1.getController();
        loginController.setClient(clientManager.getClient());

        // Load the FXML for PostController
        FXMLLoader fxmlLoader2 = new FXMLLoader(App.class.getResource("/com/cultura/post.fxml"));
        Parent postRoot = fxmlLoader2.load();
        PostController postController = fxmlLoader2.getController();
        postController.setClient(clientManager.getClient());

        scene = new Scene(loadFXML("login"), 700, 500);
        stage.setScene(scene);
        stage.show(); 

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
