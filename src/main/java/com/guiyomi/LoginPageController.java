package com.guiyomi;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginPageController {
    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label messageLabel;

    private FirebaseService authService = new FirebaseService();

    @FXML
    public void handleSignUpButton(ActionEvent event) throws Exception {
        // If sign up button pressed, show sign up page
        Parent chatMainParent = FXMLLoader.load(getClass().getResource("SignUpPage.fxml"));
        Scene chatMainScene = new Scene(chatMainParent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(chatMainScene);
        window.show();
    }

    @FXML
    public void handleLoginButton(ActionEvent event) throws Exception {
        String email = emailField.getText();
        String password = passwordField.getText();

        try {
            int responseCode = authService.signIn(email, password);

            System.out.println(responseCode);

            if (responseCode != 200) {
                messageLabel.setText("Invalid email/password.");
            } else {
                // If login is successful, load ChatMain.fxml
                Parent chatMainParent = FXMLLoader.load(getClass().getResource("ChatMain.fxml"));
                Scene chatMainScene = new Scene(chatMainParent);

                // Get the stage information
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(chatMainScene);
                window.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}