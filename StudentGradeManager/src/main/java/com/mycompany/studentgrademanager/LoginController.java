package com.mycompany.studentgrademanager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    // Handle admin login verification and screen transition
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Check hardcoded admin credentials
        if (username.equals("admin") && password.equals("1234")) {
            errorLabel.setText("Login successful!");
            errorLabel.setStyle("-fx-text-fill: green;");

            try {
                // Navigate to Dashboard upon successful login
                StudentGradeManager.setRoot("Dashboard");

            } catch (IOException e) {
                errorLabel.setText("Error loading ViewStudents.fxml: " + e.getMessage());
                errorLabel.setStyle("-fx-text-fill: red;");
                e.printStackTrace();
            }
        } else {
            // Show error message for incorrect credentials
            errorLabel.setText("Invalid username or password!");
            errorLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
