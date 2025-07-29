package com.acu.javafx.generics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main launcher class for the JavaFX Generics Demo application.
 * This class serves as the entry point for the JavaFX application.
 */
public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) {
        GenericsDemoApp app = new GenericsDemoApp();
        app.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
} 