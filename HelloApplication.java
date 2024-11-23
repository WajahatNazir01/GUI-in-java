package com.example.digitalclock;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class HelloApplication extends Application {

    private Text timeText;
    private ComboBox<String> timezoneComboBox;

    @Override
    public void start(Stage stage) {
        timeText = new Text();
        timeText.setFont(Font.font("System", 80));  // Large font size
        timeText.setFill(Color.WHITE);  // White color for the time
        timeText.setStyle("-fx-font-weight: bold;");  // Make the text bold

        timezoneComboBox = new ComboBox<>();
        addTimeZones();  // Add all time zones (including Asia/Karachi for Islamabad and Asia/Islamabad label)
        timezoneComboBox.setValue("Asia/Kolkata");  // Default to an Asian time zone (India Standard Time)
        timezoneComboBox.setStyle("-fx-font-size: 16px;");

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;");  // Set background to black
        root.getChildren().addAll(timeText, timezoneComboBox);

        StackPane.setAlignment(timezoneComboBox, Pos.BOTTOM_CENTER);  // Position ComboBox at the bottom center

        Scene scene = new Scene(root, 600, 400);  // Set size of the window
        stage.setTitle("Digital Clock");
        stage.setScene(scene);
        stage.show();

        startClock();  // Start the clock thread to update the time
    }

    private void addTimeZones() {
        // Adding all major Asian Time Zones, including Asia/Karachi for Islamabad and an alias for Asia/Islamabad
        timezoneComboBox.getItems().addAll(
                "Asia/Kolkata", "Asia/Almaty", "Asia/Amman", "Asia/Baghdad", "Asia/Baku",
                "Asia/Calcutta", "Asia/Dubai", "Asia/Hong_Kong", "Asia/Jakarta",
                "Asia/Karachi", "Asia/Islamabad",  // Adding Asia/Islamabad as a label for user convenience
                "Asia/Kathmandu", "Asia/Kuala_Lumpur", "Asia/Makassar", "Asia/Manila", "Asia/Muscat",
                "Asia/Nicosia", "Asia/Novosibirsk", "Asia/Seoul", "Asia/Singapore", "Asia/Tashkent",
                "Asia/Tokyo", "Asia/Ulaanbaatar", "Asia/Yakutsk", "Asia/Yangon"
        );
    }

    private void startClock() {
        Thread clockThread = new Thread(() -> {
            while (true) {
                try {
                    String selectedTimeZone = timezoneComboBox.getValue();
                    String validTimeZone = "Asia/Karachi";  // Default to Asia/Karachi for both options

                    // If the user selects "Asia/Islamabad", we treat it as Asia/Karachi
                    if ("Asia/Islamabad".equals(selectedTimeZone)) {
                        validTimeZone = "Asia/Karachi";
                    } else {
                        validTimeZone = selectedTimeZone;
                    }

                    TimeZone timeZone = TimeZone.getTimeZone(validTimeZone);

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    sdf.setTimeZone(timeZone);
                    String time = sdf.format(new Date());

                    javafx.application.Platform.runLater(() -> timeText.setText(time));  // Update time in the UI

                    Thread.sleep(1000);  // Wait for 1 second to update the clock
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        clockThread.setDaemon(true);  // Allow the clock thread to be terminated when the application closes
        clockThread.start();
    }

    public static void main(String[] args) {
        launch(args);  // Launch the application
    }
}
