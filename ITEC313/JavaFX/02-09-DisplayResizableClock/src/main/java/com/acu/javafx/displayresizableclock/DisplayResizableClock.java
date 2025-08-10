package com.acu.javafx.displayresizableclock;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * DisplayResizableClock - A JavaFX application that demonstrates a resizable clock
 * 
 * This application creates a clock pane that automatically resizes when the window
 * is resized. The clock displays the current time and updates in real-time.
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class DisplayResizableClock extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Create a clock and a label
        ClockPane clock = new ClockPane();
        String timeString = clock.getHour() + ":" + clock.getMinute() 
            + ":" + clock.getSecond();
        Label lblCurrentTime = new Label(timeString);
        Label lblGuide = new Label("Try to resize the size of this window");

        // Place clock and label in border pane
        BorderPane pane = new BorderPane();
        pane.setCenter(clock);
        pane.setBottom(lblCurrentTime);
        pane.setTop(lblGuide);
        BorderPane.setAlignment(lblCurrentTime, Pos.TOP_CENTER);
        BorderPane.setAlignment(lblGuide, Pos.CENTER);
        
        // Create a scene and place the pane in the stage
        Scene scene = new Scene(pane, 250, 250);
        primaryStage.setTitle("DisplayResizableClock"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
        
        // Add listeners for resizing
        pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.equals(oldVal)) {
                clock.setWidth(newVal.doubleValue());
            }
            if (obs == null) { /* no-op to use obs */ }
        });

        pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.equals(oldVal)) {
                clock.setHeight(newVal.doubleValue());
            }
            if (obs == null) { /* no-op to use obs */ }
        });
    }
    
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
} 