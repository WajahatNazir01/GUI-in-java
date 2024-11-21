
package com.example.demo1;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage1) {
        // Create the GridPane layout
        GridPane gridPane = new GridPane();

        // Set GridPane padding and gaps
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Create labels and text fields
        Label emailLabel = new Label("Enter Email:");
        emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
        TextField emailField = new TextField();
        emailField.setStyle("-fx-font-size: 14px; -fx-padding: 10; -fx-background-color: #e0f7fa; -fx-border-color: #4caf50; -fx-border-radius: 5px;");

        Label passwordLabel = new Label("Enter Password:");
        passwordLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
        PasswordField passwordField = new PasswordField();
        passwordField.setStyle("-fx-font-size: 14px; -fx-padding: 10; -fx-background-color: #e0f7fa; -fx-border-color: #4caf50; -fx-border-radius: 5px;");

        // Show Password toggle with an eye icon
        Button showPasswordButton = new Button();
        Image eyeClosed = new Image("file:eye-closed.png");  // Eye closed image
        Image eyeOpened = new Image("file:eye-open.png");  // Eye opened image
        showPasswordButton.setGraphic(new ImageView(eyeClosed));
        showPasswordButton.setStyle("-fx-background-color: transparent;");

        // Create buttons
        Button loginButton = new Button("Login");
        Button cancelButton = new Button("Cancel");

        // Styling the buttons
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10; -fx-border-radius: 5px;");
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10; -fx-border-radius: 5px;");

        // Add components to the gridPane
        gridPane.add(emailLabel, 0, 0);
        gridPane.add(emailField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(showPasswordButton, 2, 1);

        // Place the buttons closer together
        HBox buttonBox = new HBox(10, loginButton, cancelButton);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);
        gridPane.add(buttonBox, 1, 3, 2, 1);

        // Add a success message text, initially hidden
        Text successText = new Text();
        successText.setStyle("-fx-font-size: 16px; -fx-fill: green;");
        gridPane.add(successText, 1, 4, 2, 1);

        // Button actions
        loginButton.setOnAction(event -> {
            // Simulate success upon clicking login
            successText.setText("Login Success!");
            fadeIn(successText); // Add animation for success text
        });

        cancelButton.setOnAction(event -> {
            // Close the application when cancel is clicked
            stage1.close();
        });

        // Show/Hide Password logic
        showPasswordButton.setOnAction(event -> {
            if (passwordField.isVisible()) {
                passwordField.setVisible(false);  // Hide password field
                TextField plainPassword = new TextField(passwordField.getText());
                plainPassword.setStyle("-fx-font-size: 14px; -fx-padding: 10; -fx-background-color: #e0f7fa; -fx-border-color: #4caf50; -fx-border-radius: 5px;");
                gridPane.add(plainPassword, 1, 1);  // Add a new text field for plain text password
                plainPassword.setVisible(true);
                showPasswordButton.setGraphic(new ImageView(eyeOpened));  // Change to open eye
                plainPassword.setOnAction(e -> {
                    passwordField.setText(plainPassword.getText());
                    gridPane.getChildren().remove(plainPassword);  // Remove plain password field
                    passwordField.setVisible(true);
                    showPasswordButton.setGraphic(new ImageView(eyeClosed));  // Change back to closed eye
                });
            }
        });

        // Set the scene with custom styling
        Scene scene = new Scene(gridPane, 400, 250);
        scene.getStylesheets().add("file:styles.css");  // Optionally add an external CSS file for further styling

        stage1.setScene(scene);
        stage1.setTitle("Login Example");
        stage1.setResizable(false); // Make the window non-resizable
        stage1.show();
    }

    private void fadeIn(Text successText) {
        // Add fade-in animation to the success message
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), successText);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);
        fadeIn.play();
    }

    public static void main(String[] args) {
        launch();
    }
}
