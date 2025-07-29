package com.acu.javafx.generics;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main JavaFX application demonstrating Java generics concepts.
 * This application showcases various generics examples including:
 * - GenericStack
 * - Wildcard demonstrations
 * - ArrayList with generics
 * - Generic Matrix operations
 */
public class GenericsDemoApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Generics Demo");

        // Create tab pane for different demonstrations
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Add tabs for different generics demonstrations
        tabPane.getTabs().addAll(
            createGenericStackTab(),
            createWildcardNeedTab(),
            createAnyWildcardTab(),
            createSuperWildcardTab(),
            createArrayListTab(),
            createGenericMatrixTab()
        );

        BorderPane root = new BorderPane();
        root.setCenter(tabPane);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Tab createGenericStackTab() {
        Tab tab = new Tab("Generic Stack");
        tab.setClosable(false);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(20);
        
        Button runButton = new Button("Run Generic Stack Demo");
        runButton.setOnAction(e -> {
            outputArea.clear();
            GenericStackDemo.runDemo(outputArea);
        });
        
        content.getChildren().addAll(
            new Label("Generic Stack Demonstration"),
            new Label("This demonstrates a generic stack implementation that can work with any type."),
            runButton,
            outputArea
        );
        
        tab.setContent(content);
        return tab;
    }

    private Tab createWildcardNeedTab() {
        Tab tab = new Tab("Wildcard Need Demo");
        tab.setClosable(false);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(20);
        
        Button runButton = new Button("Run Wildcard Need Demo");
        runButton.setOnAction(e -> {
            outputArea.clear();
            WildCardNeedDemo.runDemo(outputArea);
        });
        
        content.getChildren().addAll(
            new Label("Wildcard Need Demonstration"),
            new Label("This shows why wildcards are needed in generic programming."),
            runButton,
            outputArea
        );
        
        tab.setContent(content);
        return tab;
    }

    private Tab createAnyWildcardTab() {
        Tab tab = new Tab("Any Wildcard Demo");
        tab.setClosable(false);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(20);
        
        Button runButton = new Button("Run Any Wildcard Demo");
        runButton.setOnAction(e -> {
            outputArea.clear();
            AnyWildCardDemo.runDemo(outputArea);
        });
        
        content.getChildren().addAll(
            new Label("Any Wildcard (?) Demonstration"),
            new Label("This demonstrates the unbounded wildcard usage."),
            runButton,
            outputArea
        );
        
        tab.setContent(content);
        return tab;
    }

    private Tab createSuperWildcardTab() {
        Tab tab = new Tab("Super Wildcard Demo");
        tab.setClosable(false);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(20);
        
        Button runButton = new Button("Run Super Wildcard Demo");
        runButton.setOnAction(e -> {
            outputArea.clear();
            SuperWildCardDemo.runDemo(outputArea);
        });
        
        content.getChildren().addAll(
            new Label("Super Wildcard (? super T) Demonstration"),
            new Label("This demonstrates the lower bounded wildcard usage."),
            runButton,
            outputArea
        );
        
        tab.setContent(content);
        return tab;
    }

    private Tab createArrayListTab() {
        Tab tab = new Tab("ArrayList with Generics");
        tab.setClosable(false);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(20);
        
        Button runButton = new Button("Run ArrayList Demo");
        runButton.setOnAction(e -> {
            outputArea.clear();
            TestArrayListNew.runDemo(outputArea);
        });
        
        content.getChildren().addAll(
            new Label("ArrayList with Generics Demonstration"),
            new Label("This shows how ArrayList works with generics and type safety."),
            runButton,
            outputArea
        );
        
        tab.setContent(content);
        return tab;
    }

    private Tab createGenericMatrixTab() {
        Tab tab = new Tab("Generic Matrix Operations");
        tab.setClosable(false);
        
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(25);
        
        Button runIntegerButton = new Button("Run Integer Matrix Demo");
        Button runRationalButton = new Button("Run Rational Matrix Demo");
        
        runIntegerButton.setOnAction(e -> {
            outputArea.clear();
            TestIntegerMatrix.runDemo(outputArea);
        });
        
        runRationalButton.setOnAction(e -> {
            outputArea.clear();
            TestRationalMatrix.runDemo(outputArea);
        });
        
        content.getChildren().addAll(
            new Label("Generic Matrix Operations Demonstration"),
            new Label("This demonstrates generic matrix operations with different number types."),
            runIntegerButton,
            runRationalButton,
            outputArea
        );
        
        tab.setContent(content);
        return tab;
    }
} 