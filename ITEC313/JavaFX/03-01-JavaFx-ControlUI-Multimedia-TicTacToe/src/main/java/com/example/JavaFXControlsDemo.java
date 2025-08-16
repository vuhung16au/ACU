package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX Controls Demo Application
 * 
 * This application demonstrates all the major JavaFX UI controls including:
 * - Labels with graphics
 * - Buttons with event handling
 * - CheckBoxes and RadioButtons
 * - TextFields and TextAreas
 * - ComboBoxes and ListViews
 * - ScrollBars and Sliders
 * - Media playback
 * - Tic-Tac-Toe game
 * 
 * @author ITEC313 Student
 * @version 1.0.0
 */
public class JavaFXControlsDemo extends Application {
    
    // Main display text that will be manipulated by various controls
    private Text displayText = new Text("JavaFX Programming Demo");
    
    // Font variations for text styling
    private Font normalFont = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 16);
    
    // No longer keeps MediaPlayer here; handled by MultimediaTab
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Controls and Multimedia Demo");
        
        // Create tabbed interface for different control categories
        TabPane tabPane = new TabPane();
        
    // Create tabs for different control groups (now in separate classes)
    Tab basicControlsTab = new BasicControlsTab(displayText, normalFont);
    Tab inputControlsTab = new InputControlsTab(displayText, normalFont);
    Tab selectionControlsTab = new SelectionControlsTab(displayText);
    Tab multimediaTab = new MultimediaTab();
    Tab flagAnthemTab = new FlagAnthemTab();
    Tab gameTab = new GameTab();
    Tab bouncingBallTab = new SliderDemo();
        
        tabPane.getTabs().addAll(
            basicControlsTab,
            inputControlsTab, 
            selectionControlsTab,
            multimediaTab,
            flagAnthemTab,
            gameTab,
            bouncingBallTab
        );
        
        // Create main scene
        Scene scene = new Scene(tabPane, 1000, 700);
        
        // Try to load CSS, but don't fail if it's not found
        try {
            String cssResource = getClass().getResource("/styles.css").toExternalForm();
            scene.getStylesheets().add(cssResource);
        } catch (Exception e) {
            System.out.println("Warning: Could not load CSS stylesheet: " + e.getMessage());
        }
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
