package com.acu.javafx.binpacking;

import java.util.*;

/**
 * First Fit Decreasing Algorithm for Bin Packing Problem
 * 
 * Algorithm: Sorts items in decreasing order by weight, then applies First Fit algorithm.
 * This often produces better results than First Fit because larger items are placed first.
 * 
 * Time Complexity: O(n log n + n * m) where n is the number of items and m is the number of bins
 * Space Complexity: O(n + m) where n is the number of items and m is the number of bins
 * 
 * This algorithm guarantees that no more than (11/9) * OPT + 6/9 bins are used,
 * where OPT is the optimal number of bins.
 */
public class FirstFitDecreasingAlgorithm extends BinPackingAlgorithm {
    
    @Override
    public BinPackingResult solve(List<Item> items, int binCapacity) {
        long startTime = System.currentTimeMillis();
        
        // Create a copy of items and sort in decreasing order by weight
        List<Item> itemsCopy = new ArrayList<>(items);
        itemsCopy.sort((a, b) -> Integer.compare(b.getWeight(), a.getWeight()));
        
        List<Bin> bins = new ArrayList<>();
        List<Item> unplacedItems = new ArrayList<>();
        
        // Process each item (now in decreasing order)
        for (Item item : itemsCopy) {
            boolean placed = false;
            
            // Try to place the item in existing bins (first fit)
            for (Bin bin : bins) {
                if (bin.addItem(item)) {
                    placed = true;
                    break;
                }
            }
            
            // If item couldn't be placed in any existing bin, create a new bin
            if (!placed) {
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
        return "First Fit Decreasing";
    }
    
    @Override
    public String getTimeComplexity() {
        return "O(n log n + n * m) where n = number of items, m = number of bins";
    }
}
