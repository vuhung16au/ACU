package com.acu.javafx.textdemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * JavaFX application demonstrating Text, Font, and Color classes.
 * This application showcases various text styling capabilities including:
 * - Different font families, sizes, and styles
 * - Color manipulation and transparency
 * - Text positioning and layout
 * - Text effects like underline and strikethrough
 */
public class TextFontColorDemo extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Create the main container
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #f0f0f0, #e0e0e0);");
        
        // Create title
        Label titleLabel = new Label("JavaFX Text, Font & Color Demo");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);
        
        // Create tab pane for different demonstrations
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(800, 600);
        
        // Tab 1: Font Demo (from FontDemo example)
        Tab fontDemoTab = new Tab("Font Demo", createFontDemo());
        fontDemoTab.setClosable(false);
        
        // Tab 2: Show Text Demo (from ShowText example)
        Tab showTextTab = new Tab("Show Text Demo", createShowTextDemo());
        showTextTab.setClosable(false);
        
        // Tab 3: Color Demo
        Tab colorDemoTab = new Tab("Color Demo", createColorDemo());
        colorDemoTab.setClosable(false);
        
        // Tab 4: Combined Demo
        Tab combinedDemoTab = new Tab("Combined Demo", createCombinedDemo());
        combinedDemoTab.setClosable(false);
        
        tabPane.getTabs().addAll(fontDemoTab, showTextTab, colorDemoTab, combinedDemoTab);
        
        // Add components to root
        root.getChildren().addAll(titleLabel, tabPane);
        
        // Create scene and stage
        Scene scene = new Scene(root, 900, 700);
        primaryStage.setTitle("JavaFX Text, Font & Color Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Creates the Font Demo tab content based on the FontDemo example.
     */
    private VBox createFontDemo() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        
        // Create a pane to hold the circle and text
        StackPane pane = new StackPane();
        pane.setPrefSize(400, 300);
        pane.setStyle("-fx-border-color: #cccccc; -fx-border-width: 2; -fx-border-radius: 10;");
        
        // Create a circle and set its properties
        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle();
        circle.setRadius(80);
        circle.setStroke(Color.BLACK);
        circle.setFill(new Color(0.5, 0.5, 0.5, 0.1)); // Semi-transparent gray
        pane.getChildren().add(circle);
        
        // Create a label and set its properties
        Label label = new Label("JavaFX");
        label.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
        label.setTextFill(Color.DARKGREEN);
        pane.getChildren().add(label);
        
        // Add description
        Label description = new Label("Font Demo: Demonstrates font styling with Times New Roman, Bold, Italic");
        description.setFont(Font.font("Arial", 12));
        description.setTextFill(Color.GRAY);
        description.setWrapText(true);
        description.setMaxWidth(400);
        
        content.getChildren().addAll(pane, description);
        return content;
    }
    
    /**
     * Creates the Show Text Demo tab content based on the ShowText example.
     */
    private VBox createShowTextDemo() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        
        // Create a pane to hold the texts
        Pane pane = new Pane();
        pane.setPrefSize(500, 300);
        pane.setPadding(new Insets(10));
        pane.setStyle("-fx-border-color: #cccccc; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-color: white;");
        
        // Text 1: Courier font with bold and italic
        Text text1 = new Text(20, 30, "Programming is fun");
        text1.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 15));
        text1.setFill(Color.BLUE);
        pane.getChildren().add(text1);
        
        // Text 2: Default font
        Text text2 = new Text(60, 70, "Programming is fun\nDisplay text");
        text2.setFill(Color.BLACK);
        pane.getChildren().add(text2);
        
        // Text 3: Red text with underline and strikethrough
        Text text3 = new Text(10, 120, "Programming is fun\nDisplay text");
        text3.setFill(Color.RED);
        text3.setUnderline(true);
        text3.setStrikethrough(true);
        pane.getChildren().add(text3);
        
        // Text 4: Large text with custom color
        Text text4 = new Text(200, 180, "JavaFX Text");
        text4.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        text4.setFill(new Color(0.2, 0.6, 0.8, 1.0)); // Custom blue
        pane.getChildren().add(text4);
        
        // Add description
        Label description = new Label("Show Text Demo: Demonstrates various text styles, colors, and effects");
        description.setFont(Font.font("Arial", 12));
        description.setTextFill(Color.GRAY);
        description.setWrapText(true);
        description.setMaxWidth(500);
        
        content.getChildren().addAll(pane, description);
        return content;
    }
    
    /**
     * Creates the Color Demo tab content.
     */
    private VBox createColorDemo() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        
        // Create a grid to display different colors
        GridPane colorGrid = new GridPane();
        colorGrid.setHgap(10);
        colorGrid.setVgap(10);
        colorGrid.setAlignment(Pos.CENTER);
        
        // Define colors to demonstrate
        Color[] colors = {
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW,
            Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.PINK,
            Color.BROWN, Color.PURPLE, Color.GRAY, Color.BLACK
        };
        
        String[] colorNames = {
            "RED", "GREEN", "BLUE", "YELLOW",
            "CYAN", "MAGENTA", "ORANGE", "PINK",
            "BROWN", "PURPLE", "GRAY", "BLACK"
        };
        
        // Create color squares with labels
        for (int i = 0; i < colors.length; i++) {
            VBox colorBox = new VBox(5);
            colorBox.setAlignment(Pos.CENTER);
            
            // Color square
            javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(60, 60);
            rect.setFill(colors[i]);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(1);
            
            // Color name label
            Label nameLabel = new Label(colorNames[i]);
            nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            nameLabel.setTextFill(colors[i]);
            
            colorBox.getChildren().addAll(rect, nameLabel);
            colorGrid.add(colorBox, i % 4, i / 4);
        }
        
        // Add transparency demonstration
        VBox transparencyDemo = new VBox(10);
        transparencyDemo.setAlignment(Pos.CENTER);
        transparencyDemo.setPadding(new Insets(20));
        transparencyDemo.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-border-radius: 5;");
        
        Label transparencyLabel = new Label("Transparency Demo");
        transparencyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        HBox transparencyBox = new HBox(10);
        transparencyBox.setAlignment(Pos.CENTER);
        
        for (int i = 0; i < 5; i++) {
            javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(30);
            double opacity = (i + 1) * 0.2;
            circle.setFill(new Color(1.0, 0.0, 0.0, opacity)); // Red with varying opacity
            transparencyBox.getChildren().add(circle);
        }
        
        transparencyDemo.getChildren().addAll(transparencyLabel, transparencyBox);
        
        // Add description
        Label description = new Label("Color Demo: Demonstrates predefined colors and transparency effects");
        description.setFont(Font.font("Arial", 12));
        description.setTextFill(Color.GRAY);
        description.setWrapText(true);
        description.setMaxWidth(600);
        
        content.getChildren().addAll(colorGrid, transparencyDemo, description);
        return content;
    }
    
    /**
     * Creates the Combined Demo tab content.
     */
    private VBox createCombinedDemo() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.CENTER);
        
        // Create a canvas for combined demonstrations
        Pane canvas = new Pane();
        canvas.setPrefSize(600, 400);
        canvas.setStyle("-fx-border-color: #cccccc; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-color: white;");
        
        // Gradient background text
        Text gradientText = new Text(50, 80, "JavaFX Text & Color Demo");
        gradientText.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        gradientText.setFill(Color.TRANSPARENT);
        gradientText.setStroke(Color.BLACK);
        gradientText.setStrokeWidth(0.5);
        canvas.getChildren().add(gradientText);
        
        // Multi-colored text
        Text multiColorText = new Text(50, 120, "Rainbow Text Effect");
        multiColorText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        canvas.getChildren().add(multiColorText);
        
        // Create rainbow effect by overlaying multiple texts
        String rainbowText = "Rainbow Text Effect";
        Color[] rainbowColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.INDIGO, Color.VIOLET};
        
        for (int i = 0; i < rainbowText.length(); i++) {
            Text charText = new Text(50 + i * 12, 120, String.valueOf(rainbowText.charAt(i)));
            charText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
            charText.setFill(rainbowColors[i % rainbowColors.length]);
            canvas.getChildren().add(charText);
        }
        
        // Animated-style text (simulated with different styles)
        Text animatedText = new Text(50, 180, "Animated Style Text");
        animatedText.setFont(Font.font("Courier", FontWeight.BOLD, 18));
        animatedText.setFill(new Color(0.8, 0.2, 0.8, 1.0)); // Purple
        animatedText.setUnderline(true);
        canvas.getChildren().add(animatedText);
        
        // 3D effect text (simulated with multiple layers)
        Text text3D = new Text(50, 240, "3D Text Effect");
        text3D.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        text3D.setFill(Color.GRAY);
        canvas.getChildren().add(text3D);
        
        Text text3DFront = new Text(52, 238, "3D Text Effect");
        text3DFront.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        text3DFront.setFill(Color.WHITE);
        canvas.getChildren().add(text3DFront);
        
        // Glowing text effect
        Text glowingText = new Text(50, 300, "Glowing Text");
        glowingText.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        glowingText.setFill(Color.CYAN);
        canvas.getChildren().add(glowingText);
        
        // Add a glow effect by adding a slightly larger, semi-transparent text behind
        Text glowEffect = new Text(50, 300, "Glowing Text");
        glowEffect.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        glowEffect.setFill(new Color(0.0, 1.0, 1.0, 0.3)); // Semi-transparent cyan
        canvas.getChildren().add(0, glowEffect); // Add to beginning to place behind
        
        // Add description
        Label description = new Label("Combined Demo: Shows advanced text effects combining fonts, colors, and styling");
        description.setFont(Font.font("Arial", 12));
        description.setTextFill(Color.GRAY);
        description.setWrapText(true);
        description.setMaxWidth(600);
        
        content.getChildren().addAll(canvas, description);
        return content;
    }
    
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
} 