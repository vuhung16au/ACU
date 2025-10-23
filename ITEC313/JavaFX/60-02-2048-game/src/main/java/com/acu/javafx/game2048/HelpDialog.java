package com.acu.javafx.game2048;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Help dialog that displays game instructions and controls.
 * Shows how to play the 2048 game with clear, educational content.
 * 
 * @author ACU JavaFX Course
 * @version 1.0
 */
public class HelpDialog {
    
    private Stage dialogStage;
    
    /**
     * Creates and shows the help dialog.
     * 
     * @param parentStage The parent stage (main game window)
     */
    public void showHelp(Stage parentStage) {
        // Create the dialog stage
        dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(parentStage);
        dialogStage.setTitle("How to Play 2048 - ACU JavaFX");
        dialogStage.setResizable(false);
        
        // Create the main content
        VBox mainContent = createMainContent();
        
        // Create the scene
        Scene scene = new Scene(mainContent, 500, 600);
        scene.setFill(ACUColorScheme.getGameBackground());
        
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }
    
    /**
     * Creates the main content of the help dialog.
     * 
     * @return The main VBox containing all help content
     */
    private VBox createMainContent() {
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setAlignment(Pos.CENTER);
        mainContent.setBackground(new Background(new BackgroundFill(
            ACUColorScheme.getGameBackground(), null, null)));
        
        // Title
        Label titleLabel = createTitleLabel();
        
        // Game objective
        VBox objectiveSection = createObjectiveSection();
        
        // How to play
        VBox howToPlaySection = createHowToPlaySection();
        
        // Controls
        VBox controlsSection = createControlsSection();
        
        // Tips
        VBox tipsSection = createTipsSection();
        
        // Close button
        Button closeButton = createCloseButton();
        
        mainContent.getChildren().addAll(
            titleLabel,
            objectiveSection,
            howToPlaySection,
            controlsSection,
            tipsSection,
            closeButton
        );
        
        return mainContent;
    }
    
    /**
     * Creates the title label.
     * 
     * @return The styled title label
     */
    private Label createTitleLabel() {
        Label titleLabel = new Label("How to Play 2048");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setTextFill(ACUColorScheme.ACU_PURPLE);
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        return titleLabel;
    }
    
    /**
     * Creates the objective section.
     * 
     * @return VBox containing objective information
     */
    private VBox createObjectiveSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER_LEFT);
        
        Label sectionTitle = createSectionTitle("🎯 Objective");
        Label objectiveText = createTextLabel(
            "Combine numbered tiles to create a tile with the number 2048!\n" +
            "The game is won when you create the 2048 tile."
        );
        
        section.getChildren().addAll(sectionTitle, objectiveText);
        return section;
    }
    
    /**
     * Creates the how to play section.
     * 
     * @return VBox containing how to play information
     */
    private VBox createHowToPlaySection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER_LEFT);
        
        Label sectionTitle = createSectionTitle("🎮 How to Play");
        Label howToPlayText = createTextLabel(
            "• Use your arrow keys to move the tiles (or WASD)\n" +
            "• When two tiles with the same number touch, they merge into one!\n" +
            "• After each move, a new tile (2 or 4) appears in a random empty space\n" +
            "• The game ends when the board is full and no moves are possible\n" +
            "• Your score increases with each merge!"
        );
        
        section.getChildren().addAll(sectionTitle, howToPlayText);
        return section;
    }
    
    /**
     * Creates the controls section.
     * 
     * @return VBox containing controls information
     */
    private VBox createControlsSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER_LEFT);
        
        Label sectionTitle = createSectionTitle("⌨️ Controls");
        
        // Create a grid for controls
        GridPane controlsGrid = new GridPane();
        controlsGrid.setHgap(20);
        controlsGrid.setVgap(10);
        controlsGrid.setAlignment(Pos.CENTER);
        
        // Add control items
        addControlItem(controlsGrid, "Move Tiles", "Arrow Keys or WASD", 0);
        addControlItem(controlsGrid, "New Game", "R Key or NEW GAME Button", 1);
        addControlItem(controlsGrid, "Exit Game", "ESC Key", 2);
        addControlItem(controlsGrid, "Show Help", "? Button", 3);
        
        section.getChildren().addAll(sectionTitle, controlsGrid);
        return section;
    }
    
    /**
     * Creates the tips section.
     * 
     * @return VBox containing tips information
     */
    private VBox createTipsSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER_LEFT);
        
        Label sectionTitle = createSectionTitle("💡 Tips");
        Label tipsText = createTextLabel(
            "• Keep your highest tile in a corner\n" +
            "• Try to build up one side of the board\n" +
            "• Don't move randomly - plan your moves\n" +
            "• The 2048 tile is special - it uses ACU Purple color!"
        );
        
        section.getChildren().addAll(sectionTitle, tipsText);
        return section;
    }
    
    /**
     * Creates a section title label.
     * 
     * @param text The title text
     * @return The styled section title
     */
    private Label createSectionTitle(String text) {
        Label title = new Label(text);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setTextFill(ACUColorScheme.ACU_PURPLE);
        return title;
    }
    
    /**
     * Creates a text label with proper styling.
     * 
     * @param text The text content
     * @return The styled text label
     */
    private Label createTextLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 14));
        label.setTextFill(ACUColorScheme.getTextDark());
        label.setWrapText(true);
        label.setMaxWidth(450);
        return label;
    }
    
    /**
     * Adds a control item to the controls grid.
     * 
     * @param grid The grid to add to
     * @param action The action description
     * @param key The key/button description
     * @param row The row index
     */
    private void addControlItem(GridPane grid, String action, String key, int row) {
        Label actionLabel = new Label(action + ":");
        actionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        actionLabel.setTextFill(ACUColorScheme.getTextDark());
        
        Label keyLabel = new Label(key);
        keyLabel.setFont(Font.font("Arial", 14));
        keyLabel.setTextFill(ACUColorScheme.ACU_PURPLE);
        keyLabel.setStyle("-fx-background-color: #F2EFEB; -fx-padding: 5 10 5 10; -fx-background-radius: 5;");
        
        grid.add(actionLabel, 0, row);
        grid.add(keyLabel, 1, row);
    }
    
    /**
     * Creates the close button.
     * 
     * @return The styled close button
     */
    private Button createCloseButton() {
        Button closeButton = new Button("Got it! Let's Play!");
        closeButton.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        closeButton.setTextFill(ACUColorScheme.getTextLight());
        closeButton.setBackground(new Background(new BackgroundFill(
            ACUColorScheme.ACU_PURPLE, new CornerRadii(8), null)));
        closeButton.setPadding(new Insets(12, 24, 12, 24));
        closeButton.setStyle("-fx-background-radius: 8;");
        
        closeButton.setOnAction(e -> dialogStage.close());
        
        return closeButton;
    }
}
