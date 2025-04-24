package com.example.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewParkingLot {

    private static final int SLOT_WIDTH = 60;
    private static final int SLOT_HEIGHT = 40;
    private static final int LARGE_SLOT_COUNT = 20;
    private static final int SMALL_SLOT_COUNT = 20;
    private static final String FILE_PATH = "vehicles.txt";

    private Set<Integer> occupiedLarge = new HashSet<>();
    private Set<Integer> occupiedSmall = new HashSet<>();

    public void show() {
        loadOccupiedSlotsFromFile();

        Stage stage = new Stage();
        stage.setTitle("View Parking Lot");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        Label largeLabel = new Label("Large Vehicle Slots");
        grid.add(largeLabel, 0, 0, 10, 1);

        for (int i = 0; i < LARGE_SLOT_COUNT; i++) {
            StackPane slot = createSlot(i, occupiedLarge.contains(i));
            grid.add(slot, i % 10, 1 + i / 10);
        }

        Label smallLabel = new Label("Small Vehicle Slots");
        grid.add(smallLabel, 0, 3, 10, 1);

        for (int i = 0; i < SMALL_SLOT_COUNT; i++) {
            StackPane slot = createSlot(i, occupiedSmall.contains(i));
            grid.add(slot, i % 10, 4 + i / 10);
        }

        Scene scene = new Scene(grid, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private StackPane createSlot(int index, boolean occupied) {
        Rectangle rect = new Rectangle(SLOT_WIDTH, SLOT_HEIGHT);
        rect.setFill(occupied ? Color.GRAY : Color.YELLOW);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(2);

        Label label = new Label("S" + index);

        StackPane pane = new StackPane();
        pane.getChildren().addAll(rect, label);
        return pane;
    }

    private void loadOccupiedSlotsFromFile() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String type = parts[1];
                    int slot = Integer.parseInt(parts[2]);
                    if (type.equals("Large")) {
                        occupiedLarge.add(slot);
                    } else if (type.equals("Small")) {
                        occupiedSmall.add(slot);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
