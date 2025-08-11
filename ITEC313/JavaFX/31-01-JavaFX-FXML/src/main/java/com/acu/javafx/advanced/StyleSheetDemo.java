package com.acu.javafx.advanced;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Advanced JavaFX and FXML Demo with CSS Styling
 * 
 * This application demonstrates how to use CSS styling in JavaFX applications.
 * It shows different ways to apply styles to UI nodes including:
 * - Style classes (.plaincircle, .circleborder, .border)
 * - Style IDs (#redcircle, #greencircle)
 * - Multiple style classes on a single node
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class StyleSheetDemo extends Application {
    
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create the main container with larger size
        HBox mainContainer = new HBox(20);
        mainContainer.setPadding(new Insets(20));
        
        Scene scene = new Scene(mainContainer, 600, 400);
        
        // Load the CSS stylesheet
        scene.getStylesheets().add("mystyle.css");
        
        // Create left side with circles demonstration
        VBox leftSide = new VBox(15);
        leftSide.setAlignment(Pos.TOP_CENTER);
        
        // Title for the circles section
        Text circlesTitle = new Text("CSS Styling Demonstration");
        circlesTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Create first pane with three circles
        Pane pane1 = new Pane();
        pane1.setPrefSize(200, 150);
        Circle circle1 = new Circle(50, 50, 30);
        Circle circle2 = new Circle(150, 50, 30);
        Circle circle3 = new Circle(100, 100, 30);
        
        // Add circles to the first pane
        pane1.getChildren().addAll(circle1, circle2, circle3);
        
        // Apply style classes and IDs
        pane1.getStyleClass().add("border");
        circle1.getStyleClass().add("plaincircle"); // Add a style class
        circle2.getStyleClass().add("plaincircle"); // Add a style class
        circle3.setId("redcircle"); // Add a style id
        
        // Create second pane with one circle
        Pane pane2 = new Pane();
        pane2.setPrefSize(200, 150);
        Circle circle4 = new Circle(100, 100, 30);
        
        // Apply multiple style classes and an ID to the same circle
        circle4.getStyleClass().addAll("circleborder", "plaincircle");
        circle4.setId("greencircle"); // Add a style id
        
        pane2.getChildren().add(circle4);
        pane2.getStyleClass().add("border");
        
        // Add panes to left side
        leftSide.getChildren().addAll(circlesTitle, pane1, pane2);
        
        // Create right side with instructions
        VBox rightSide = new VBox(15);
        rightSide.setAlignment(Pos.TOP_LEFT);
        rightSide.setPrefWidth(300);
        
        // Title for instructions
        Text instructionsTitle = new Text("Instructions & Guidelines");
        instructionsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Create scrollable content for instructions
        VBox instructionsContent = new VBox(10);
        instructionsContent.setAlignment(Pos.TOP_LEFT);
        
        // Instructions text
        Text instruction1 = new Text("1. CSS Integration:");
        instruction1.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Text instruction1Detail = new Text("• Stylesheet loaded via scene.getStylesheets().add()\n" +
                                         "• CSS file placed in src/main/resources\n" +
                                         "• Automatic resource inclusion in classpath");
        instruction1Detail.setFont(Font.font("Arial", 12));
        instruction1Detail.setWrappingWidth(280);
        
        Text instruction2 = new Text("2. Style Classes (.classname):");
        instruction2.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Text instruction2Detail = new Text("• Reusable styles for common elements\n" +
                                         "• Applied via getStyleClass().add()\n" +
                                         "• Multiple classes: getStyleClass().addAll()");
        instruction2Detail.setFont(Font.font("Arial", 12));
        instruction2Detail.setWrappingWidth(280);
        
        Text instruction3 = new Text("3. Style IDs (#idname):");
        instruction3.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Text instruction3Detail = new Text("• Unique styling for specific elements\n" +
                                         "• Applied via setId()\n" +
                                         "• Higher specificity than classes");
        instruction3Detail.setFont(Font.font("Arial", 12));
        instruction3Detail.setWrappingWidth(280);
        
        Text instruction4 = new Text("4. CSS Properties Used:");
        instruction4.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Text instruction4Detail = new Text("• -fx-fill: Sets fill color\n" +
                                         "• -fx-stroke: Sets stroke color\n" +
                                         "• -fx-stroke-width: Sets stroke thickness\n" +
                                         "• -fx-stroke-dash-array: Creates dashed lines\n" +
                                         "• -fx-border-color: Sets border color\n" +
                                         "• -fx-border-width: Sets border thickness");
        instruction4Detail.setFont(Font.font("Arial", 12));
        instruction4Detail.setWrappingWidth(280);
        
        Text instruction5 = new Text("5. Visual Output Summary:");
        instruction5.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        Text instruction5Detail = new Text("Left Pane:\n" +
                                         "• Two white circles with black stroke (plaincircle class)\n" +
                                         "• One red circle (redcircle ID)\n" +
                                         "• All in bordered container\n\n" +
                                         "Right Pane:\n" +
                                         "• One green circle with dashed border (greencircle ID + circleborder class)\n" +
                                         "• In bordered container");
        instruction5Detail.setFont(Font.font("Arial", 12));
        instruction5Detail.setWrappingWidth(280);
        
        // Add all instruction elements
        instructionsContent.getChildren().addAll(
            instruction1, instruction1Detail,
            instruction2, instruction2Detail,
            instruction3, instruction3Detail,
            instruction4, instruction4Detail,
            instruction5, instruction5Detail
        );
        
        // Create scroll pane for instructions
        ScrollPane scrollPane = new ScrollPane(instructionsContent);
        scrollPane.setPrefViewportHeight(300);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        
        // Add to right side
        rightSide.getChildren().addAll(instructionsTitle, scrollPane);
        
        // Add both sides to main container
        mainContainer.getChildren().addAll(leftSide, rightSide);
        
        // Set up the stage
        primaryStage.setTitle("StyleSheetDemo - Advanced JavaFX and FXML"); // Set the window title
        primaryStage.setScene(scene); // Place the scene in the window
        primaryStage.show(); // Display the window
    }
    
    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
