package com.acu.javafx.binpacking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

/**
 * Test class for bin packing algorithms
 * Tests the core functionality of First Fit, Best Fit, and First Fit Decreasing algorithms
 */
@DisplayName("Bin Packing Algorithm Tests")
public class BinPackingAlgorithmTest {
    
    private FirstFitAlgorithm firstFitAlgorithm;
    private BestFitAlgorithm bestFitAlgorithm;
    private FirstFitDecreasingAlgorithm firstFitDecreasingAlgorithm;
    
    @BeforeEach
    void setUp() {
        firstFitAlgorithm = new FirstFitAlgorithm();
        bestFitAlgorithm = new BestFitAlgorithm();
        firstFitDecreasingAlgorithm = new FirstFitDecreasingAlgorithm();
    }
    
    @Test
    @DisplayName("Test First Fit Algorithm with simple case")
    void testFirstFitSimple() {
        // Arrange
        List<BinPackingAlgorithm.Item> items = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 7),
            new BinPackingAlgorithm.Item(2, 5),
            new BinPackingAlgorithm.Item(3, 2),
            new BinPackingAlgorithm.Item(4, 3),
            new BinPackingAlgorithm.Item(5, 5),
            new BinPackingAlgorithm.Item(6, 8)
        );
        int binCapacity = 10;
        
        // Act
        BinPackingAlgorithm.BinPackingResult result = firstFitAlgorithm.solve(items, binCapacity);
        
        // Assert
        assertNotNull(result);
        assertEquals("First Fit", result.getAlgorithmName());
        assertTrue(result.getNumberOfBins() > 0);
        assertTrue(result.isOptimal()); // All items should be placed
        assertEquals(30, result.getTotalWeight()); // Sum of weights: 7+5+2+3+5+8 = 30
    }
    
    @Test
    @DisplayName("Test Best Fit Algorithm with simple case")
    void testBestFitSimple() {
        // Arrange
        List<BinPackingAlgorithm.Item> items = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 7),
            new BinPackingAlgorithm.Item(2, 5),
            new BinPackingAlgorithm.Item(3, 2),
            new BinPackingAlgorithm.Item(4, 3),
            new BinPackingAlgorithm.Item(5, 5),
            new BinPackingAlgorithm.Item(6, 8)
        );
        int binCapacity = 10;
        
        // Act
        BinPackingAlgorithm.BinPackingResult result = bestFitAlgorithm.solve(items, binCapacity);
        
        // Assert
        assertNotNull(result);
        assertEquals("Best Fit", result.getAlgorithmName());
        assertTrue(result.getNumberOfBins() > 0);
        assertTrue(result.isOptimal()); // All items should be placed
        assertEquals(30, result.getTotalWeight()); // Sum of weights: 7+5+2+3+5+8 = 30
    }
    
    @Test
    @DisplayName("Test First Fit Decreasing Algorithm with simple case")
    void testFirstFitDecreasingSimple() {
        // Arrange
        List<BinPackingAlgorithm.Item> items = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 7),
            new BinPackingAlgorithm.Item(2, 5),
            new BinPackingAlgorithm.Item(3, 2),
            new BinPackingAlgorithm.Item(4, 3),
            new BinPackingAlgorithm.Item(5, 5),
            new BinPackingAlgorithm.Item(6, 8)
        );
        int binCapacity = 10;
        
        // Act
        BinPackingAlgorithm.BinPackingResult result = firstFitDecreasingAlgorithm.solve(items, binCapacity);
        
        // Assert
        assertNotNull(result);
        assertEquals("First Fit Decreasing", result.getAlgorithmName());
        assertTrue(result.getNumberOfBins() > 0);
        assertTrue(result.isOptimal()); // All items should be placed
        assertEquals(30, result.getTotalWeight()); // Sum of weights: 7+5+2+3+5+8 = 30
    }
    
    @Test
    @DisplayName("Test empty items list")
    void testEmptyItems() {
        // Arrange
        List<BinPackingAlgorithm.Item> items = Arrays.asList();
        int binCapacity = 10;
        
        // Act
        BinPackingAlgorithm.BinPackingResult result = firstFitAlgorithm.solve(items, binCapacity);
        
        // Assert
        assertNotNull(result);
        assertEquals(0, result.getNumberOfBins());
        assertTrue(result.getUnplacedItems().isEmpty());
        assertEquals(0, result.getTotalWeight());
    }
    
    @Test
    @DisplayName("Test single item")
    void testSingleItem() {
        // Arrange
        List<BinPackingAlgorithm.Item> items = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 5)
        );
        int binCapacity = 10;
        
        // Act
        BinPackingAlgorithm.BinPackingResult result = firstFitAlgorithm.solve(items, binCapacity);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.getNumberOfBins());
        assertTrue(result.isOptimal());
        assertEquals(5, result.getTotalWeight());
    }
    
    @Test
    @DisplayName("Test item too large for any bin")
    void testItemTooLarge() {
        // Arrange
        List<BinPackingAlgorithm.Item> items = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 15) // Larger than bin capacity
        );
        int binCapacity = 10;
        
        // Act
        BinPackingAlgorithm.BinPackingResult result = firstFitAlgorithm.solve(items, binCapacity);
        
        // Assert
        assertNotNull(result);
        assertEquals(0, result.getNumberOfBins());
        assertEquals(1, result.getUnplacedItems().size());
        assertEquals(0, result.getTotalWeight());
    }
    
    @Test
    @DisplayName("Test perfect fit scenario")
    void testPerfectFit() {
        // Arrange
        List<BinPackingAlgorithm.Item> items = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 10),
            new BinPackingAlgorithm.Item(2, 10),
            new BinPackingAlgorithm.Item(3, 10)
        );
        int binCapacity = 10;
        
        // Act
        BinPackingAlgorithm.BinPackingResult result = firstFitAlgorithm.solve(items, binCapacity);
        
        // Assert
        assertNotNull(result);
        assertEquals(3, result.getNumberOfBins());
        assertTrue(result.isOptimal());
        assertEquals(30, result.getTotalWeight());
        assertEquals(1.0, result.getEfficiency(), 0.001); // Perfect efficiency
    }
    
    @Test
    @DisplayName("Test algorithm comparison")
    void testAlgorithmComparison() {
        // Arrange
        List<BinPackingAlgorithm.Item> items = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 7),
            new BinPackingAlgorithm.Item(2, 5),
            new BinPackingAlgorithm.Item(3, 2),
            new BinPackingAlgorithm.Item(4, 3),
            new BinPackingAlgorithm.Item(5, 5),
            new BinPackingAlgorithm.Item(6, 8)
        );
        int binCapacity = 10;
        
        // Act
        BinPackingAlgorithm.BinPackingResult firstFitResult = firstFitAlgorithm.solve(items, binCapacity);
        BinPackingAlgorithm.BinPackingResult bestFitResult = bestFitAlgorithm.solve(items, binCapacity);
        BinPackingAlgorithm.BinPackingResult firstFitDecreasingResult = firstFitDecreasingAlgorithm.solve(items, binCapacity);
        
        // Assert
        assertNotNull(firstFitResult);
        assertNotNull(bestFitResult);
        assertNotNull(firstFitDecreasingResult);
        
        // All algorithms should place all items
        assertTrue(firstFitResult.isOptimal());
        assertTrue(bestFitResult.isOptimal());
        assertTrue(firstFitDecreasingResult.isOptimal());
        
        // All should have the same total weight
        assertEquals(firstFitResult.getTotalWeight(), bestFitResult.getTotalWeight());
        assertEquals(bestFitResult.getTotalWeight(), firstFitDecreasingResult.getTotalWeight());
    }
    
    @Test
    @DisplayName("Test bin capacity validation")
    void testBinCapacityValidation() {
        // Arrange
        List<BinPackingAlgorithm.Item> items = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 5),
            new BinPackingAlgorithm.Item(2, 3)
        );
        int binCapacity = 10;
        
        // Act
        BinPackingAlgorithm.BinPackingResult result = firstFitAlgorithm.solve(items, binCapacity);
        
        // Assert
        assertNotNull(result);
        assertEquals(1, result.getNumberOfBins());
        
        BinPackingAlgorithm.Bin bin = result.getBins().get(0);
        assertEquals(10, bin.getCapacity());
        assertEquals(8, bin.getCurrentWeight());
        assertEquals(2, bin.getRemainingCapacity());
        assertEquals(2, bin.getItems().size());
    }
    
    @Test
    @DisplayName("Test execution time measurement")
    void testExecutionTime() {
        // Arrange
        List<BinPackingAlgorithm.Item> items = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 7),
            new BinPackingAlgorithm.Item(2, 5),
            new BinPackingAlgorithm.Item(3, 2),
            new BinPackingAlgorithm.Item(4, 3),
            new BinPackingAlgorithm.Item(5, 5),
            new BinPackingAlgorithm.Item(6, 8)
        );
        int binCapacity = 10;
        
        // Act
        BinPackingAlgorithm.BinPackingResult result = firstFitAlgorithm.solve(items, binCapacity);
        
        // Assert
        assertNotNull(result);
        assertTrue(result.getExecutionTimeMs() >= 0);
    }
    
    @Test
    @DisplayName("Test algorithm names")
    void testAlgorithmNames() {
        // Assert
        assertEquals("First Fit", firstFitAlgorithm.getAlgorithmName());
        assertEquals("Best Fit", bestFitAlgorithm.getAlgorithmName());
        assertEquals("First Fit Decreasing", firstFitDecreasingAlgorithm.getAlgorithmName());
    }
    
    @Test
    @DisplayName("Test time complexity descriptions")
    void testTimeComplexity() {
        // Assert
        assertNotNull(firstFitAlgorithm.getTimeComplexity());
        assertNotNull(bestFitAlgorithm.getTimeComplexity());
        assertNotNull(firstFitDecreasingAlgorithm.getTimeComplexity());
        
        assertTrue(firstFitAlgorithm.getTimeComplexity().contains("O("));
        assertTrue(bestFitAlgorithm.getTimeComplexity().contains("O("));
        assertTrue(firstFitDecreasingAlgorithm.getTimeComplexity().contains("O("));
    }
}
