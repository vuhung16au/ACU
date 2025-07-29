package com.acu.javafx.shape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

/**
 * Demonstrates the use of Arc shapes in JavaFX.
 * Creates multiple arcs with different types and styles.
 * Based on the example from Pearson's Java Programming textbook.
 */
public class ShowArc extends Pane {
    
    public ShowArc() {
        paint();
    }
    
    /**
     * Creates and displays multiple arcs with different properties.
     */
    private void paint() {
        getChildren().clear();
        
        // Create arcs with different types and properties
        Arc arc1 = new Arc(100, 100, 80, 80, 0, 90);
        arc1.setFill(Color.RED);
        arc1.setType(ArcType.ROUND);
        getChildren().add(arc1);
        
        Arc arc2 = new Arc(100, 100, 80, 80, 90, 90);
        arc2.setFill(Color.BLUE);
        arc2.setType(ArcType.ROUND);
        getChildren().add(arc2);
        
        Arc arc3 = new Arc(100, 100, 80, 80, 180, 90);
        arc3.setFill(Color.GREEN);
        arc3.setType(ArcType.ROUND);
        getChildren().add(arc3);
        
        Arc arc4 = new Arc(100, 100, 80, 80, 270, 90);
        arc4.setFill(Color.YELLOW);
        arc4.setType(ArcType.ROUND);
        getChildren().add(arc4);
        
        // Create chord arcs
        Arc arc5 = new Arc(300, 100, 80, 80, 0, 90);
        arc5.setFill(Color.ORANGE);
        arc5.setType(ArcType.CHORD);
        getChildren().add(arc5);
        
        Arc arc6 = new Arc(300, 100, 80, 80, 90, 90);
        arc6.setFill(Color.PURPLE);
        arc6.setType(ArcType.CHORD);
        getChildren().add(arc6);
        
        Arc arc7 = new Arc(300, 100, 80, 80, 180, 90);
        arc7.setFill(Color.PINK);
        arc7.setType(ArcType.CHORD);
        getChildren().add(arc7);
        
        Arc arc8 = new Arc(300, 100, 80, 80, 270, 90);
        arc8.setFill(Color.CYAN);
        arc8.setType(ArcType.CHORD);
        getChildren().add(arc8);
        
        // Create open arcs
        Arc arc9 = new Arc(500, 100, 80, 80, 0, 90);
        arc9.setFill(Color.TRANSPARENT);
        arc9.setStroke(Color.BLACK);
        arc9.setStrokeWidth(3);
        arc9.setType(ArcType.OPEN);
        getChildren().add(arc9);
        
        Arc arc10 = new Arc(500, 100, 80, 80, 90, 90);
        arc10.setFill(Color.TRANSPARENT);
        arc10.setStroke(Color.RED);
        arc10.setStrokeWidth(3);
        arc10.setType(ArcType.OPEN);
        getChildren().add(arc10);
        
        Arc arc11 = new Arc(500, 100, 80, 80, 180, 90);
        arc11.setFill(Color.TRANSPARENT);
        arc11.setStroke(Color.BLUE);
        arc11.setStrokeWidth(3);
        arc11.setType(ArcType.OPEN);
        getChildren().add(arc11);
        
        Arc arc12 = new Arc(500, 100, 80, 80, 270, 90);
        arc12.setFill(Color.TRANSPARENT);
        arc12.setStroke(Color.GREEN);
        arc12.setStrokeWidth(3);
        arc12.setType(ArcType.OPEN);
        getChildren().add(arc12);
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