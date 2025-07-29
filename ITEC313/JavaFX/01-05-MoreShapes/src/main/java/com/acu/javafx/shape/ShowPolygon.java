package com.acu.javafx.shape;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Demonstrates the use of Polygon shapes in JavaFX.
 * Creates multiple polygons with different configurations.
 * Based on the example from Pearson's Java Programming textbook.
 */
public class ShowPolygon extends Pane {
    
    public ShowPolygon() {
        paint();
    }
    
    /**
     * Creates and displays multiple polygons with different properties.
     */
    private void paint() {
        getChildren().clear();
        
        // Create a triangle
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(new Double[]{
            50.0, 50.0,
            100.0, 50.0,
            75.0, 100.0
        });
        triangle.setFill(Color.RED);
        triangle.setStroke(Color.BLACK);
        triangle.setStrokeWidth(2);
        getChildren().add(triangle);
        
        // Create a diamond
        Polygon diamond = new Polygon();
        diamond.getPoints().addAll(new Double[]{
            200.0, 50.0,
            250.0, 75.0,
            200.0, 100.0,
            150.0, 75.0
        });
        diamond.setFill(Color.BLUE);
        diamond.setStroke(Color.BLACK);
        diamond.setStrokeWidth(2);
        getChildren().add(diamond);
        
        // Create a hexagon
        Polygon hexagon = new Polygon();
        double centerX = 400;
        double centerY = 75;
        double radius = 30;
        for (int i = 0; i < 6; i++) {
            double angle = i * Math.PI / 3;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            hexagon.getPoints().addAll(x, y);
        }
        hexagon.setFill(Color.GREEN);
        hexagon.setStroke(Color.BLACK);
        hexagon.setStrokeWidth(2);
        getChildren().add(hexagon);
        
        // Create a star
        Polygon star = new Polygon();
        double starCenterX = 550;
        double starCenterY = 75;
        double outerRadius = 30;
        double innerRadius = 15;
        for (int i = 0; i < 10; i++) {
            double angle = i * Math.PI / 5;
            double starRadius = (i % 2 == 0) ? outerRadius : innerRadius;
            double x = starCenterX + starRadius * Math.cos(angle);
            double y = starCenterY + starRadius * Math.sin(angle);
            star.getPoints().addAll(x, y);
        }
        star.setFill(Color.YELLOW);
        star.setStroke(Color.BLACK);
        star.setStrokeWidth(2);
        getChildren().add(star);
        
        // Create a pentagon
        Polygon pentagon = new Polygon();
        double pentCenterX = 100;
        double pentCenterY = 200;
        double pentRadius = 30;
        for (int i = 0; i < 5; i++) {
            double angle = i * 2 * Math.PI / 5 - Math.PI / 2;
            double x = pentCenterX + pentRadius * Math.cos(angle);
            double y = pentCenterY + pentRadius * Math.sin(angle);
            pentagon.getPoints().addAll(x, y);
        }
        pentagon.setFill(Color.ORANGE);
        pentagon.setStroke(Color.BLACK);
        pentagon.setStrokeWidth(2);
        getChildren().add(pentagon);
        
        // Create an octagon
        Polygon octagon = new Polygon();
        double octCenterX = 250;
        double octCenterY = 200;
        double octRadius = 30;
        for (int i = 0; i < 8; i++) {
            double angle = i * Math.PI / 4;
            double x = octCenterX + octRadius * Math.cos(angle);
            double y = octCenterY + octRadius * Math.sin(angle);
            octagon.getPoints().addAll(x, y);
        }
        octagon.setFill(Color.PURPLE);
        octagon.setStroke(Color.BLACK);
        octagon.setStrokeWidth(2);
        getChildren().add(octagon);
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