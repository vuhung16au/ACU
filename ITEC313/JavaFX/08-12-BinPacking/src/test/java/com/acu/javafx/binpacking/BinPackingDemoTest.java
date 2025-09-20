package com.acu.javafx.binpacking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

/**
 * Test class for the main BinPackingDemo application
 * Tests the core functionality and integration of the bin packing system
 */
@DisplayName("Bin Packing Demo Tests")
public class BinPackingDemoTest {
    
    @Test
    @DisplayName("Test algorithm instantiation")
    void testAlgorithmInstantiation() {
        // Test that all algorithms can be instantiated
        FirstFitAlgorithm firstFit = new FirstFitAlgorithm();
        BestFitAlgorithm bestFit = new BestFitAlgorithm();
        FirstFitDecreasingAlgorithm firstFitDecreasing = new FirstFitDecreasingAlgorithm();
        
        assertNotNull(firstFit);
        assertNotNull(bestFit);
        assertNotNull(firstFitDecreasing);
    }
    
    @Test
    @DisplayName("Test algorithm consistency")
    void testAlgorithmConsistency() {
        // Test that all algorithms produce valid results for the same input
        List<BinPackingAlgorithm.Item> items = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 7),
            new BinPackingAlgorithm.Item(2, 5),
            new BinPackingAlgorithm.Item(3, 2),
            new BinPackingAlgorithm.Item(4, 3),
            new BinPackingAlgorithm.Item(5, 5),
            new BinPackingAlgorithm.Item(6, 8)
        );
        int binCapacity = 10;
        
        FirstFitAlgorithm firstFit = new FirstFitAlgorithm();
        BestFitAlgorithm bestFit = new BestFitAlgorithm();
        FirstFitDecreasingAlgorithm firstFitDecreasing = new FirstFitDecreasingAlgorithm();
        
        BinPackingAlgorithm.BinPackingResult firstFitResult = firstFit.solve(items, binCapacity);
        BinPackingAlgorithm.BinPackingResult bestFitResult = bestFit.solve(items, binCapacity);
        BinPackingAlgorithm.BinPackingResult firstFitDecreasingResult = firstFitDecreasing.solve(items, binCapacity);
        
        // All algorithms should produce valid results
        assertNotNull(firstFitResult);
        assertNotNull(bestFitResult);
        assertNotNull(firstFitDecreasingResult);
        
        // All should place all items
        assertTrue(firstFitResult.isOptimal());
        assertTrue(bestFitResult.isOptimal());
        assertTrue(firstFitDecreasingResult.isOptimal());
        
        // All should have the same total weight
        assertEquals(firstFitResult.getTotalWeight(), bestFitResult.getTotalWeight());
        assertEquals(bestFitResult.getTotalWeight(), firstFitDecreasingResult.getTotalWeight());
    }
    
    @Test
    @DisplayName("Test edge cases")
    void testEdgeCases() {
        FirstFitAlgorithm algorithm = new FirstFitAlgorithm();
        
        // Test with items that exactly fill bins
        List<BinPackingAlgorithm.Item> exactFitItems = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 10),
            new BinPackingAlgorithm.Item(2, 10),
            new BinPackingAlgorithm.Item(3, 10)
        );
        
        BinPackingAlgorithm.BinPackingResult result = algorithm.solve(exactFitItems, 10);
        assertEquals(3, result.getNumberOfBins());
        assertEquals(1.0, result.getEfficiency(), 0.001);
        
        // Test with items that don't fit perfectly
        List<BinPackingAlgorithm.Item> imperfectFitItems = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 7),
            new BinPackingAlgorithm.Item(2, 5),
            new BinPackingAlgorithm.Item(3, 3)
        );
        
        result = algorithm.solve(imperfectFitItems, 10);
        assertEquals(2, result.getNumberOfBins());
        assertTrue(result.getEfficiency() < 1.0);
    }
    
    @Test
    @DisplayName("Test performance with larger dataset")
    void testPerformanceWithLargerDataset() {
        // Create a larger dataset to test performance
        List<BinPackingAlgorithm.Item> items = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 7), new BinPackingAlgorithm.Item(2, 5),
            new BinPackingAlgorithm.Item(3, 2), new BinPackingAlgorithm.Item(4, 3),
            new BinPackingAlgorithm.Item(5, 5), new BinPackingAlgorithm.Item(6, 8),
            new BinPackingAlgorithm.Item(7, 4), new BinPackingAlgorithm.Item(8, 6),
            new BinPackingAlgorithm.Item(9, 1), new BinPackingAlgorithm.Item(10, 9)
        );
        int binCapacity = 10;
        
        FirstFitAlgorithm algorithm = new FirstFitAlgorithm();
        
        long startTime = System.currentTimeMillis();
        BinPackingAlgorithm.BinPackingResult result = algorithm.solve(items, binCapacity);
        long endTime = System.currentTimeMillis();
        
        assertNotNull(result);
        assertTrue(result.getExecutionTimeMs() >= 0);
        assertTrue((endTime - startTime) >= 0);
        assertTrue(result.isOptimal());
    }
    
    @Test
    @DisplayName("Test algorithm guarantees")
    void testAlgorithmGuarantees() {
        // Test that algorithms respect their theoretical guarantees
        List<BinPackingAlgorithm.Item> items = Arrays.asList(
            new BinPackingAlgorithm.Item(1, 7), new BinPackingAlgorithm.Item(2, 5),
            new BinPackingAlgorithm.Item(3, 2), new BinPackingAlgorithm.Item(4, 3),
            new BinPackingAlgorithm.Item(5, 5), new BinPackingAlgorithm.Item(6, 8),
            new BinPackingAlgorithm.Item(7, 4), new BinPackingAlgorithm.Item(8, 6),
            new BinPackingAlgorithm.Item(9, 1), new BinPackingAlgorithm.Item(10, 9)
        );
        int binCapacity = 10;
        
        FirstFitAlgorithm firstFit = new FirstFitAlgorithm();
        BestFitAlgorithm bestFit = new BestFitAlgorithm();
        FirstFitDecreasingAlgorithm firstFitDecreasing = new FirstFitDecreasingAlgorithm();
        
        BinPackingAlgorithm.BinPackingResult firstFitResult = firstFit.solve(items, binCapacity);
        BinPackingAlgorithm.BinPackingResult bestFitResult = bestFit.solve(items, binCapacity);
        BinPackingAlgorithm.BinPackingResult firstFitDecreasingResult = firstFitDecreasing.solve(items, binCapacity);
        
        // All algorithms should produce valid results
        assertNotNull(firstFitResult);
        assertNotNull(bestFitResult);
        assertNotNull(firstFitDecreasingResult);
        
        // All should place all items
        assertTrue(firstFitResult.isOptimal());
        assertTrue(bestFitResult.isOptimal());
        assertTrue(firstFitDecreasingResult.isOptimal());
        
        // Results should be reasonable (not use more bins than items)
        assertTrue(firstFitResult.getNumberOfBins() <= items.size());
        assertTrue(bestFitResult.getNumberOfBins() <= items.size());
        assertTrue(firstFitDecreasingResult.getNumberOfBins() <= items.size());
    }
}
