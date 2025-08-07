package com.acu.javafx.imagedemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Enhanced JavaFX application demonstrating Image and ImageView classes.
 * This application showcases various image manipulation techniques including:
 * - Loading images from different sources
 * - ImageView properties and transformations
 * - Interactive image controls
 * - Error handling for missing images
 */
public class ImageDemo extends Application {
    
    // UI Components
    private VBox mainContainer;
    private HBox imageContainer;
    private VBox controlPanel;
    private Slider scaleSlider;
    private Slider rotationSlider;
    private ComboBox<String> imageSelector;
    private Label statusLabel;
    
    // Image and ImageView objects
    private Image currentImage;
    private ImageView mainImageView;
    private ImageView scaledImageView;
    private ImageView rotatedImageView;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Image and ImageView Demo");
        
        // Initialize UI components
        createUI();
        setupEventHandlers();
        loadDefaultImage();
        
        // Create main scene
        Scene scene = new Scene(mainContainer, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }
    
    /**
     * Creates the user interface components
     */
    private void createUI() {
        // Main container
        mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setAlignment(Pos.CENTER);
        
        // Title
        Label titleLabel = new Label("JavaFX Image and ImageView Demo");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Image container
        imageContainer = new HBox(20);
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setPadding(new Insets(20));
        imageContainer.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-color: #ecf0f1;");
        
        // Create image views
        mainImageView = new ImageView();
        mainImageView.setFitWidth(200);
        mainImageView.setFitHeight(150);
        mainImageView.setPreserveRatio(true);
        mainImageView.setStyle("-fx-border-color: #34495e; -fx-border-width: 1;");
        
        scaledImageView = new ImageView();
        scaledImageView.setFitWidth(200);
        scaledImageView.setFitHeight(150);
        scaledImageView.setPreserveRatio(true);
        scaledImageView.setStyle("-fx-border-color: #e74c3c; -fx-border-width: 1;");
        
        rotatedImageView = new ImageView();
        rotatedImageView.setFitWidth(200);
        rotatedImageView.setFitHeight(150);
        rotatedImageView.setPreserveRatio(true);
        rotatedImageView.setStyle("-fx-border-color: #27ae60; -fx-border-width: 1;");
        
        // Add image views to container
        VBox originalBox = createImageBox("Original", mainImageView);
        VBox scaledBox = createImageBox("Scaled", scaledImageView);
        VBox rotatedBox = createImageBox("Rotated", rotatedImageView);
        
        imageContainer.getChildren().addAll(originalBox, scaledBox, rotatedBox);
        
        // Control panel
        controlPanel = createControlPanel();
        
        // Status label
        statusLabel = new Label("Ready");
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        
        // Add all components to main container
        mainContainer.getChildren().addAll(titleLabel, imageContainer, controlPanel, statusLabel);
    }
    
    /**
     * Creates a labeled image box
     */
    private VBox createImageBox(String label, ImageView imageView) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        
        Label titleLabel = new Label(label);
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        box.getChildren().addAll(titleLabel, imageView);
        return box;
    }
    
    /**
     * Creates the control panel with interactive controls
     */
    private VBox createControlPanel() {
        VBox panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(20));
        panel.setStyle("-fx-border-color: #95a5a6; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-color: #f8f9fa;");
        
        // Image selector
        Label selectorLabel = new Label("Select Image:");
        imageSelector = new ComboBox<>();
        imageSelector.getItems().addAll("AU Flag", "Sample Image 1", "Sample Image 2");
        imageSelector.setValue("AU Flag");
        imageSelector.setPrefWidth(200);
        
        // Scale slider
        Label scaleLabel = new Label("Scale Factor:");
        scaleSlider = new Slider(0.1, 3.0, 1.0);
        scaleSlider.setShowTickLabels(true);
        scaleSlider.setShowTickMarks(true);
        scaleSlider.setMajorTickUnit(0.5);
        scaleSlider.setMinorTickCount(4);
        scaleSlider.setPrefWidth(300);
        
        // Rotation slider
        Label rotationLabel = new Label("Rotation (degrees):");
        rotationSlider = new Slider(0, 360, 0);
        rotationSlider.setShowTickLabels(true);
        rotationSlider.setShowTickMarks(true);
        rotationSlider.setMajorTickUnit(90);
        rotationSlider.setMinorTickCount(3);
        rotationSlider.setPrefWidth(300);
        
        // Reset button
        Button resetButton = new Button("Reset All");
        resetButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        resetButton.setPrefWidth(120);
        
        panel.getChildren().addAll(
            selectorLabel, imageSelector,
            scaleLabel, scaleSlider,
            rotationLabel, rotationSlider,
            resetButton
        );
        
        return panel;
    }
    
    /**
     * Sets up event handlers for interactive controls
     */
    private void setupEventHandlers() {
        // Image selector event
        imageSelector.setOnAction(e -> loadSelectedImage());
        
        // Scale slider event
        scaleSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (currentImage != null) {
                double scale = newVal.doubleValue();
                scaledImageView.setFitWidth(200 * scale);
                scaledImageView.setFitHeight(150 * scale);
                updateStatus("Scale factor: " + String.format("%.2f", scale));
            }
        });
        
        // Rotation slider event
        rotationSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (currentImage != null) {
                double rotation = newVal.doubleValue();
                rotatedImageView.setRotate(rotation);
                updateStatus("Rotation: " + String.format("%.1f", rotation) + "°");
            }
        });
        
        // Reset button event
        controlPanel.getChildren().stream()
            .filter(node -> node instanceof Button)
            .map(node -> (Button) node)
            .findFirst()
            .ifPresent(button -> button.setOnAction(e -> resetAll()));
    }
    
    /**
     * Loads the default image
     */
    private void loadDefaultImage() {
        try {
            // Try to load the AU flag image from classpath
            currentImage = new Image(getClass().getResource("/image/au.gif").toExternalForm());
            updateAllImageViews();
            updateStatus("Loaded AU flag image successfully");
        } catch (Exception e) {
            // If image not found, create a placeholder
            createPlaceholderImage();
            updateStatus("Warning: Could not load image. Using placeholder.");
        }
    }
    
    /**
     * Loads the selected image
     */
    private void loadSelectedImage() {
        String selected = imageSelector.getValue();
        try {
            switch (selected) {
                case "AU Flag":
                    currentImage = new Image(getClass().getResource("/image/au.gif").toExternalForm());
                    break;
                case "Sample Image 1":
                    currentImage = new Image(getClass().getResource("/image/sample1.jpg").toExternalForm());
                    break;
                case "Sample Image 2":
                    currentImage = new Image(getClass().getResource("/image/sample2.png").toExternalForm());
                    break;
                default:
                    currentImage = new Image(getClass().getResource("/image/au.gif").toExternalForm());
            }
            updateAllImageViews();
            updateStatus("Loaded " + selected + " successfully");
        } catch (Exception e) {
            createPlaceholderImage();
            updateStatus("Warning: Could not load " + selected + ". Using placeholder.");
        }
    }
    
    /**
     * Creates a placeholder image when the actual image cannot be loaded
     */
    private void createPlaceholderImage() {
        // Create a simple colored rectangle as placeholder
        // This is a simplified approach - in a real application, you might use a Canvas
        currentImage = new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNkYPhfDwAChwGA60e6kgAAAABJRU5ErkJggg==");
    }
    
    /**
     * Updates all image views with the current image
     */
    private void updateAllImageViews() {
        mainImageView.setImage(currentImage);
        scaledImageView.setImage(currentImage);
        rotatedImageView.setImage(currentImage);
    }
    
    /**
     * Resets all controls to default values
     */
    private void resetAll() {
        scaleSlider.setValue(1.0);
        rotationSlider.setValue(0);
        imageSelector.setValue("AU Flag");
        updateStatus("All controls reset to default values");
    }
    
    /**
     * Updates the status label
     */
    private void updateStatus(String message) {
        statusLabel.setText(message);
    }
    
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
} 