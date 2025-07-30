package com.acu.javafx.huffmancode;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class HuffmanCodeDemo extends Application {

    private TextArea inputTextArea;
    private TableView<HuffmanData> resultTable;
    private Label summaryLabel;
    private TextArea encodedTextArea;
    private TextArea decodedTextArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Huffman Code Demo");

        // Create main layout
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // Title
        Label titleLabel = new Label("Huffman Code Algorithm Demo");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKBLUE);

        // Input section
        VBox inputSection = createInputSection();
        
        // Process button
        Button processButton = new Button("Generate Huffman Codes");
        processButton.setStyle("-fx-font-size: 14px; -fx-padding: 10px 20px;");
        processButton.setOnAction(e -> processHuffmanCode());

        // Results section
        VBox resultsSection = createResultsSection();

        // Add all sections to main layout
        mainLayout.getChildren().addAll(
            titleLabel,
            inputSection,
            processButton,
            resultsSection
        );

        // Create scroll pane for the main layout
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 1000, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createInputSection() {
        VBox inputSection = new VBox(10);
        inputSection.setAlignment(Pos.TOP_LEFT);

        Label inputLabel = new Label("Enter text to encode:");
        inputLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        inputTextArea = new TextArea();
        inputTextArea.setPromptText("Enter your text here...");
        inputTextArea.setPrefRowCount(4);
        inputTextArea.setWrapText(true);

        inputSection.getChildren().addAll(inputLabel, inputTextArea);
        return inputSection;
    }

    private VBox createResultsSection() {
        VBox resultsSection = new VBox(15);
        resultsSection.setAlignment(Pos.TOP_LEFT);

        // Huffman codes table
        Label tableLabel = new Label("Huffman Codes:");
        tableLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        resultTable = new TableView<>();
        resultTable.setPrefHeight(200);

        // Create table columns
        TableColumn<HuffmanData, Integer> asciiCol = new TableColumn<>("ASCII");
        asciiCol.setCellValueFactory(new PropertyValueFactory<>("asciiCode"));
        asciiCol.setPrefWidth(80);

        TableColumn<HuffmanData, String> charCol = new TableColumn<>("Character");
        charCol.setCellValueFactory(new PropertyValueFactory<>("character"));
        charCol.setPrefWidth(100);

        TableColumn<HuffmanData, Integer> freqCol = new TableColumn<>("Frequency");
        freqCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        freqCol.setPrefWidth(100);

        TableColumn<HuffmanData, String> codeCol = new TableColumn<>("Huffman Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeCol.setPrefWidth(150);

        resultTable.getColumns().addAll(asciiCol, charCol, freqCol, codeCol);

        // Summary label
        summaryLabel = new Label();
        summaryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        summaryLabel.setTextFill(Color.DARKGREEN);

        // Encoded text section
        Label encodedLabel = new Label("Encoded Text:");
        encodedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        encodedTextArea = new TextArea();
        encodedTextArea.setEditable(false);
        encodedTextArea.setPrefRowCount(3);
        encodedTextArea.setWrapText(true);

        // Decoded text section
        Label decodedLabel = new Label("Decoded Text:");
        decodedLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        decodedTextArea = new TextArea();
        decodedTextArea.setEditable(false);
        decodedTextArea.setPrefRowCount(3);
        decodedTextArea.setWrapText(true);

        resultsSection.getChildren().addAll(
            tableLabel, resultTable, summaryLabel,
            encodedLabel, encodedTextArea,
            decodedLabel, decodedTextArea
        );

        return resultsSection;
    }

    private void processHuffmanCode() {
        String text = inputTextArea.getText().trim();
        
        if (text.isEmpty()) {
            showAlert("Input Error", "Please enter some text to process.");
            return;
        }

        try {
            // Get character frequencies
            int[] counts = HuffmanCode.getCharacterFrequency(text);
            
            // Create Huffman tree and get codes
            HuffmanCode.Tree tree = HuffmanCode.getHuffmanTree(counts);
            String[] codes = HuffmanCode.getCode(tree.root);

            // Populate table with results
            ObservableList<HuffmanData> data = FXCollections.observableArrayList();
            int totalCharacters = 0;
            int totalBits = 0;

            for (int i = 0; i < codes.length; i++) {
                if (counts[i] != 0) {
                    HuffmanData huffmanData = new HuffmanData(
                        i, 
                        String.valueOf((char)i), 
                        counts[i], 
                        codes[i]
                    );
                    data.add(huffmanData);
                    totalCharacters += counts[i];
                    totalBits += counts[i] * codes[i].length();
                }
            }

            resultTable.setItems(data);

            // Calculate and display summary
            int originalBits = totalCharacters * 8; // ASCII uses 8 bits per character
            double compressionRatio = (double) totalBits / originalBits * 100;
            
            summaryLabel.setText(String.format(
                "Summary: %d characters, %d bits (original: %d bits, compression: %.1f%%)",
                totalCharacters, totalBits, originalBits, compressionRatio
            ));

            // Encode the input text
            StringBuilder encoded = new StringBuilder();
            for (char c : text.toCharArray()) {
                if (codes[c] != null) {
                    encoded.append(codes[c]);
                }
            }
            encodedTextArea.setText(encoded.toString());

            // Decode the encoded text (for demonstration)
            decodedTextArea.setText(decodeText(encoded.toString(), codes));

        } catch (Exception e) {
            showAlert("Error", "An error occurred while processing: " + e.getMessage());
        }
    }

    private String decodeText(String encodedText, String[] codes) {
        // Create reverse mapping from code to character
        java.util.Map<String, Character> codeToChar = new java.util.HashMap<>();
        for (int i = 0; i < codes.length; i++) {
            if (codes[i] != null) {
                codeToChar.put(codes[i], (char)i);
            }
        }

        StringBuilder decoded = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();
        
        for (char bit : encodedText.toCharArray()) {
            currentCode.append(bit);
            String code = currentCode.toString();
            
            if (codeToChar.containsKey(code)) {
                decoded.append(codeToChar.get(code));
                currentCode.setLength(0); // Reset for next code
            }
        }
        
        return decoded.toString();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Data class for table
    public static class HuffmanData {
        private final int asciiCode;
        private final String character;
        private final int frequency;
        private final String code;

        public HuffmanData(int asciiCode, String character, int frequency, String code) {
            this.asciiCode = asciiCode;
            this.character = character;
            this.frequency = frequency;
            this.code = code;
        }

        public int getAsciiCode() { return asciiCode; }
        public String getCharacter() { return character; }
        public int getFrequency() { return frequency; }
        public String getCode() { return code; }
    }
} 