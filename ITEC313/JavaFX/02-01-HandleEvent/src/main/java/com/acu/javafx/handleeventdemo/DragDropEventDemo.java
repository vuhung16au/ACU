package com.acu.javafx.handleeventdemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Demonstrates drag and drop event handling with a draggable rectangle
 */
public class DragDropEventDemo extends Application {
    
    private Rectangle rectangle;
    private Label eventLabel;
    private int eventCount = 0;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drag & Drop Event Demo");
        
        // Create main container
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Create description
        Label description = new Label("Drag the rectangle to see drag and drop events");
        description.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        
        // Create interactive rectangle
        rectangle = new Rectangle(150, 75, 100, 50);
        rectangle.setFill(Color.LIGHTPINK);
        rectangle.setStroke(Color.HOTPINK);
        rectangle.setStrokeWidth(2);
        
        // Create event counter
        eventLabel = new Label("Drag Events: 0");
        eventLabel.setStyle("-fx-font-size: 12;");
        
        // Setup drag and drop event handlers
        setupDragDropEventHandlers();
        
        root.getChildren().addAll(description, rectangle, eventLabel);
        
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void setupDragDropEventHandlers() {
        // Mouse drag detected
        rectangle.setOnDragDetected(e -> {
            eventCount++;
            logEvent("Drag Detected", String.format("Position: (%.1f, %.1f)", e.getX(), e.getY()));
            
            // Start drag and drop
            Dragboard db = rectangle.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString("Dragged Rectangle");
            db.setContent(content);
            
            e.consume();
        });
        
        // Drag done
        rectangle.setOnDragDone(e -> {
            eventCount++;
            logEvent("Drag Done", String.format("Transfer Mode: %s", e.getTransferMode()));
            
            if (e.getTransferMode() == TransferMode.MOVE) {
                rectangle.setFill(Color.LIGHTGREEN);
            }
            
            e.consume();
        });
        
        // Drag over
        rectangle.setOnDragOver(e -> {
            eventCount++;
            logEvent("Drag Over", String.format("Position: (%.1f, %.1f)", e.getX(), e.getY()));
            
            if (e.getGestureSource() != rectangle && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            
            e.consume();
        });
        
        // Drag dropped
        rectangle.setOnDragDropped(e -> {
            eventCount++;
            logEvent("Drag Dropped", String.format("Position: (%.1f, %.1f)", e.getX(), e.getY()));
            
            Dragboard db = e.getDragboard();
            boolean success = false;
            
            if (db.hasString()) {
                rectangle.setFill(Color.LIGHTBLUE);
                success = true;
            }
            
            e.setDropCompleted(success);
            e.consume();
        });
        
        // Drag enter
        rectangle.setOnDragEntered(e -> {
            eventCount++;
            logEvent("Drag Entered", String.format("Position: (%.1f, %.1f)", e.getX(), e.getY()));
            
            if (e.getGestureSource() != rectangle && e.getDragboard().hasString()) {
                rectangle.setFill(Color.LIGHTYELLOW);
            }
            
            e.consume();
        });
        
        // Drag exit
        rectangle.setOnDragExited(e -> {
            eventCount++;
            logEvent("Drag Exited", String.format("Position: (%.1f, %.1f)", e.getX(), e.getY()));
            
            rectangle.setFill(Color.LIGHTPINK);
            e.consume();
        });
    }
    
    private void logEvent(String eventType, String details) {
        System.out.printf("[%s] %s: %s%n", 
            java.time.LocalTime.now().toString(), eventType, details);
        eventLabel.setText("Drag Events: " + eventCount);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 