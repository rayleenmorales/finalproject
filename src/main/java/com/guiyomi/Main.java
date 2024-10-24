package com.guiyomi;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GET STARTED.fxml"));
        primaryStage.setTitle("KaTalk");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        //For Logout Confirmation
        primaryStage.setOnCloseRequest(e -> {
            Parent currentRoot = primaryStage.getScene().getRoot();
            if (currentRoot.getId() != null && currentRoot.getId().equals("chatMainRoot")) {
                // Prevent the window from closing immediately
                e.consume();
                
                // Load ChatMainController from the currently active scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainChat.fxml"));
                try {
                    loader.load();  // Load the scene again to get the controller, though it's better to store it when you switch to MainChat
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                ChatMainController cmc = loader.getController(); // Get the controller from the loaded FXML
                cmc.LogOutConfirmation(primaryStage); // Show logout confirmation
            } else {
                System.out.println("Chat Main not currently loaded");
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
