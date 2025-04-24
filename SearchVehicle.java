package com.example.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SearchVehicle {

    private static final String FILE_PATH = "vehicles.txt";

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Search Vehicle");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        Label label = new Label("Enter Number Plate:");
        TextField numberField = new TextField();
        Button searchButton = new Button("Search Vehicle");

        grid.add(label, 0, 0);
        grid.add(numberField, 1, 0);
        grid.add(searchButton, 1, 1);

        searchButton.setOnAction(e -> {
            String numberPlate = numberField.getText().trim();
            if (numberPlate.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a number plate.");
                return;
            }
            handleSearch(numberPlate, stage);
        });

        Scene scene = new Scene(grid, 400, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void handleSearch(String numberPlate, Stage stage) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get(FILE_PATH));
            boolean found = false;
            String slot = "";

            for (String line : allLines) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].equals(numberPlate)) {
                    found = true;
                    slot = parts[2]; // Slot number is the third part
                    break;
                }
            }

            if (found) {
                showAlert(Alert.AlertType.INFORMATION, "Vehicle Found",
                        "Vehicle: " + numberPlate + "\nSlot Number: " + slot);
            } else {
                showAlert(Alert.AlertType.ERROR, "Not Found", "Vehicle not found.");
            }

            stage.close();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Something went wrong.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
