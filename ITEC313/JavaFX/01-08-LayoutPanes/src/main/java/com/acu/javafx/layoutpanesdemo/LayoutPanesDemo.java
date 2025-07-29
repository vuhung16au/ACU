package com.acu.javafx.layoutpanesdemo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX application demonstrating various layout panes and ImageView.
 * This application showcases Pane, StackPane, FlowPane, GridPane, 
 * BorderPane, HBox, VBox, and ImageView components.
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class LayoutPanesDemo extends Application {

    private TabPane tabPane;
    private int currentImageIndex = 0;
    private final String[] imageUrls = {
        "/image/us.gif",
        "/image/uk.gif",
        "/image/au.gif"
    };

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Layout Panes Demo");

        // Create tab pane to organize different layout demonstrations
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Add tabs for different layout panes
        tabPane.getTabs().addAll(
            createPaneTab(),
            createStackPaneTab(),
            createFlowPaneTab(),
            createGridPaneTab(),
            createBorderPaneTab(),
            createHBoxVBoxTab(),
            createImageViewTab()
        );

        Scene scene = new Scene(tabPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a tab demonstrating the basic Pane layout.
     */
    private Tab createPaneTab() {
        Tab tab = new Tab("Pane");
        tab.setClosable(false);

        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: lightblue;");

        // Add some shapes to the pane
        Circle circle = new Circle(100, 100, 50);
        circle.setFill(Color.RED);
        circle.setStroke(Color.DARKRED);
        circle.setStrokeWidth(3);

        Rectangle rectangle = new Rectangle(200, 200, 100, 80);
        rectangle.setFill(Color.GREEN);
        rectangle.setStroke(Color.DARKGREEN);
        rectangle.setStrokeWidth(2);

        Text text = new Text(50, 300, "Pane Demo - Absolute Positioning");
        text.setFont(Font.font("Arial", 16));
        text.setFill(Color.BLACK);

        pane.getChildren().addAll(circle, rectangle, text);

        tab.setContent(pane);
        return tab;
    }

    /**
     * Creates a tab demonstrating the StackPane layout.
     */
    private Tab createStackPaneTab() {
        Tab tab = new Tab("StackPane");
        tab.setClosable(false);

        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: lightyellow;");

        // Create a background rectangle
        Rectangle background = new Rectangle(300, 200);
        background.setFill(Color.LIGHTGRAY);
        background.setStroke(Color.GRAY);
        background.setStrokeWidth(2);

        // Create a circle in the center
        Circle centerCircle = new Circle(50);
        centerCircle.setFill(Color.ORANGE);
        centerCircle.setStroke(Color.DARKORANGE);
        centerCircle.setStrokeWidth(3);

        // Create text overlay
        Text text = new Text("StackPane Demo - Centered Elements");
        text.setFont(Font.font("Arial", 14));
        text.setFill(Color.BLACK);

        stackPane.getChildren().addAll(background, centerCircle, text);

        tab.setContent(stackPane);
        return tab;
    }

    /**
     * Creates a tab demonstrating the FlowPane layout.
     */
    private Tab createFlowPaneTab() {
        Tab tab = new Tab("FlowPane");
        tab.setClosable(false);

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(10));
        flowPane.setStyle("-fx-background-color: lightgreen;");

        // Add buttons to demonstrate flow layout
        for (int i = 1; i <= 12; i++) {
            Button button = new Button("Button " + i);
            button.setPrefSize(80, 40);
            flowPane.getChildren().add(button);
        }

        // Add some shapes
        Circle circle = new Circle(30);
        circle.setFill(Color.RED);
        flowPane.getChildren().add(circle);

        Rectangle rect = new Rectangle(60, 40);
        rect.setFill(Color.BLUE);
        flowPane.getChildren().add(rect);

        Text text = new Text("FlowPane Demo - Wrapping Layout");
        text.setFont(Font.font("Arial", 16));
        flowPane.getChildren().add(text);

        tab.setContent(flowPane);
        return tab;
    }

    /**
     * Creates a tab demonstrating the GridPane layout.
     */
    private Tab createGridPaneTab() {
        Tab tab = new Tab("GridPane");
        tab.setClosable(false);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setStyle("-fx-background-color: lightcoral;");

        // Add buttons in a grid pattern
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button("(" + row + "," + col + ")");
                button.setPrefSize(80, 40);
                gridPane.add(button, col, row);
            }
        }

        // Add a label spanning multiple columns
        Label label = new Label("GridPane Demo - Grid Layout");
        label.setFont(Font.font("Arial", 16));
        GridPane.setColumnSpan(label, 3);
        gridPane.add(label, 0, 3);

        tab.setContent(gridPane);
        return tab;
    }

    /**
     * Creates a tab demonstrating the BorderPane layout.
     */
    private Tab createBorderPaneTab() {
        Tab tab = new Tab("BorderPane");
        tab.setClosable(false);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: lightsteelblue;");

        // Top region
        HBox topBox = new HBox(10);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));
        topBox.setStyle("-fx-background-color: lightblue;");
        topBox.getChildren().addAll(
            new Button("File"),
            new Button("Edit"),
            new Button("View"),
            new Button("Help")
        );
        borderPane.setTop(topBox);

        // Left region
        VBox leftBox = new VBox(10);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPadding(new Insets(10));
        leftBox.setStyle("-fx-background-color: lightgreen;");
        leftBox.getChildren().addAll(
            new Button("Home"),
            new Button("Products"),
            new Button("Services"),
            new Button("Contact")
        );
        borderPane.setLeft(leftBox);

        // Right region
        VBox rightBox = new VBox(10);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(10));
        rightBox.setStyle("-fx-background-color: lightyellow;");
        rightBox.getChildren().addAll(
            new Label("Info"),
            new Label("Status"),
            new Label("Log")
        );
        borderPane.setRight(rightBox);

        // Bottom region
        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));
        bottomBox.setStyle("-fx-background-color: lightpink;");
        bottomBox.getChildren().addAll(
            new Label("Status: Ready"),
            new Label("Position: 0,0"),
            new Label("Zoom: 100%")
        );
        borderPane.setBottom(bottomBox);

        // Center region
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(20));
        centerBox.setStyle("-fx-background-color: white;");
        
        Text title = new Text("BorderPane Demo");
        title.setFont(Font.font("Arial", 20));
        
        Text description = new Text("This demonstrates the BorderPane layout\nwith content in all five regions");
        description.setFont(Font.font("Arial", 14));
        description.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        
        centerBox.getChildren().addAll(title, description);
        borderPane.setCenter(centerBox);

        tab.setContent(borderPane);
        return tab;
    }

    /**
     * Creates a tab demonstrating HBox and VBox layouts.
     */
    private Tab createHBoxVBoxTab() {
        Tab tab = new Tab("HBox & VBox");
        tab.setClosable(false);

        VBox mainBox = new VBox(20);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(20));
        mainBox.setStyle("-fx-background-color: lavender;");

        // HBox demonstration
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10));
        hBox.setStyle("-fx-background-color: lightblue;");
        hBox.getChildren().addAll(
            new Button("Left"),
            new Button("Center"),
            new Button("Right")
        );

        // VBox demonstration
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
        vBox.setStyle("-fx-background-color: lightgreen;");
        vBox.getChildren().addAll(
            new Button("Top"),
            new Button("Middle"),
            new Button("Bottom")
        );

        // Mixed layout
        HBox mixedBox = new HBox(20);
        mixedBox.setAlignment(Pos.CENTER);
        mixedBox.setPadding(new Insets(10));
        mixedBox.setStyle("-fx-background-color: lightyellow;");

        VBox leftVBox = new VBox(5);
        leftVBox.setAlignment(Pos.CENTER);
        leftVBox.getChildren().addAll(
            new Label("Left Column"),
            new Button("Button 1"),
            new Button("Button 2")
        );

        VBox rightVBox = new VBox(5);
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.getChildren().addAll(
            new Label("Right Column"),
            new Button("Button 3"),
            new Button("Button 4")
        );

        mixedBox.getChildren().addAll(leftVBox, rightVBox);

        // Add title
        Text title = new Text("HBox & VBox Demo");
        title.setFont(Font.font("Arial", 18));

        mainBox.getChildren().addAll(title, hBox, vBox, mixedBox);

        tab.setContent(mainBox);
        return tab;
    }

    /**
     * Creates a tab demonstrating ImageView functionality.
     */
    private Tab createImageViewTab() {
        Tab tab = new Tab("ImageView");
        tab.setClosable(false);

        VBox imageBox = new VBox(20);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setPadding(new Insets(20));
        imageBox.setStyle("-fx-background-color: lightcyan;");

        // Create ImageView
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        // Load initial image
        loadImage(imageView, 0);

        // Create controls
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);

        Button prevButton = new Button("Previous");
        Button nextButton = new Button("Next");
        Button rotateButton = new Button("Rotate");

        prevButton.setOnAction(e -> {
            currentImageIndex = (currentImageIndex - 1 + imageUrls.length) % imageUrls.length;
            loadImage(imageView, currentImageIndex);
        });

        nextButton.setOnAction(e -> {
            currentImageIndex = (currentImageIndex + 1) % imageUrls.length;
            loadImage(imageView, currentImageIndex);
        });

        rotateButton.setOnAction(e -> {
            imageView.setRotate(imageView.getRotate() + 90);
        });

        controls.getChildren().addAll(prevButton, nextButton, rotateButton);

        // Add title
        Text title = new Text("ImageView Demo");
        title.setFont(Font.font("Arial", 18));

        imageBox.getChildren().addAll(title, imageView, controls);

        tab.setContent(imageBox);
        return tab;
    }

    /**
     * Loads an image into the ImageView.
     * 
     * @param imageView the ImageView to load the image into
     * @param index the index of the image to load
     */
    private void loadImage(ImageView imageView, int index) {
        try {
            // Load image from classpath resources
            Image image = new Image(getClass().getResourceAsStream(imageUrls[index]));
            imageView.setImage(image);
        } catch (Exception e) {
            // Create a placeholder if image loading fails
            Rectangle placeholder = new Rectangle(200, 150);
            placeholder.setFill(Color.GRAY);
            placeholder.setStroke(Color.BLACK);
            placeholder.setStrokeWidth(2);
            
            // Convert Rectangle to Image (simplified approach)
            imageView.setImage(null);
            System.err.println("Failed to load image: " + imageUrls[index]);
        }
    }

    /**
     * Main method for direct execution.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
} 