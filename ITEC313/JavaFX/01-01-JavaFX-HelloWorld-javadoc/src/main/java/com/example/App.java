package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Entry point of the JavaFX application.
 * <p>
 * This class bootstraps the app, loads the initial FXML view, and manages the
 * shared {@link Scene} used to switch between views.
 */
public class App extends Application {

    private static Scene scene;

    /**
     * Initializes the primary {@link Stage} and shows the initial scene.
     *
     * @param stage the primary application window provided by the runtime
     * @throws IOException if the FXML cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Replaces the current scene root with the content loaded from an FXML file.
     *
     * @param fxml name of the FXML file (without the .fxml extension)
     * @throws IOException if the FXML cannot be loaded
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads an FXML file from the application resources and returns its root node.
     *
     * @param fxml name of the FXML file (without the .fxml extension)
     * @return the root {@link Parent} node defined in the FXML
     * @throws IOException if the resource cannot be found or loaded
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Standard Java entry point which delegates to {@link Application#launch(String...)}.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }

}