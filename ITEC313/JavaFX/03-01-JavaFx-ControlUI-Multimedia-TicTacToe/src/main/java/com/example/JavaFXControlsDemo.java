package com.example;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    
    // Media player components
    private MediaPlayer mediaPlayer;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Controls and Multimedia Demo");
        
        // Create tabbed interface for different control categories
        TabPane tabPane = new TabPane();
        
        // Create tabs for different control groups
        Tab basicControlsTab = createBasicControlsTab();
        Tab inputControlsTab = createInputControlsTab();
        Tab selectionControlsTab = createSelectionControlsTab();
        Tab multimediaTab = createMultimediaTab();
        Tab gameTab = createGameTab();
        
        tabPane.getTabs().addAll(
            basicControlsTab,
            inputControlsTab, 
            selectionControlsTab,
            multimediaTab,
            gameTab
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
    
    /**
     * Creates the Basic Controls tab (Labels, Buttons, CheckBoxes, RadioButtons)
     */
    private Tab createBasicControlsTab() {
        Tab tab = new Tab("Basic Controls");
        tab.setClosable(false);
        
        BorderPane mainPane = new BorderPane();
        
        // Top: Labels with graphics demonstration
        VBox labelSection = createLabelSection();
        
        // Center: Text display area
        Pane textPane = new Pane();
        displayText.setX(50);
        displayText.setY(100);
        displayText.setFont(normalFont);
        displayText.setFill(Color.BLACK);
        textPane.getChildren().add(displayText);
        textPane.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        textPane.setPrefSize(400, 150);
        
        // Left: Radio buttons for text color
        VBox radioSection = createRadioButtonSection();
        
        // Right: Check boxes for text styling
        VBox checkBoxSection = createCheckBoxSection();
        
        // Bottom: Buttons for text movement
        HBox buttonSection = createButtonSection();
        
        mainPane.setTop(labelSection);
        mainPane.setCenter(textPane);
        mainPane.setLeft(radioSection);
        mainPane.setRight(checkBoxSection);
        mainPane.setBottom(buttonSection);
        
        tab.setContent(mainPane);
        return tab;
    }
    
    /**
     * Creates the Input Controls tab (TextFields, PasswordFields, TextAreas)
     */
    private Tab createInputControlsTab() {
        Tab tab = new Tab("Input Controls");
        tab.setClosable(false);
        
        VBox mainPane = new VBox(20);
        mainPane.setPadding(new Insets(20));
        
        // TextField section
        VBox textFieldSection = new VBox(10);
        textFieldSection.getChildren().add(new Label("Text Input:"));
        
        TextField textField = new TextField();
        textField.setPromptText("Enter text here...");
        textField.setOnAction(_ -> displayText.setText(textField.getText()));
        
        textFieldSection.getChildren().add(textField);
        
        // PasswordField section
        VBox passwordSection = new VBox(10);
        passwordSection.getChildren().add(new Label("Password Input:"));
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password...");
        
        Label passwordLabel = new Label("Password will appear here when entered");
        passwordField.setOnAction(_ -> {
            if (!passwordField.getText().isEmpty()) {
                passwordLabel.setText("Password entered: " + "*".repeat(passwordField.getText().length()));
            }
        });
        
        passwordSection.getChildren().addAll(passwordField, passwordLabel);
        
        // TextArea section
        VBox textAreaSection = new VBox(10);
        textAreaSection.getChildren().add(new Label("Multi-line Text Input:"));
        
        TextArea textArea = new TextArea();
        textArea.setPromptText("Enter multiple lines of text...");
        textArea.setPrefRowCount(5);
        textArea.setWrapText(true);
        
        Button updateFromTextArea = new Button("Update Display Text");
        updateFromTextArea.setOnAction(_ -> {
            if (!textArea.getText().isEmpty()) {
                // Take first line for display
                String[] lines = textArea.getText().split("\n");
                displayText.setText(lines[0]);
            }
        });
        
        textAreaSection.getChildren().addAll(textArea, updateFromTextArea);
        
        // Text display area
        Pane textPane = new Pane();
        Text localDisplayText = new Text(displayText.getText());
        localDisplayText.setX(50);
        localDisplayText.setY(50);
        localDisplayText.setFont(normalFont);
        textPane.getChildren().add(localDisplayText);
        textPane.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        textPane.setPrefSize(400, 100);
        
        // Bind the local display text to the main display text
        localDisplayText.textProperty().bind(displayText.textProperty());
        
        mainPane.getChildren().addAll(
            textFieldSection,
            passwordSection, 
            textAreaSection,
            new Label("Display Area:"),
            textPane
        );
        
        ScrollPane scrollPane = new ScrollPane(mainPane);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        return tab;
    }
    
    /**
     * Creates the Selection Controls tab (ComboBox, ListView, ScrollBar, Slider)
     */
    private Tab createSelectionControlsTab() {
        Tab tab = new Tab("Selection Controls");
        tab.setClosable(false);
        
        BorderPane mainPane = new BorderPane();
        
        // Top: ComboBox and ListView
        HBox topSection = new HBox(20);
        topSection.setPadding(new Insets(20));
        
        // ComboBox section
        VBox comboSection = new VBox(10);
        comboSection.getChildren().add(new Label("Select Font:"));
        
        ComboBox<String> fontComboBox = new ComboBox<>();
        fontComboBox.getItems().addAll("Arial", "Times New Roman", "Courier New", "Helvetica", "Georgia");
        fontComboBox.setValue("Arial");
        fontComboBox.setOnAction(_ -> {
            String selectedFont = fontComboBox.getValue();
            displayText.setFont(Font.font(selectedFont, displayText.getFont().getSize()));
        });
        
        comboSection.getChildren().add(fontComboBox);
        
        // ListView section
        VBox listSection = new VBox(10);
        listSection.getChildren().add(new Label("Select Colors (Multiple):"));
        
        ListView<String> colorListView = new ListView<>();
        ObservableList<String> colors = FXCollections.observableArrayList(
            "Black", "Red", "Green", "Blue", "Orange", "Purple", "Brown"
        );
        colorListView.setItems(colors);
        colorListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        colorListView.setPrefHeight(120);
        
        Label selectedColorsLabel = new Label("Selected: None");
        colorListView.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> {
            ObservableList<String> selected = colorListView.getSelectionModel().getSelectedItems();
            selectedColorsLabel.setText("Selected: " + String.join(", ", selected));
            
            // Change text color to first selected color
            if (!selected.isEmpty()) {
                Color color = Color.valueOf(selected.get(0).toLowerCase());
                displayText.setFill(color);
            }
        });
        
        listSection.getChildren().addAll(colorListView, selectedColorsLabel);
        
        topSection.getChildren().addAll(comboSection, listSection);
        
        // Center: Text display with ScrollBar and Slider controls
        VBox centerSection = new VBox(10);
        centerSection.setPadding(new Insets(20));
        
        // Text display pane
        Pane textPane = new Pane();
        textPane.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        textPane.setPrefSize(400, 200);
        
        Text movableText = new Text("Move me with scrollbars and sliders!");
        movableText.setX(50);
        movableText.setY(50);
        textPane.getChildren().add(movableText);
        
        // ScrollBar controls
        HBox scrollBarSection = new HBox(10);
        scrollBarSection.getChildren().add(new Label("ScrollBars:"));
        
        ScrollBar horizontalScrollBar = new ScrollBar();
        horizontalScrollBar.setOrientation(Orientation.HORIZONTAL);
        horizontalScrollBar.setMin(0);
        horizontalScrollBar.setMax(300);
        horizontalScrollBar.setValue(50);
        horizontalScrollBar.valueProperty().addListener((_, _, newVal) -> {
            movableText.setX(newVal.doubleValue());
        });
        
        ScrollBar verticalScrollBar = new ScrollBar();
        verticalScrollBar.setOrientation(Orientation.VERTICAL);
        verticalScrollBar.setMin(20);
        verticalScrollBar.setMax(180);
        verticalScrollBar.setValue(50);
        verticalScrollBar.valueProperty().addListener((_, _, newVal) -> {
            movableText.setY(newVal.doubleValue());
        });
        
        // Slider controls  
        HBox sliderSection = new HBox(10);
        sliderSection.getChildren().add(new Label("Sliders (Font Size & Rotation):"));
        
        Slider fontSizeSlider = new Slider(8, 48, 16);
        fontSizeSlider.setShowTickLabels(true);
        fontSizeSlider.setShowTickMarks(true);
        fontSizeSlider.setMajorTickUnit(8);
        fontSizeSlider.valueProperty().addListener((_, _, newVal) -> {
            movableText.setFont(Font.font(movableText.getFont().getFamily(), newVal.doubleValue()));
        });
        
        Slider rotationSlider = new Slider(-180, 180, 0);
        rotationSlider.setShowTickLabels(true);
        rotationSlider.setShowTickMarks(true);
        rotationSlider.setMajorTickUnit(45);
        rotationSlider.valueProperty().addListener((_, _, newVal) -> {
            movableText.setRotate(newVal.doubleValue());
        });
        
        VBox sliderControls = new VBox(10);
        sliderControls.getChildren().addAll(
            new Label("Font Size:"), fontSizeSlider,
            new Label("Rotation:"), rotationSlider
        );
        
        BorderPane scrollBarPane = new BorderPane();
        scrollBarPane.setCenter(textPane);
        scrollBarPane.setBottom(horizontalScrollBar);
        scrollBarPane.setRight(verticalScrollBar);
        
        centerSection.getChildren().addAll(scrollBarPane, sliderControls);
        
        mainPane.setTop(topSection);
        mainPane.setCenter(centerSection);
        
        tab.setContent(mainPane);
        return tab;
    }
    
    /**
     * Creates the Multimedia tab (Media playback)
     */
    private Tab createMultimediaTab() {
        Tab tab = new Tab("Multimedia");
        tab.setClosable(false);
        
        VBox mainPane = new VBox(20);
        mainPane.setPadding(new Insets(20));
        mainPane.setAlignment(Pos.CENTER);
        
        // Media section
        try {
            // Try to load the MP3 file from the media directory
            java.io.File mediaFile = new java.io.File("media/sound-design-elements-sfx-ps-022-302865.mp3");
            String mediaUrl;
            
            if (mediaFile.exists()) {
                // Convert to absolute URI
                mediaUrl = mediaFile.toURI().toString();
            } else {
                // Try alternative path relative to project root
                mediaFile = new java.io.File("../media/sound-design-elements-sfx-ps-022-302865.mp3");

                if (mediaFile.exists()) {
                    mediaUrl = mediaFile.toURI().toString();
                } else {
                    // Try with full workspace path
                    mediaFile = new java.io.File("https://github.com/vuhung16au/ACU/raw/refs/heads/main/ITEC313/JavaFX/03-01-JavaFx-ControlUI-Multimedia-TicTacToe/media/sound-design-elements-sfx-ps-022-302865.mp3");
                    if (mediaFile.exists()) {
                        mediaUrl = mediaFile.toURI().toString();
                    } else {
                        throw new Exception("MP3 file not found in any expected location");
                    }
                }
            }
            
            System.out.println("Loading media from: " + mediaUrl);
            Media media = new Media(mediaUrl);
            mediaPlayer = new MediaPlayer(media);
            
            // For audio files, we don't need MediaView (it's for video)
            // Just create a placeholder for the audio player
            VBox audioPlayerPane = new VBox(10);
            audioPlayerPane.setAlignment(Pos.CENTER);
            audioPlayerPane.setStyle("-fx-border-color: lightgray; -fx-border-width: 2; -fx-padding: 20;");
            
            Label audioLabel = new Label("🎵 Audio Player");
            audioLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            
            Label fileLabel = new Label("Playing: sound-design-elements-sfx-ps-022-302865.mp3");
            fileLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            fileLabel.setStyle("-fx-text-fill: gray;");
            
            audioPlayerPane.getChildren().addAll(audioLabel, fileLabel);
            
            // Media controls
            HBox mediaControls = createMediaControls();
            
            mainPane.getChildren().addAll(
                new Label("Media Player Demo"),
                audioPlayerPane,
                mediaControls
            );
            
        } catch (Exception e) {
            // Fallback if media cannot be loaded
            Label errorLabel = new Label("Media playback not available");
            errorLabel.setStyle("-fx-text-fill: red;");
            
            // Create a simple animation demo instead
            VBox animationDemo = createAnimationDemo();
            
            mainPane.getChildren().addAll(
                errorLabel,
                new Separator(),
                animationDemo
            );
        }
        
        ScrollPane scrollPane = new ScrollPane(mainPane);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
        return tab;
    }
    
    /**
     * Creates the Game tab (Tic-Tac-Toe)
     */
    private Tab createGameTab() {
        Tab tab = new Tab("Tic-Tac-Toe Game");
        tab.setClosable(false);
        
        VBox mainPane = new VBox(20);
        mainPane.setPadding(new Insets(20));
        mainPane.setAlignment(Pos.CENTER);
        
        TicTacToeGame game = new TicTacToeGame();
        mainPane.getChildren().addAll(
            new Label("Tic-Tac-Toe Game"),
            game.getGamePane()
        );
        
        tab.setContent(mainPane);
        return tab;
    }
    
    // Helper methods for creating UI sections
    
    private VBox createLabelSection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(10));
        section.getChildren().add(new Label("Labels with Graphics:"));
        
        HBox labelPane = new HBox(20);
        labelPane.setAlignment(Pos.CENTER);
        
        // Label with circle
        Label circleLabel = new Label("Circle", new Circle(25, Color.LIGHTBLUE));
        circleLabel.setContentDisplay(ContentDisplay.TOP);
        
        // Label with rectangle
        Label rectLabel = new Label("Rectangle", new Rectangle(50, 30, Color.LIGHTGREEN));
        rectLabel.setContentDisplay(ContentDisplay.BOTTOM);
        
        // Label with ellipse
        Ellipse ellipse = new Ellipse(40, 25);
        ellipse.setFill(Color.LIGHTYELLOW);
        ellipse.setStroke(Color.ORANGE);
        Label ellipseLabel = new Label("Ellipse", ellipse);
        ellipseLabel.setContentDisplay(ContentDisplay.LEFT);
        
        labelPane.getChildren().addAll(circleLabel, rectLabel, ellipseLabel);
        section.getChildren().add(labelPane);
        
        return section;
    }
    
    private VBox createRadioButtonSection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(10));
        section.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        section.getChildren().add(new Label("Text Color:"));
        
        ToggleGroup colorGroup = new ToggleGroup();
        
        RadioButton blackRadio = new RadioButton("Black");
        RadioButton redRadio = new RadioButton("Red");
        RadioButton blueRadio = new RadioButton("Blue");
        RadioButton greenRadio = new RadioButton("Green");
        
        blackRadio.setToggleGroup(colorGroup);
        redRadio.setToggleGroup(colorGroup);
        blueRadio.setToggleGroup(colorGroup);
        greenRadio.setToggleGroup(colorGroup);
        
        blackRadio.setSelected(true);
        
        // Event handlers
        blackRadio.setOnAction(_ -> displayText.setFill(Color.BLACK));
        redRadio.setOnAction(_ -> displayText.setFill(Color.RED));
        blueRadio.setOnAction(_ -> displayText.setFill(Color.BLUE));
        greenRadio.setOnAction(_ -> displayText.setFill(Color.GREEN));
        
        section.getChildren().addAll(blackRadio, redRadio, blueRadio, greenRadio);
        return section;
    }
    
    private VBox createCheckBoxSection() {
        VBox section = new VBox(10);
        section.setPadding(new Insets(10));
        section.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        section.getChildren().add(new Label("Text Style:"));
        
        CheckBox boldCheckBox = new CheckBox("Bold");
        CheckBox italicCheckBox = new CheckBox("Italic");
        
        // Event handler to update font based on checkbox states
        Runnable updateFont = () -> {
            FontWeight weight = boldCheckBox.isSelected() ? FontWeight.BOLD : FontWeight.NORMAL;
            FontPosture posture = italicCheckBox.isSelected() ? FontPosture.ITALIC : FontPosture.REGULAR;
            displayText.setFont(Font.font(displayText.getFont().getFamily(), weight, posture, displayText.getFont().getSize()));
        };
        
        boldCheckBox.setOnAction(_ -> updateFont.run());
        italicCheckBox.setOnAction(_ -> updateFont.run());
        
        section.getChildren().addAll(boldCheckBox, italicCheckBox);
        return section;
    }
    
    private HBox createButtonSection() {
        HBox section = new HBox(20);
        section.setPadding(new Insets(10));
        section.setAlignment(Pos.CENTER);
        section.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        
        // Create buttons with icons (using shapes as icons)
        Button leftButton = new Button("Left", new Rectangle(15, 10, Color.GRAY));
        Button rightButton = new Button("Right", new Rectangle(15, 10, Color.GRAY));
        Button upButton = new Button("Up", new Rectangle(10, 15, Color.GRAY));
        Button downButton = new Button("Down", new Rectangle(10, 15, Color.GRAY));
        
        // Event handlers for text movement
        leftButton.setOnAction(_ -> displayText.setX(Math.max(0, displayText.getX() - 10)));
        rightButton.setOnAction(_ -> displayText.setX(displayText.getX() + 10));
        upButton.setOnAction(_ -> displayText.setY(Math.max(20, displayText.getY() - 10)));
        downButton.setOnAction(_ -> displayText.setY(displayText.getY() + 10));
        
        section.getChildren().addAll(leftButton, rightButton, upButton, downButton);
        return section;
    }
    
    private HBox createMediaControls() {
        VBox controlsContainer = new VBox(10);
        controlsContainer.setAlignment(Pos.CENTER);
        
        // Main controls
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);
        
        Button playButton = new Button("▶");
        Button pauseButton = new Button("⏸");
        Button stopButton = new Button("⏹");
        Button rewindButton = new Button("⏪");
        
        // Style the buttons
        playButton.setStyle("-fx-font-size: 16px; -fx-padding: 5 10 5 10;");
        pauseButton.setStyle("-fx-font-size: 16px; -fx-padding: 5 10 5 10;");
        stopButton.setStyle("-fx-font-size: 16px; -fx-padding: 5 10 5 10;");
        rewindButton.setStyle("-fx-font-size: 16px; -fx-padding: 5 10 5 10;");
        
        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setPrefWidth(100);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(25);
        
        // Status label
        Label statusLabel = new Label("Ready to play");
        statusLabel.setStyle("-fx-text-fill: blue;");
        
        // Event handlers
        playButton.setOnAction(_ -> {
            if (mediaPlayer != null) {
                mediaPlayer.play();
                statusLabel.setText("Playing...");
                statusLabel.setStyle("-fx-text-fill: green;");
            }
        });
        
        pauseButton.setOnAction(_ -> {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                statusLabel.setText("Paused");
                statusLabel.setStyle("-fx-text-fill: orange;");
            }
        });
        
        stopButton.setOnAction(_ -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                statusLabel.setText("Stopped");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        });
        
        rewindButton.setOnAction(_ -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.ZERO);
                statusLabel.setText("Rewound to start");
                statusLabel.setStyle("-fx-text-fill: blue;");
            }
        });
        
        volumeSlider.valueProperty().addListener((_, _, newVal) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newVal.doubleValue() / 100.0);
            }
        });
        
        // Add status listener to media player
        if (mediaPlayer != null) {
            mediaPlayer.setOnReady(() -> {
                statusLabel.setText("Media loaded successfully");
                statusLabel.setStyle("-fx-text-fill: green;");
            });
            
            mediaPlayer.setOnError(() -> {
                statusLabel.setText("Error playing media");
                statusLabel.setStyle("-fx-text-fill: red;");
            });
            
            mediaPlayer.setOnEndOfMedia(() -> {
                statusLabel.setText("Playback finished");
                statusLabel.setStyle("-fx-text-fill: blue;");
            });
        }
        
        controls.getChildren().addAll(
            playButton, pauseButton, stopButton, rewindButton,
            new Label("Volume:"), volumeSlider
        );
        
        controlsContainer.getChildren().addAll(controls, statusLabel);
        
        // Return the container wrapped in an HBox for compatibility
        HBox wrapper = new HBox();
        wrapper.setAlignment(Pos.CENTER);
        wrapper.getChildren().add(controlsContainer);
        return wrapper;
    }
    
    private VBox createAnimationDemo() {
        VBox demo = new VBox(20);
        demo.setAlignment(Pos.CENTER);
        
        demo.getChildren().add(new Label("Animation Demo (Fallback)"));
        
        // Create animated shapes
        Circle animatedCircle = new Circle(30, Color.LIGHTBLUE);
        Rectangle animatedRect = new Rectangle(60, 40, Color.LIGHTGREEN);
        
        HBox shapesPane = new HBox(20);
        shapesPane.setAlignment(Pos.CENTER);
        shapesPane.getChildren().addAll(animatedCircle, animatedRect);
        
        Button animateButton = new Button("Animate Colors");
        animateButton.setOnAction(_ -> {
            // Simple color animation
            animatedCircle.setFill(animatedCircle.getFill() == Color.LIGHTBLUE ? Color.ORANGE : Color.LIGHTBLUE);
            animatedRect.setFill(animatedRect.getFill() == Color.LIGHTGREEN ? Color.PINK : Color.LIGHTGREEN);
        });
        
        demo.getChildren().addAll(shapesPane, animateButton);
        return demo;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
