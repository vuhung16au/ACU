package com.acu.javafx.binpacking;

import java.util.*;

/**
 * Best Fit Algorithm for Bin Packing Problem
 * 
 * Algorithm: Places each item into the bin that will have the least remaining capacity
 * after placing the item. This minimizes wasted space.
 * 
 * Time Complexity: O(n * m) where n is the number of items and m is the number of bins
 * Space Complexity: O(n + m) where n is the number of items and m is the number of bins
 * 
 * This is a greedy algorithm that typically produces better results than First Fit
 * but still may not be optimal. It guarantees that no more than 1.7 * OPT bins are used.
 */
public class BestFitAlgorithm extends BinPackingAlgorithm {
    
    @Override
    public BinPackingResult solve(List<Item> items, int binCapacity) {
        long startTime = System.currentTimeMillis();
        
        // Create a copy of items to avoid modifying the original list
        List<Item> itemsCopy = new ArrayList<>(items);
        List<Bin> bins = new ArrayList<>();
        List<Item> unplacedItems = new ArrayList<>();
        
        // Process each item
        for (Item item : itemsCopy) {
            Bin bestBin = null;
            int bestRemainingCapacity = Integer.MAX_VALUE;
            
            // Find the bin with the smallest remaining capacity that can fit the item
            for (Bin bin : bins) {
                int remainingCapacity = bin.getRemainingCapacity();
                if (remainingCapacity >= item.getWeight() && 
                    remainingCapacity < bestRemainingCapacity) {
                    bestBin = bin;
                    bestRemainingCapacity = remainingCapacity;
                }
            }
            
            // Place the item in the best bin or create a new bin
            if (bestBin != null) {
                bestBin.addItem(item);
            } else {
                // Create a new bin
                Bin newBin = new Bin(binCapacity);
                if (newBin.addItem(item)) {
                    bins.add(newBin);
                } else {
                    // Item is too large for any bin
                    unplacedItems.add(item);
                }
            }
        }
        
        long executionTime = System.currentTimeMillis() - startTime;
        
        return new BinPackingResult(bins, unplacedItems, executionTime, getAlgorithmName());
    }
    
    @Override
    public String getAlgorithmName() {
        return "Best Fit";
    }
    
    @Override
    public String getTimeComplexity() {
        return "O(n * m) where n = number of items, m = number of bins";
    }
}
