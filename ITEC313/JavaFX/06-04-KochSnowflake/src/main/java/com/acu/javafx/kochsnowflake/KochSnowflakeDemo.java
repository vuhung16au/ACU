package com.acu.javafx.kochsnowflake;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Koch Snowflake Fractal Demo - JavaFX Application
 * 
 * This application demonstrates the Koch snowflake fractal with interactive controls.
 * Users can adjust the fractal order using a slider and clear the display.
 * 
 * Features:
 * - Interactive slider to control fractal order (0-6)
 * - Clear button to reset the display
 * - Real-time fractal generation and display
 * - Information display showing current order and segment count
 * 
 * @author ACU JavaFX Team
 * @version 1.0
 */
public class KochSnowflakeDemo extends Application {
    
    private static final int DEFAULT_CANVAS_SIZE = 500;
    private static final int MAX_ORDER = 6;
    private static final int DEFAULT_ORDER = 0;
    private static final int MIN_WINDOW_WIDTH = 650;
    private static final int MIN_WINDOW_HEIGHT = 750;
    
    private KochSnowflake kochSnowflake;
    private Canvas canvas;
    private Slider orderSlider;
    private Label orderLabel;
    private Label segmentCountLabel;
    private Label perimeterLabel;
    private Button clearButton;
    private ScrollPane canvasScrollPane;
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize the Koch snowflake
        kochSnowflake = new KochSnowflake(DEFAULT_ORDER, DEFAULT_CANVAS_SIZE, Color.BLUE);
        
        // Create the main layout using BorderPane for better control
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        
        // Create title
        Label titleLabel = createTitleLabel();
        
        // Create description
        Label descriptionLabel = createDescriptionLabel();
        
        // Create control panel
        HBox controlPanel = createControlPanel();
        
        // Create information panel
        HBox infoPanel = createInfoPanel();
        
        // Create canvas with scroll pane
        canvas = createCanvas();
        canvasScrollPane = createCanvasScrollPane();
        
        // Create clear button
        clearButton = createClearButton();
        
        // Create top section with title, description, and controls
        VBox topSection = new VBox(15);
        topSection.setAlignment(Pos.CENTER);
        topSection.getChildren().addAll(
            titleLabel,
            descriptionLabel,
            controlPanel,
            infoPanel
        );
        
        // Create bottom section with clear button positioned to the right
        HBox bottomSection = new HBox();
        bottomSection.setAlignment(Pos.CENTER_RIGHT);
        bottomSection.setPadding(new Insets(10, 0, 0, 0));
        bottomSection.getChildren().add(clearButton);
        
        // Set up BorderPane layout
        root.setTop(topSection);
        root.setCenter(canvasScrollPane);
        root.setBottom(bottomSection);
        
        // Create scene with proper sizing
        Scene scene = new Scene(root, MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
        primaryStage.setTitle("Koch Snowflake Fractal Demo");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(MIN_WINDOW_WIDTH);
        primaryStage.setMinHeight(MIN_WINDOW_HEIGHT);
        primaryStage.setResizable(true);
        primaryStage.show();
        
        // Initial draw
        drawFractal();
    }
    
    /**
     * Creates the title label
     */
    private Label createTitleLabel() {
        Label titleLabel = new Label("Koch Snowflake Fractal");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        return titleLabel;
    }
    
    /**
     * Creates the description label
     */
    private Label createDescriptionLabel() {
        Label descriptionLabel = new Label(
            "The Koch snowflake is a fractal created by recursively dividing line segments " +
            "and adding equilateral triangles. Use the slider to change the fractal order."
        );
        descriptionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(550);
        return descriptionLabel;
    }
    
    /**
     * Creates the control panel with slider
     */
    private HBox createControlPanel() {
        HBox controlPanel = new HBox(15);
        controlPanel.setAlignment(Pos.CENTER);
        
        // Order label
        Label orderTextLabel = new Label("Order:");
        orderTextLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        // Order slider
        orderSlider = new Slider(0, MAX_ORDER, DEFAULT_ORDER);
        orderSlider.setMajorTickUnit(1);
        orderSlider.setMinorTickCount(0);
        orderSlider.setShowTickLabels(true);
        orderSlider.setShowTickMarks(true);
        orderSlider.setSnapToTicks(true);
        orderSlider.setPrefWidth(200);
        
        // Order value label
        orderLabel = new Label(String.valueOf(DEFAULT_ORDER));
        orderLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-min-width: 30px;");
        
        // Add listener to slider
        orderSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            int newOrder = newVal.intValue();
            orderLabel.setText(String.valueOf(newOrder));
            kochSnowflake.setOrder(newOrder);
            drawFractal();
            updateInfoLabels();
        });
        
        controlPanel.getChildren().addAll(orderTextLabel, orderSlider, orderLabel);
        return controlPanel;
    }
    
    /**
     * Creates the information panel
     */
    private HBox createInfoPanel() {
        HBox infoPanel = new HBox(20);
        infoPanel.setAlignment(Pos.CENTER);
        
        // Segment count label
        segmentCountLabel = new Label();
        segmentCountLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        
        // Perimeter label
        perimeterLabel = new Label();
        perimeterLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        
        infoPanel.getChildren().addAll(segmentCountLabel, perimeterLabel);
        return infoPanel;
    }
    
    /**
     * Creates the canvas for drawing the fractal
     */
    private Canvas createCanvas() {
        Canvas canvas = new Canvas(DEFAULT_CANVAS_SIZE, DEFAULT_CANVAS_SIZE);
        canvas.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 2px;");
        return canvas;
    }
    
    /**
     * Creates a scroll pane for the canvas to handle overflow
     */
    private ScrollPane createCanvasScrollPane() {
        ScrollPane scrollPane = new ScrollPane(canvas);
        scrollPane.setFitToWidth(false);
        scrollPane.setFitToHeight(false);
        scrollPane.setPrefSize(DEFAULT_CANVAS_SIZE + 40, DEFAULT_CANVAS_SIZE + 40);
        scrollPane.setMinSize(DEFAULT_CANVAS_SIZE + 40, DEFAULT_CANVAS_SIZE + 40);
        scrollPane.setStyle("-fx-background-color: #f8f9fa;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPadding(new Insets(10));
        return scrollPane;
    }
    
    /**
     * Creates the clear button
     */
    private Button createClearButton() {
        Button clearButton = new Button("Clear");
        clearButton.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-background-color: #e74c3c; " +
            "-fx-text-fill: white; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 5px;"
        );
        clearButton.setOnAction(e -> clearFractal());
        return clearButton;
    }
    
    /**
     * Draws the fractal on the canvas
     */
    private void drawFractal() {
        kochSnowflake.draw(canvas);
    }
    
    /**
     * Clears the fractal and resets to order 0
     */
    private void clearFractal() {
        orderSlider.setValue(0);
        kochSnowflake.setOrder(0);
        drawFractal();
        updateInfoLabels();
    }
    
    /**
     * Updates the information labels
     */
    private void updateInfoLabels() {
        int order = kochSnowflake.getOrder();
        int segmentCount = kochSnowflake.getSegmentCount();
        double perimeter = KochSnowflake.calculatePerimeter(order, DEFAULT_CANVAS_SIZE);
        
        segmentCountLabel.setText(String.format("Segments: %d", segmentCount));
        perimeterLabel.setText(String.format("Perimeter: %.2f", perimeter));
    }
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
