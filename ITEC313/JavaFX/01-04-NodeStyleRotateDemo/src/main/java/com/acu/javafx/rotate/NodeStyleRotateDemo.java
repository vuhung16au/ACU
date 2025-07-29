package com.acu.javafx.rotate;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * JavaFX application demonstrating common node properties and methods.
 * This application showcases:
 * - CSS styling of nodes
 * - Rotation of nodes
 * - Interactive controls for dynamic property changes
 */
public class NodeStyleRotateDemo extends Application {
    
    // UI Components
    private StackPane mainPane;
    private VBox demoContainer;
    private Button styledButton;
    private Rectangle styledRectangle;
    private Circle styledCircle;
    private Slider rotationSlider;
    private Label rotationLabel;
    
    @Override
    public void start(Stage primaryStage) {
        createUI();
        setupEventHandlers();
        
        Scene scene = new Scene(mainPane, 800, 600);
        primaryStage.setTitle("JavaFX Node Style and Rotate Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Creates the user interface components
     */
    private void createUI() {
        // Main container
        mainPane = new StackPane();
        mainPane.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20;");
        
        // Demo container
        demoContainer = new VBox(20);
        demoContainer.setAlignment(Pos.CENTER);
        demoContainer.setPadding(new Insets(20));
        
        // Title
        Label title = new Label("JavaFX Node Style & Rotate Demo");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");
        
        // Create demo components
        createStyledButton();
        createStyledShapes();
        createRotationControls();
        
        // Add all components to container
        demoContainer.getChildren().addAll(
            title,
            createButtonDemo(),
            createShapesDemo(),
            createRotationDemo()
        );
        
        mainPane.getChildren().add(demoContainer);
    }
    
    /**
     * Creates a styled button demonstration
     */
    private VBox createButtonDemo() {
        VBox buttonDemo = new VBox(10);
        buttonDemo.setAlignment(Pos.CENTER);
        buttonDemo.setStyle("-fx-background-color: white; -fx-border-color: #ccc; " +
                           "-fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 15;");
        
        Label label = new Label("Styled Button Demo");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        styledButton = new Button("Click Me!");
        styledButton.setStyle(
            "-fx-background-color: linear-gradient(#ff5400, #be1d00);" +
            "-fx-background-radius: 30;" +
            "-fx-background-insets: 0;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 10 20 10 20;" +
            "-fx-border-color: #333;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 30;"
        );
        
        // Add hover effect
        styledButton.setOnMouseEntered(e -> 
            styledButton.setStyle(
                "-fx-background-color: linear-gradient(#ff6a00, #d42300);" +
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-border-color: #333;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 30;" +
                "-fx-scale-x: 1.05;" +
                "-fx-scale-y: 1.05;"
            )
        );
        
        styledButton.setOnMouseExited(e -> 
            styledButton.setStyle(
                "-fx-background-color: linear-gradient(#ff5400, #be1d00);" +
                "-fx-background-radius: 30;" +
                "-fx-background-insets: 0;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 10 20 10 20;" +
                "-fx-border-color: #333;" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 30;" +
                "-fx-scale-x: 1.0;" +
                "-fx-scale-y: 1.0;"
            )
        );
        
        buttonDemo.getChildren().addAll(label, styledButton);
        return buttonDemo;
    }
    
    /**
     * Creates styled shapes demonstration
     */
    private VBox createShapesDemo() {
        VBox shapesDemo = new VBox(15);
        shapesDemo.setAlignment(Pos.CENTER);
        shapesDemo.setStyle("-fx-background-color: white; -fx-border-color: #ccc; " +
                           "-fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 15;");
        
        Label label = new Label("Styled Shapes Demo");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        HBox shapesContainer = new HBox(30);
        shapesContainer.setAlignment(Pos.CENTER);
        
        // Styled Rectangle
        styledRectangle = new Rectangle(80, 60);
        styledRectangle.setFill(Color.TRANSPARENT);
        styledRectangle.setStyle(
            "-fx-fill: linear-gradient(#e6f3ff, #0066cc);" +
            "-fx-stroke: #003d7a;" +
            "-fx-stroke-width: 3;" +
            "-fx-arc-width: 15;" +
            "-fx-arc-height: 15;"
        );
        
        // Styled Circle
        styledCircle = new Circle(40);
        styledCircle.setFill(Color.TRANSPARENT);
        styledCircle.setStyle(
            "-fx-fill: radial-gradient(center 30% 30%, radius 80%, #ffeb99, #ff9900);" +
            "-fx-stroke: #cc7700;" +
            "-fx-stroke-width: 3;"
        );
        
        shapesContainer.getChildren().addAll(styledRectangle, styledCircle);
        shapesDemo.getChildren().addAll(label, shapesContainer);
        
        return shapesDemo;
    }
    
    /**
     * Creates rotation demonstration with interactive controls
     */
    private VBox createRotationDemo() {
        VBox rotationDemo = new VBox(15);
        rotationDemo.setAlignment(Pos.CENTER);
        rotationDemo.setStyle("-fx-background-color: white; -fx-border-color: #ccc; " +
                             "-fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 15;");
        
        Label label = new Label("Interactive Rotation Demo");
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Rotation controls
        HBox rotationControls = new HBox(10);
        rotationControls.setAlignment(Pos.CENTER);
        
        Label sliderLabel = new Label("Rotation:");
        sliderLabel.setStyle("-fx-font-size: 12px;");
        
        rotationSlider = new Slider(0, 360, 45);
        rotationSlider.setShowTickLabels(true);
        rotationSlider.setShowTickMarks(true);
        rotationSlider.setMajorTickUnit(90);
        rotationSlider.setMinorTickCount(2);
        rotationSlider.setPrefWidth(200);
        
        rotationLabel = new Label("45°");
        rotationLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");
        
        rotationControls.getChildren().addAll(sliderLabel, rotationSlider, rotationLabel);
        
        // Demo pane that will be rotated
        StackPane rotatingPane = new StackPane();
        rotatingPane.setPrefSize(150, 100);
        rotatingPane.setStyle(
            "-fx-background-color: linear-gradient(#ffcccc, #ff6666);" +
            "-fx-border-color: #cc0000;" +
            "-fx-border-width: 2;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;"
        );
        
        Label rotatingLabel = new Label("Rotating Pane");
        rotatingLabel.setStyle("-fx-text-fill: #660000; -fx-font-weight: bold;");
        rotatingPane.getChildren().add(rotatingLabel);
        
        // Set initial rotation
        rotatingPane.setRotate(45);
        
        // Connect slider to rotation
        rotationSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double rotation = newValue.doubleValue();
            rotatingPane.setRotate(rotation);
            rotationLabel.setText(String.format("%.0f°", rotation));
            
            // Also rotate other demo elements
            styledButton.setRotate(rotation * 0.3); // Subtle rotation
            styledRectangle.setRotate(rotation * 0.5);
            styledCircle.setRotate(rotation * 0.7);
        });
        
        rotationDemo.getChildren().addAll(label, rotationControls, rotatingPane);
        return rotationDemo;
    }
    
    /**
     * Creates the styled button
     */
    private void createStyledButton() {
        // Already created in createButtonDemo()
    }
    
    /**
     * Creates styled shapes
     */
    private void createStyledShapes() {
        // Already created in createShapesDemo()
    }
    
    /**
     * Creates rotation controls
     */
    private void createRotationControls() {
        // Already created in createRotationDemo()
    }
    
    /**
     * Sets up event handlers for interactive components
     */
    private void setupEventHandlers() {
        // Button click handler
        if (styledButton != null) {
            styledButton.setOnAction(e -> {
                // Animate button rotation on click
                double currentRotation = styledButton.getRotate();
                styledButton.setRotate(currentRotation + 360);
                
                // Create a simple animation effect
                javafx.animation.RotateTransition rotate = 
                    new javafx.animation.RotateTransition(javafx.util.Duration.seconds(0.5), styledButton);
                rotate.setFromAngle(currentRotation);
                rotate.setToAngle(currentRotation + 360);
                rotate.play();
            });
        }
    }
    
    /**
     * The main method is only needed for IDEs with limited JavaFX support.
     * Not needed for running from the command line.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
} 