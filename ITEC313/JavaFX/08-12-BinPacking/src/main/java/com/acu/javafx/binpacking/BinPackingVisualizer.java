package com.acu.javafx.binpacking;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.List;

/**
 * Visualizer for bin packing solutions using JavaFX Canvas
 * 
 * This class provides methods to draw bins and items in a visual representation
 * that helps users understand the bin packing solution.
 */
public class BinPackingVisualizer {
    
    private static final int BIN_WIDTH = 180;
    private static final int BIN_HEIGHT = 100;
    private static final int ITEM_HEIGHT = 18;
    private static final int MARGIN = 25;
    private static final int SPACING = 30;
    
    private final Canvas canvas;
    private final GraphicsContext gc;
    
    // Color palette for different items
    private static final Color[] ITEM_COLORS = {
        Color.LIGHTBLUE, Color.LIGHTGREEN, Color.LIGHTCORAL, Color.LIGHTYELLOW,
        Color.LIGHTGRAY, Color.LIGHTPINK, Color.LIGHTSEAGREEN, Color.LIGHTSTEELBLUE,
        Color.LIGHTGOLDENRODYELLOW, Color.LIGHTGREEN
    };
    
    public BinPackingVisualizer(Canvas canvas) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }
    
    /**
     * Draw the bin packing solution
     * @param result the bin packing result to visualize
     */
    public void drawSolution(BinPackingAlgorithm.BinPackingResult result) {
        clearCanvas();
        
        List<BinPackingAlgorithm.Bin> bins = result.getBins();
        if (bins.isEmpty()) {
            drawNoSolution();
            return;
        }
        
        // Calculate layout - show max 3 bins per row
        int binsPerRow = Math.min(3, bins.size()); // Max 3 bins per row
        int rows = (bins.size() + binsPerRow - 1) / binsPerRow;
        
        // Calculate starting position to center the visualization
        double totalWidth = binsPerRow * (BIN_WIDTH + SPACING) - SPACING;
        double totalHeight = rows * (BIN_HEIGHT + SPACING) - SPACING;
        
        double startX = Math.max(MARGIN, (canvas.getWidth() - totalWidth) / 2);
        double startY = Math.max(MARGIN + 60, (canvas.getHeight() - totalHeight) / 2); // Leave more space for statistics and labels
        
        // Draw bins
        for (int i = 0; i < bins.size(); i++) {
            int row = i / binsPerRow;
            int col = i % binsPerRow;
            
            double x = startX + col * (BIN_WIDTH + SPACING);
            double y = startY + row * (BIN_HEIGHT + SPACING);
            
            drawBin(bins.get(i), x, y, i + 1);
        }
        
        // Draw unplaced items if any - positioned below all bins with more space
        if (!result.getUnplacedItems().isEmpty()) {
            drawUnplacedItems(result.getUnplacedItems(), startY + rows * (BIN_HEIGHT + SPACING) + 50);
        }
        
        // Draw statistics - positioned at top with more space
        drawStatistics(result, 10, 25);
    }
    
    /**
     * Draw a single bin with its items
     */
    private void drawBin(BinPackingAlgorithm.Bin bin, double x, double y, int binNumber) {
        // Draw bin outline
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeRect(x, y, BIN_WIDTH, BIN_HEIGHT);
        
        // Draw bin label - positioned well above the bin
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 12));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Bin " + binNumber, x + BIN_WIDTH / 2, y - 15);
        
        // Capacity information removed per user request
        
        // Draw items with better spacing
        List<BinPackingAlgorithm.Item> items = bin.getItems();
        double itemY = y + 8; // Start further from top
        double itemSpacing = 3; // More space between items
        
        for (int i = 0; i < items.size(); i++) {
            BinPackingAlgorithm.Item item = items.get(i);
            Color itemColor = ITEM_COLORS[i % ITEM_COLORS.length];
            
            // Calculate item width based on weight (proportional to bin capacity)
            double itemWidth = (double) item.getWeight() / bin.getCapacity() * (BIN_WIDTH - 16);
            
            // Ensure minimum width for readability
            if (itemWidth < 20) {
                itemWidth = 20;
            }
            
            // Draw item rectangle
            gc.setFill(itemColor);
            gc.fillRect(x + 8, itemY, itemWidth, ITEM_HEIGHT);
            
            // Draw item border
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeRect(x + 8, itemY, itemWidth, ITEM_HEIGHT);
            
            // Draw item weight - only if there's enough space
            if (itemWidth >= 15) {
                gc.setFill(Color.BLACK);
                gc.setFont(new Font("Arial", 9));
                gc.setTextAlign(TextAlignment.CENTER);
                gc.fillText(String.valueOf(item.getWeight()), 
                           x + 8 + itemWidth / 2, itemY + ITEM_HEIGHT / 2 + 3);
            }
            
            itemY += ITEM_HEIGHT + itemSpacing;
            
            // Prevent items from going outside the bin
            if (itemY + ITEM_HEIGHT > y + BIN_HEIGHT - 5) {
                break;
            }
        }
    }
    
    /**
     * Draw unplaced items
     */
    private void drawUnplacedItems(List<BinPackingAlgorithm.Item> unplacedItems, double y) {
        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 12));
        gc.setTextAlign(TextAlignment.LEFT);
        gc.fillText("Unplaced Items:", 10, y);
        
        double x = 10;
        double itemSpacing = 40; // More space between items
        
        for (int i = 0; i < unplacedItems.size(); i++) {
            BinPackingAlgorithm.Item item = unplacedItems.get(i);
            Color itemColor = ITEM_COLORS[i % ITEM_COLORS.length];
            
            // Draw small item representation with better spacing
            gc.setFill(itemColor);
            gc.fillRect(x, y + 15, 30, 15);
            
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeRect(x, y + 15, 30, 15);
            
            // Draw item weight with better positioning
            gc.setFill(Color.BLACK);
            gc.setFont(new Font("Arial", 9));
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText(String.valueOf(item.getWeight()), x + 15, y + 25);
            
            x += itemSpacing;
            
            // Wrap to next line if needed
            if (x + 30 > canvas.getWidth() - 20) {
                x = 10;
                y += 25;
            }
        }
    }
    
    /**
     * Draw solution statistics
     */
    private void drawStatistics(BinPackingAlgorithm.BinPackingResult result, double x, double y) {
        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Arial", 10));
        gc.setTextAlign(TextAlignment.LEFT);
        
        String stats = String.format(
            "Algorithm: %s | Bins: %d | Efficiency: %.1f%% | Time: %dms",
            result.getAlgorithmName(),
            result.getNumberOfBins(),
            result.getEfficiency() * 100,
            result.getExecutionTimeMs()
        );
        
        gc.fillText(stats, x, y);
    }
    
    /**
     * Draw message when no solution is available
     */
    private void drawNoSolution() {
        gc.setFill(Color.RED);
        gc.setFont(new Font("Arial", 16));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("No solution available", 
                   canvas.getWidth() / 2, canvas.getHeight() / 2);
    }
    
    /**
     * Clear the canvas
     */
    public void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    /**
     * Draw a welcome message
     */
    public void drawWelcome() {
        clearCanvas();
        gc.setFill(Color.BLUE);
        gc.setFont(new Font("Arial", 16));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Bin Packing Problem Solver", 
                   canvas.getWidth() / 2, canvas.getHeight() / 2 - 15);
        
        gc.setFill(Color.GRAY);
        gc.setFont(new Font("Arial", 12));
        gc.fillText("Enter items and click 'Find Solution' to start", 
                   canvas.getWidth() / 2, canvas.getHeight() / 2 + 15);
    }
}
