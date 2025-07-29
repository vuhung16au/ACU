package com.acu.javafx.shape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Demonstrates the use of Rectangle shapes in JavaFX.
 * Creates multiple rectangles with different styles and colors.
 * Based on the example from Pearson's Java Programming textbook.
 */
public class ShowRectangle extends Pane {
    
    public ShowRectangle() {
        paint();
    }
    
    /**
     * Creates and displays multiple rectangles with different styles.
     */
    private void paint() {
        getChildren().clear();
        
        // Create rectangles with different properties
        Rectangle rect1 = new Rectangle(25, 10, 60, 30);
        rect1.setStrokeWidth(1);
        rect1.setStroke(Color.BLACK);
        rect1.setFill(Color.WHITE);
        getChildren().add(rect1);
        
        Rectangle rect2 = new Rectangle(25, 50, 60, 30);
        rect2.setStrokeWidth(1);
        rect2.setStroke(Color.BLACK);
        rect2.setFill(Color.RED);
        getChildren().add(rect2);
        
        Rectangle rect3 = new Rectangle(25, 90, 60, 30);
        rect3.setStrokeWidth(1);
        rect3.setStroke(Color.BLACK);
        rect3.setFill(Color.BLUE);
        getChildren().add(rect3);
        
        Rectangle rect4 = new Rectangle(25, 130, 60, 30);
        rect4.setStrokeWidth(1);
        rect4.setStroke(Color.BLACK);
        rect4.setFill(Color.GREEN);
        getChildren().add(rect4);
        
        Rectangle rect5 = new Rectangle(25, 170, 60, 30);
        rect5.setStrokeWidth(1);
        rect5.setStroke(Color.BLACK);
        rect5.setFill(Color.YELLOW);
        getChildren().add(rect5);
        
        Rectangle rect6 = new Rectangle(25, 210, 60, 30);
        rect6.setStrokeWidth(1);
        rect6.setStroke(Color.BLACK);
        rect6.setFill(Color.ORANGE);
        getChildren().add(rect6);
        
        Rectangle rect7 = new Rectangle(25, 250, 60, 30);
        rect7.setStrokeWidth(1);
        rect7.setStroke(Color.BLACK);
        rect7.setFill(Color.PURPLE);
        getChildren().add(rect7);
        
        Rectangle rect8 = new Rectangle(25, 290, 60, 30);
        rect8.setStrokeWidth(1);
        rect8.setStroke(Color.BLACK);
        rect8.setFill(Color.PINK);
        getChildren().add(rect8);
    }
    
    @Override
    protected void setWidth(double value) {
        super.setWidth(value);
        paint();
    }
    
    @Override
    protected void setHeight(double value) {
        super.setHeight(value);
        paint();
    }
} 