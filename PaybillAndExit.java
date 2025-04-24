package com.example.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PaybillAndExit {

    private static final String FILE_PATH = "vehicles.txt";

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Exit and Bill");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        Label label = new Label("Enter Number Plate:");
        TextField numberField = new TextField();
        Button exitButton = new Button("Calculate Bill and Exit");

        grid.add(label, 0, 0);
        grid.add(numberField, 1, 0);
        grid.add(exitButton, 1, 1);

        exitButton.setOnAction(e -> {
            String numberPlate = numberField.getText().trim();
            if (numberPlate.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a number plate.");
                return;
            }
            handleExit(numberPlate, stage);
        });

        Scene scene = new Scene(grid, 400, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void handleExit(String numberPlate, Stage stage) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get(FILE_PATH));
            List<String> updatedLines = new ArrayList<>();
            boolean found = false;
            String vehicleType = "";
            String entryTimeStr = "";
            int slot = -1;

            for (String line : allLines) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].equals(numberPlate)) {
                    found = true;
                    vehicleType = parts[1];
                    slot = Integer.parseInt(parts[2]);
                    entryTimeStr = parts[3];
                } else {
                    updatedLines.add(line); // keep other lines
                }
            }

            if (!found) {
                showAlert(Alert.AlertType.ERROR, "Not Found", "Vehicle not found.");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime entryTime = LocalDateTime.parse(entryTimeStr, formatter);
            LocalDateTime now = LocalDateTime.now();

            long minutes = Duration.between(entryTime, now).toMinutes();
            int rate = vehicleType.equals("Large") ? 2 : 1;
            long bill = minutes * rate;

            Files.write(Paths.get(FILE_PATH), updatedLines);

            showAlert(Alert.AlertType.INFORMATION, "Exit Complete",
                    "Vehicle: " + numberPlate +
                            "\nType: " + vehicleType +
                            "\nSlot: " + slot +
                            "\nTime Spent: " + minutes + " minutes" +
                            "\nTotal Bill: Rs. " + bill);

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
