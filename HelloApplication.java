package com.example.project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        // Create Buttons
        Button btnEnterVehicle = new Button("Enter Vehicle");
        Button btnExitBill = new Button("Pay Bill and Exit");
        btnExitBill.setOnAction(e->{
            PaybillAndExit pb = new PaybillAndExit();
            pb.show();
        });
        Button btnSearch = new Button("Search Vehicle");
        btnSearch.setOnAction(e->{
            SearchVehicle sv = new SearchVehicle();
            sv.show();
        });
        Button btnShowLot = new Button("Show Parking Lot");

        btnEnterVehicle.setOnAction(e -> {
            EnterVehicle enterScreen = new EnterVehicle();
            enterScreen.show();
        });
        btnShowLot.setOnAction(e->{
            ViewParkingLot vp1= new ViewParkingLot();
            vp1.show();
        });
        // Styling buttons
        Button[] buttons = {btnEnterVehicle, btnExitBill, btnSearch, btnShowLot};
        for (Button btn : buttons) {
            btn.setMinWidth(250);
            btn.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");
        }

        // Layout
        VBox vbox = new VBox(20); // 20px spacing
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(buttons);

        Scene scene = new Scene(vbox);
        stage.setTitle("Smart Parking Lot Manager");
        stage.setScene(scene);
        stage.setMaximized(true); // Fullscreen without covering taskbar
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
