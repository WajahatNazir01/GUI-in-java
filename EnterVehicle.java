
package com.example.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnterVehicle {

    private static final String FILE_PATH = "vehicles.txt";
    private static final int MAX_SMALL_SLOTS = 20;
    private static final int MAX_LARGE_SLOTS = 20;

    public void show() {
        Stage stage = new Stage();
        stage.setTitle("Enter Vehicle");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label numberLabel = new Label("Number Plate:");
        TextField numberField = new TextField();
        grid.add(numberLabel, 0, 0);
        grid.add(numberField, 1, 0);

        Label typeLabel = new Label("Vehicle Type:");
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Small", "Large");
        typeBox.setPromptText("Select Type");
        grid.add(typeLabel, 0, 1);
        grid.add(typeBox, 1, 1);

        Label dateTimeLabel = new Label("Entry Time:");
        TextField dateTimeField = new TextField();
        dateTimeField.setEditable(false);
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        dateTimeField.setText(currentDateTime);
        grid.add(dateTimeLabel, 0, 2);
        grid.add(dateTimeField, 1, 2);

        Button submitButton = new Button("Add Vehicle");
        grid.add(submitButton, 1, 3);

        submitButton.setOnAction(e -> {
            String number = numberField.getText().trim();
            String type = typeBox.getValue();
            String dateTime = dateTimeField.getText();

            if (number.isEmpty() || type == null) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields.");
                return;
            }

            createFileIfNotExists();
            int assignedSlot = findNextAvailableSlot(type);

            if (assignedSlot == -1) {
                showAlert(Alert.AlertType.ERROR, "Parking Full", "No available slots for " + type + " vehicles.");
                return;
            }

            if (addVehicleToFile(number, type, assignedSlot, dateTime)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Vehicle added successfully!");
                stage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save vehicle information.");
            }
        });

        Scene scene = new Scene(grid, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private void createFileIfNotExists() {
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int findNextAvailableSlot(String type) {
        Set<Integer> occupied = new HashSet<>();
        try {
            List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(FILE_PATH));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[1].equals(type)) {
                    occupied.add(Integer.parseInt(parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int limit = type.equals("Small") ? MAX_SMALL_SLOTS : MAX_LARGE_SLOTS;
        for (int i = 0; i < limit; i++) {
            if (!occupied.contains(i)) {
                return i;
            }
        }
        return -1; // No slots available
    }

    private boolean addVehicleToFile(String number, String type, int slot, String dateTime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(number + "," + type + "," + slot + "," + dateTime);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}



//
//
//package com.example.project;
//
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.ComboBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.GridPane;
//import javafx.stage.Stage;
//import org.opencv.core.Core; // Import OpenCV Core class
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class EnterVehicle {
//
//    private static final String FILE_PATH = "vehicles.txt";
//
//    // Ensure OpenCV library is loaded on class startup
//    static {
//        try {
//            // Set OpenCV native library path
//            System.setProperty("java.library.path", "E:\\Downloads\\opencv\\build\\java\\x64"); // Modify with actual path
//            System.loadLibrary(Core.NATIVE_LIBRARY_NAME); // Load the OpenCV library
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Error loading OpenCV library");
//        }
//    }
//
//    public void show() {
//        Stage stage = new Stage();
//        stage.setTitle("Enter Vehicle");
//
//        // Layout setup
//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));
//
//        // Number Plate
//        Label numberLabel = new Label("Number Plate:");
//        TextField numberField = new TextField();
//        grid.add(numberLabel, 0, 0);
//        grid.add(numberField, 1, 0);
//
//        // Vehicle Type
//        Label typeLabel = new Label("Vehicle Type:");
//        ComboBox<String> typeBox = new ComboBox<>();
//        typeBox.getItems().addAll("Small", "Large");
//        typeBox.setPromptText("Select Type");
//        grid.add(typeLabel, 0, 1);
//        grid.add(typeBox, 1, 1);
//
//        // Current Date and Time
//        Label dateTimeLabel = new Label("Entry Time:");
//        TextField dateTimeField = new TextField();
//        dateTimeField.setEditable(false);
//        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//        dateTimeField.setText(currentDateTime);
//        grid.add(dateTimeLabel, 0, 2);
//        grid.add(dateTimeField, 1, 2);
//
//        // Submit Button
//        Button submitButton = new Button("Add Vehicle");
//        grid.add(submitButton, 1, 3);
//
//        submitButton.setOnAction(e -> {
//            String number = numberField.getText().trim();
//            String type = typeBox.getValue();
//            String dateTime = dateTimeField.getText();
//
//            if (number.isEmpty() || type == null) {
//                showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields.");
//                return;
//            }
//
//            createFileIfNotExists();
//            if (addVehicleToFile(number, type, dateTime)) {
//                showAlert(Alert.AlertType.INFORMATION, "Success", "Vehicle added successfully!");
//                stage.close();
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save vehicle information.");
//            }
//        });
//
//        Scene scene = new Scene(grid, 400, 300);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    private void createFileIfNotExists() {
//        File file = new File(FILE_PATH);
//        try {
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private boolean addVehicleToFile(String number, String type, String dateTime) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
//            // Add vehicle info with a placeholder slot for now
//            writer.write(number + "," + type + "," + "0," + dateTime); // The third part (slot) will be assigned later
//            writer.newLine();
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    private void showAlert(Alert.AlertType alertType, String title, String message) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setContentText(message);
//        alert.setHeaderText(null);
//        alert.showAndWait();
//    }
//}
