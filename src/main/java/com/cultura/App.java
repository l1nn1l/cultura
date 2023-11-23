package com.cultura;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("timeline"), 700, 500);
        stage.setScene(scene);
        stage.show();

        // Initialize the Client
        ClientManager clientManager = new ClientManager();
        clientManager.initClient();

        // Load the FXML for SignupController
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("signup.fxml"));
        Parent signupRoot = fxmlLoader.load();
        SignupController signupController = fxmlLoader.getController();
        signupController.setClient(clientManager.getClient());

        // Load the FXML for LoginController
        fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Parent loginRoot = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        loginController.setClient(clientManager.getClient());
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
