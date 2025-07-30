package com.acu.javafx.avltrees;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class AVLTreeDemo extends Application {
    private AVLTree<Integer> tree;
    private Canvas canvas;
    private GraphicsContext gc;
    private TextField inputField;
    private TextArea outputArea;
    private VBox treePane;
    
    private static final double NODE_RADIUS = 25;
    private static final double LEVEL_HEIGHT = 80;
    private static final double MIN_NODE_DISTANCE = 60;

    @Override
    public void start(Stage primaryStage) {
        tree = new AVLTree<>();
        
        // Create the main layout
        BorderPane root = new BorderPane();
        
        // Create controls panel
        VBox controlsPanel = createControlsPanel();
        root.setLeft(controlsPanel);
        
        // Create tree visualization panel
        treePane = new VBox(10);
        treePane.setPadding(new Insets(10));
        treePane.setAlignment(Pos.CENTER);
        
        // Create canvas for tree drawing
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        
        // Create a pane to hold the canvas
        Pane canvasPane = new Pane(canvas);
        canvasPane.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        
        treePane.getChildren().addAll(
            new Label("AVL Tree Visualization"),
            canvasPane
        );
        
        root.setCenter(treePane);
        
        // Create output panel
        VBox outputPanel = createOutputPanel();
        root.setRight(outputPanel);
        
        // Set up the scene
        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setTitle("AVL Tree Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Initial tree drawing
        drawTree();
    }
    
    private VBox createControlsPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setPrefWidth(200);
        panel.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        
        Label titleLabel = new Label("AVL Tree Controls");
        titleLabel.setFont(Font.font(16));
        
        inputField = new TextField();
        inputField.setPromptText("Enter a number");
        inputField.setOnAction(e -> insertNode());
        
        Button insertBtn = new Button("Insert");
        insertBtn.setOnAction(e -> insertNode());
        
        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> deleteNode());
        
        Button clearBtn = new Button("Clear Tree");
        clearBtn.setOnAction(e -> clearTree());
        
        Button demoBtn = new Button("Run Demo");
        demoBtn.setOnAction(e -> runDemo());
        
        VBox buttonBox = new VBox(5);
        buttonBox.getChildren().addAll(insertBtn, deleteBtn, clearBtn, demoBtn);
        
        panel.getChildren().addAll(titleLabel, inputField, buttonBox);
        
        return panel;
    }
    
    private VBox createOutputPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setPrefWidth(250);
        panel.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        
        Label titleLabel = new Label("Tree Information");
        titleLabel.setFont(Font.font(16));
        
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(20);
        outputArea.setPrefColumnCount(30);
        
        panel.getChildren().addAll(titleLabel, outputArea);
        
        return panel;
    }
    
    private void insertNode() {
        try {
            String input = inputField.getText().trim();
            if (!input.isEmpty()) {
                int value = Integer.parseInt(input);
                boolean success = tree.insert(value);
                if (success) {
                    updateOutput("Inserted: " + value);
                    inputField.clear();
                } else {
                    updateOutput("Failed to insert: " + value + " (duplicate)");
                }
                drawTree();
            }
        } catch (NumberFormatException e) {
            updateOutput("Please enter a valid number");
        }
    }
    
    private void deleteNode() {
        try {
            String input = inputField.getText().trim();
            if (!input.isEmpty()) {
                int value = Integer.parseInt(input);
                boolean success = tree.delete(value);
                if (success) {
                    updateOutput("Deleted: " + value);
                    inputField.clear();
                } else {
                    updateOutput("Failed to delete: " + value + " (not found)");
                }
                drawTree();
            }
        } catch (NumberFormatException e) {
            updateOutput("Please enter a valid number");
        }
    }
    
    private void clearTree() {
        tree.clear();
        updateOutput("Tree cleared");
        drawTree();
    }
    
    private void runDemo() {
        // Clear the tree first
        tree.clear();
        
        // Run the demo sequence
        int[] demoValues = {25, 20, 5, 34, 50, 30, 10};
        
        for (int value : demoValues) {
            tree.insert(value);
            updateOutput("Demo: Inserted " + value);
            drawTree();
            
            // Add a small delay for visualization
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        updateOutput("Demo completed");
    }
    
    private void updateOutput(String message) {
        outputArea.appendText(message + "\n");
        
        // Update tree information
        StringBuilder info = new StringBuilder();
        info.append("Tree Size: ").append(tree.getSize()).append("\n");
        info.append("Tree Height: ").append(getTreeHeight(tree.getRoot())).append("\n");
        info.append("Is Empty: ").append(tree.isEmpty()).append("\n\n");
        
        info.append("Inorder: ");
        // We'll need to implement a way to get the inorder traversal as a string
        // For now, we'll just show the size
        
        outputArea.setText(info.toString());
    }
    
    private int getTreeHeight(BST.TreeNode<Integer> node) {
        if (node == null) return -1;
        return 1 + Math.max(getTreeHeight(node.left), getTreeHeight(node.right));
    }
    
    private void drawTree() {
        // Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        if (tree.isEmpty()) {
            gc.setFont(Font.font(16));
            gc.fillText("Tree is empty", canvas.getWidth() / 2 - 50, canvas.getHeight() / 2);
            return;
        }
        
        // Calculate tree layout
        TreeLayout layout = calculateTreeLayout(tree.getRoot(), canvas.getWidth() / 2, 50, canvas.getWidth() / 4);
        
        // Draw the tree
        drawTreeNodes(layout);
    }
    
    private TreeLayout calculateTreeLayout(BST.TreeNode<Integer> node, double x, double y, double width) {
        if (node == null) return null;
        
        TreeLayout layout = new TreeLayout();
        layout.node = node;
        layout.x = x;
        layout.y = y;
        
        if (node.left != null) {
            layout.left = calculateTreeLayout(node.left, x - width, y + LEVEL_HEIGHT, width / 2);
        }
        
        if (node.right != null) {
            layout.right = calculateTreeLayout(node.right, x + width, y + LEVEL_HEIGHT, width / 2);
        }
        
        return layout;
    }
    
    private void drawTreeNodes(TreeLayout layout) {
        if (layout == null) return;
        
        // Draw connections to children
        if (layout.left != null) {
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeLine(layout.x, layout.y, layout.left.x, layout.left.y);
        }
        
        if (layout.right != null) {
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(2);
            gc.strokeLine(layout.x, layout.y, layout.right.x, layout.right.y);
        }
        
        // Draw the node
        gc.setFill(Color.LIGHTBLUE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.fillOval(layout.x - NODE_RADIUS, layout.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        gc.strokeOval(layout.x - NODE_RADIUS, layout.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        
        // Draw the node value
        gc.setFill(Color.BLACK);
        gc.setFont(Font.font(14));
        String value = String.valueOf(layout.node.element);
        double textWidth = gc.getFont().getSize() * value.length() * 0.6;
        gc.fillText(value, layout.x - textWidth / 2, layout.y + 5);
        
        // Draw height information for AVL nodes
        if (layout.node instanceof AVLTree.AVLTreeNode) {
            AVLTree.AVLTreeNode<Integer> avlNode = (AVLTree.AVLTreeNode<Integer>) layout.node;
            gc.setFill(Color.RED);
            gc.setFont(Font.font(10));
            gc.fillText("h=" + avlNode.height, layout.x - 15, layout.y + NODE_RADIUS + 15);
        }
        
        // Recursively draw children
        drawTreeNodes(layout.left);
        drawTreeNodes(layout.right);
    }
    
    private static class TreeLayout {
        BST.TreeNode<Integer> node;
        double x, y;
        TreeLayout left, right;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
} 