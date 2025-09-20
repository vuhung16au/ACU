package com.acu.javafx.binpacking;

import java.util.*;

/**
 * First Fit Algorithm for Bin Packing Problem
 * 
 * Algorithm: Places each item into the first bin that has enough capacity.
 * Items are processed in the order they are given.
 * 
 * Time Complexity: O(n * m) where n is the number of items and m is the number of bins
 * Space Complexity: O(n + m) where n is the number of items and m is the number of bins
 * 
 * This is a greedy algorithm that may not produce optimal solutions.
 * However, it guarantees that no more than 2 * OPT bins are used, where OPT is the optimal number.
 */
public class FirstFitAlgorithm extends BinPackingAlgorithm {
    
    @Override
    public BinPackingResult solve(List<Item> items, int binCapacity) {
        long startTime = System.currentTimeMillis();
        
        // Create a copy of items to avoid modifying the original list
        List<Item> itemsCopy = new ArrayList<>(items);
        List<Bin> bins = new ArrayList<>();
        List<Item> unplacedItems = new ArrayList<>();
        
        // Process each item
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
        return "First Fit";
    }
    
    @Override
    public String getTimeComplexity() {
        return "O(n * m) where n = number of items, m = number of bins";
    }
}
