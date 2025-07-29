package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main JavaFX application that demonstrates various shapes:
 * Line, Ellipse, Rectangle, Arc, and Polygon.
 * 
 * This application uses a tabbed interface to showcase each shape type.
 */
public class ShapesDemo extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Create the main container
        BorderPane root = new BorderPane();
        
        // Create tab pane for different shape demonstrations
        TabPane tabPane = new TabPane();
        tabPane.setPadding(new Insets(10));
        
        // Create tabs for each shape type
        Tab lineTab = new Tab("Line", new ShowLine());
        lineTab.setClosable(false);
        
        Tab ellipseTab = new Tab("Ellipse", new ShowEllipse());
        ellipseTab.setClosable(false);
        
        Tab rectangleTab = new Tab("Rectangle", new ShowRectangle());
        rectangleTab.setClosable(false);
        
        Tab arcTab = new Tab("Arc", new ShowArc());
        arcTab.setClosable(false);
        
        Tab polygonTab = new Tab("Polygon", new ShowPolygon());
        polygonTab.setClosable(false);
        
        // Add tabs to the tab pane
        tabPane.getTabs().addAll(lineTab, ellipseTab, rectangleTab, arcTab, polygonTab);
        
        // Set the tab pane as the center of the border pane
        root.setCenter(tabPane);
        
        // Create the scene
        Scene scene = new Scene(root, 800, 600);
        
        // Configure the stage
        primaryStage.setTitle("JavaFX More Shapes Demo");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        
        // Show the stage
        primaryStage.show();
    }
    
    /**
     * Main method for launching the application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
} 