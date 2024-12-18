package com.guiyomi;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Map;

public class LoginPageController {
    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button loginBtn;

    @FXML
    private Button homeBtn;

    private FirebaseService authService = new FirebaseService();

    @FXML
    public void handleHomeButton(ActionEvent event) throws Exception {
        // If sign up button pressed, show sign up page
        Parent chatMainParent = FXMLLoader.load(getClass().getResource("GET STARTED.fxml"));
        Scene chatMainScene = new Scene(chatMainParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(chatMainScene);
        window.centerOnScreen();
        window.show();
    }

    @FXML
    public void handleLoginButton(ActionEvent event) throws Exception {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            Map<String, String> authData = authService.signIn(email, password);

            if (authData == null) {
                messageLabel.setText("Invalid email/password.");
            } else {
                String uid = authData.get("uid");
                String idToken = authData.get("idToken");

                authService.fetchUserDetails(uid, idToken);  // This will also initialize the session
                UserSession.startSession(uid, UserSession.getUserName(), UserSession.getUserPhotoUrl(), idToken);

                // If login is successful, load MainChat.fxml
                Parent chatMainParent = FXMLLoader.load(getClass().getResource("MainChat.fxml"));
                Scene chatMainScene = new Scene(chatMainParent);

                // Get the stage information
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(chatMainScene);
                window.centerOnScreen();
                window.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
