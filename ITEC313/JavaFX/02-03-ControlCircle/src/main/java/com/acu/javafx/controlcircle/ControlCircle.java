package com.acu.javafx.controlcircle;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * ControlCircle - JavaFX Application demonstrating circle control with event handling
 * 
 * This application displays a circle that can be controlled using buttons.
 * The circle can be enlarged or shrunk by clicking the respective buttons.
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class ControlCircle extends Application {
    
    private Circle circle;
    private Button btEnlarge;
    private Button btShrink;
    
    @Override
    public void start(Stage primaryStage) {
        // Create a circle and set its properties
        circle = new Circle();
        circle.setCenterX(100);
        circle.setCenterY(100);
        circle.setRadius(50);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.WHITE);
        
        // Create buttons
        btEnlarge = new Button("Enlarge");
        btShrink = new Button("Shrink");
        
        // Create and register the handler for the Enlarge button
        btEnlarge.setOnAction(e -> {
            circle.setRadius(circle.getRadius() + 2);
        });
        
        // Create and register the handler for the Shrink button
        btShrink.setOnAction(e -> {
            circle.setRadius(circle.getRadius() > 2 ? circle.getRadius() - 2 : circle.getRadius());
        });
        
        // Create a pane to hold the circle
        Pane pane = new Pane();
        pane.getChildren().add(circle);
        
        // Create an HBox to hold the buttons
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(btEnlarge, btShrink);
        
        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 200, 150);
        primaryStage.setTitle("Control Circle");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Create a separate window for the buttons
        Stage buttonStage = new Stage();
        buttonStage.setTitle("Control Buttons");
        buttonStage.setScene(new Scene(hBox, 200, 50));
        buttonStage.show();
    }
    
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
} 