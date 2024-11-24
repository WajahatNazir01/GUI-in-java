package com.example.demobmicalculator;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        primaryStage.setTitle("BMI Calculator");

        Scene scene = new Scene(grid, 800, 600, Color.BEIGE);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_CENTER);

        Text text = new Text("BMI CALCULATOR");
        text.setFont(Font.font("Arial", 60));
        text.setFill(Color.DARKBLUE);
        text.setStyle("-fx-padding: 20px;");
        hbox.getChildren().add(text);

        // Correct resource path for the image
        Image generalImage = new Image(getClass().getResource("/best-bmi.png").toExternalForm());  // Updated path with '/'
        ImageView imageView = new ImageView();
        imageView.setImage(generalImage);
        imageView.setFitHeight(200);
        imageView.setFitWidth(900);  // Resize image to fit well
        imageView.setPreserveRatio(true);  // Maintain aspect ratio of the image

        GridPane grid1 = new GridPane();
        Label label = new Label("Select Gender");
        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        ToggleGroup tg = new ToggleGroup();
        male.setToggleGroup(tg);
        female.setToggleGroup(tg);

        label.setFont(Font.font("Arial", 30));
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-padding: 10px; -fx-text-fill: darkblue; -fx-font-size: 30px;");

        male.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 10px;");
        female.setStyle("-fx-background-color: #FF4081; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 10px;");

        Label age = new Label("Age");
        TextField ageField = new TextField();
        Label height = new Label("Height (cm)");
        TextField heightField = new TextField();
        Label weight = new Label("Weight (kg)");
        TextField weightField = new TextField();

        grid1.add(age, 0, 0);
        grid1.add(ageField, 1, 0);
        grid1.add(height, 0, 1);
        grid1.add(heightField, 1, 1);
        grid1.add(weight, 0, 2);
        grid1.add(weightField, 1, 2);

        Button calculateBmiButton = new Button("Calculate BMI");
        Label bmiLabel = new Label();
        bmiLabel.setFont(Font.font("Arial", 20));
        bmiLabel.setStyle("-fx-text-fill: darkblue;");

        calculateBmiButton.setOnAction(event -> {
            try {
                double heightInMeters = Double.parseDouble(heightField.getText()) / 100;
                double weight1 = Double.parseDouble(weightField.getText());
                double bmi = weight1 / (heightInMeters * heightInMeters);
                bmiLabel.setText(String.format("Your BMI is: %.2f", bmi));
            } catch (NumberFormatException e) {
                bmiLabel.setText("Please enter valid values!");
            }
        });

        grid1.add(calculateBmiButton, 1, 3);
        grid1.add(bmiLabel, 1, 4);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hbox, imageView, grid1);  // Add the image before the form
        scene.setRoot(vBox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
