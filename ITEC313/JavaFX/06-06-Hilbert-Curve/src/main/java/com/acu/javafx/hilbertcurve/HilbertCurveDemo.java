package com.acu.javafx.hilbertcurve;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * HilbertCurveDemo - Main JavaFX application for displaying Hilbert curves
 * 
 * This application provides a user interface to:
 * - Enter the order of the Hilbert curve (1-6)
 * - Draw the Hilbert curve with a button click
 * - Display the curve in a resizable pane
 * - Show information about the current curve
 */
public class HilbertCurveDemo extends Application {
    
    private HilbertCurvePane hilbertPane;
    private TextField orderInput;
    private Button drawButton;
    private Label infoLabel;
    private Label statusLabel;
    private ExecutorService executor;
    
    // UI Constants
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 700;
    private static final int MIN_ORDER = 1;
    private static final int MAX_ORDER = 6;
    
    @Override
    public void start(Stage primaryStage) {
        executor = Executors.newCachedThreadPool();
        
        // Create the main layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        
        // Create title
        Label titleLabel = createTitleLabel();
        
        // Create description
        Label descriptionLabel = createDescriptionLabel();
        
        // Create control panel
        HBox controlPanel = createControlPanel();
        
        // Create info panel
        HBox infoPanel = createInfoPanel();
        
        // Create the Hilbert curve pane
        hilbertPane = new HilbertCurvePane();
        hilbertPane.setPrefWidth(550);
        hilbertPane.setPrefHeight(500);
        hilbertPane.setMinWidth(550);
        hilbertPane.setMinHeight(500);
        hilbertPane.setMaxWidth(550);
        hilbertPane.setMaxHeight(500);
        
        // Create status label
        statusLabel = new Label("Ready to draw Hilbert curve");
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        
        // Add all components to root
        root.getChildren().addAll(
            titleLabel, 
            descriptionLabel, 
            controlPanel, 
            infoPanel,
            hilbertPane,
            statusLabel
        );
        
        // Create scene
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Hilbert's Fun House");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(WINDOW_WIDTH);
        primaryStage.setMinHeight(WINDOW_HEIGHT);
        primaryStage.setMaxWidth(WINDOW_WIDTH);
        primaryStage.setMaxHeight(WINDOW_HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        // Set initial focus
        orderInput.requestFocus();
        
        // Add initial message
        updateStatus("Enter an order between " + MIN_ORDER + " and " + MAX_ORDER + " and click 'Draw Hilbert Curve'");
    }
    
    /**
     * Create the title label
     */
    private Label createTitleLabel() {
        Label titleLabel = new Label("Hilbert Curve Visualization");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        return titleLabel;
    }
    
    /**
     * Create the description label
     */
    private Label createDescriptionLabel() {
        Label descriptionLabel = new Label(
            "The Hilbert curve is a space-filling curve that visits every point in a square grid. " +
            "Enter an order (1-6) to generate and display the corresponding Hilbert curve."
        );
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
        return descriptionLabel;
    }
    
    /**
     * Create the control panel with input and button
     */
    private HBox createControlPanel() {
        HBox controlPanel = new HBox(10);
        controlPanel.setAlignment(Pos.CENTER_LEFT);
        
        // Create input label
        Label inputLabel = new Label("Enter an order:");
        inputLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Create input field
        orderInput = new TextField();
        orderInput.setPromptText("1-6");
        orderInput.setPrefWidth(100);
        orderInput.setStyle("-fx-font-size: 14px;");
        
        // Add input validation
        orderInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                orderInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        // Create draw button
        drawButton = new Button("Draw Hilbert Curve");
        drawButton.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #3498db; -fx-text-fill: white;");
        drawButton.setPrefWidth(150);
        drawButton.setPrefHeight(35);
        
        // Set button action
        drawButton.setOnAction(e -> drawHilbertCurve());
        
        // Handle Enter key in input field
        orderInput.setOnAction(e -> drawHilbertCurve());
        
        controlPanel.getChildren().addAll(inputLabel, orderInput, drawButton);
        return controlPanel;
    }
    
    /**
     * Create the info panel to display curve information
     */
    private HBox createInfoPanel() {
        HBox infoPanel = new HBox(20);
        infoPanel.setAlignment(Pos.CENTER_LEFT);
        
        infoLabel = new Label("Order: 1 | Points: 4 | Grid Size: 2×2");
        infoLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        
        infoPanel.getChildren().add(infoLabel);
        return infoPanel;
    }
    
    /**
     * Draw the Hilbert curve based on the input order
     */
    private void drawHilbertCurve() {
        try {
            // Get and validate input
            String inputText = orderInput.getText().trim();
            if (inputText.isEmpty()) {
                updateStatus("Please enter an order");
                return;
            }
            
            int order = Integer.parseInt(inputText);
            if (order < MIN_ORDER || order > MAX_ORDER) {
                updateStatus("Order must be between " + MIN_ORDER + " and " + MAX_ORDER);
                return;
            }
            
            // Disable button during drawing
            drawButton.setDisable(true);
            updateStatus("Drawing Hilbert curve of order " + order + "...");
            
            // Draw in background thread to keep UI responsive
            executor.submit(() -> {
                try {
                    // Set the order and redraw
                    Platform.runLater(() -> {
                        hilbertPane.setOrder(order);
                        updateInfoLabel(order);
                        updateStatus("Hilbert curve of order " + order + " drawn successfully");
                    });
                } catch (Exception e) {
                    Platform.runLater(() -> {
                        updateStatus("Error drawing curve: " + e.getMessage());
                    });
                } finally {
                    Platform.runLater(() -> drawButton.setDisable(false));
                }
            });
            
        } catch (NumberFormatException e) {
            updateStatus("Please enter a valid number between " + MIN_ORDER + " and " + MAX_ORDER);
        }
    }
    
    /**
     * Update the info label with current curve information
     */
    private void updateInfoLabel(int order) {
        int pointCount = HilbertCurveGenerator.getPointCount(order);
        int gridSize = (int) Math.pow(2, order);
        
        String info = String.format("Order: %d | Points: %d | Grid Size: %d×%d", 
                                   order, pointCount, gridSize, gridSize);
        infoLabel.setText(info);
    }
    
    /**
     * Update the status label
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }
    
    @Override
    public void stop() {
        if (executor != null) {
            executor.shutdown();
        }
    }
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
