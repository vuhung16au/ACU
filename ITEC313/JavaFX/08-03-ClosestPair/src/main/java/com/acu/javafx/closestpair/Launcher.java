package com.acu.javafx.closestpair;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Launcher class for the ClosestPair JavaFX application.
 * This serves as the entry point for the JavaFX application.
 */
public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) {
        ClosestPairDemo demo = new ClosestPairDemo();
        demo.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
} 