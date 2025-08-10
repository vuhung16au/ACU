package com.acu.javafx.clockanimation;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.util.Duration;

// Import the ClockPane from its package

/**
 * ClockAnimation - shows an analog clock (ClockPane) updating every second.
 * Kept intentionally simple per requirements: no buttons, no extra UI.
 */
public class ClockAnimation extends Application {
    @Override
    public void start(Stage primaryStage) {
        ClockPane clock = new ClockPane(); // Create a clock

        // Handler updates the clock time each second
        EventHandler<ActionEvent> eventHandler = e -> clock.setCurrentTime();

        // Timeline fires event every 1000 ms
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        // Scene directly hosts the clock pane
        Scene scene = new Scene(clock, 250, 250);
        primaryStage.setTitle("ClockAnimation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}