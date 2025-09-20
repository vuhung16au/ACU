package com.acu.javafx.binpacking;

import java.util.*;

/**
 * Abstract base class for bin packing algorithms.
 * Defines the common interface and data structures for bin packing solutions.
 * 
 * Time Complexity: Varies by algorithm implementation
 * Space Complexity: O(n) where n is the number of items
 */
public abstract class BinPackingAlgorithm {
    
    /**
     * Represents a single bin with its capacity and current items
     */
    public static class Bin {
        private final int capacity;
        private int currentWeight;
        private final List<Item> items;
        
        public Bin(int capacity) {
            this.capacity = capacity;
            this.currentWeight = 0;
            this.items = new ArrayList<>();
        }
        
        /**
         * Attempts to add an item to this bin
         * @param item the item to add
         * @return true if the item was added successfully, false otherwise
         */
        public boolean addItem(Item item) {
            if (currentWeight + item.getWeight() <= capacity) {
                items.add(item);
                currentWeight += item.getWeight();
                return true;
            }
            return false;
        }
        
        public int getCapacity() { return capacity; }
        public int getCurrentWeight() { return currentWeight; }
        public int getRemainingCapacity() { return capacity - currentWeight; }
        public List<Item> getItems() { return new ArrayList<>(items); }
        public boolean isEmpty() { return items.isEmpty(); }
        
        @Override
        public String toString() {
            return String.format("Bin(capacity=%d, current=%d, items=%s)", 
                capacity, currentWeight, items);
        }
    }
    
    /**
     * Represents an item with weight
     */
    public static class Item {
        private final int weight;
        private final int id;
        
        public Item(int id, int weight) {
            this.id = id;
            this.weight = weight;
        }
        
        public int getWeight() { return weight; }
        public int getId() { return id; }
        
        @Override
        public String toString() {
            return String.format("Item(id=%d, weight=%d)", id, weight);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Item item = (Item) obj;
            return id == item.id;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
    
    /**
     * Result of a bin packing algorithm execution
     */
    public static class BinPackingResult {
        private final List<Bin> bins;
        private final List<Item> unplacedItems;
        private final long executionTimeMs;
        private final String algorithmName;
        
        public BinPackingResult(List<Bin> bins, List<Item> unplacedItems, 
                              long executionTimeMs, String algorithmName) {
            this.bins = new ArrayList<>(bins);
            this.unplacedItems = new ArrayList<>(unplacedItems);
            this.executionTimeMs = executionTimeMs;
            this.algorithmName = algorithmName;
        }
        
        public List<Bin> getBins() { return bins; }
        public List<Item> getUnplacedItems() { return unplacedItems; }
        public long getExecutionTimeMs() { return executionTimeMs; }
        public String getAlgorithmName() { return algorithmName; }
        public int getNumberOfBins() { return bins.size(); }
        public boolean isOptimal() { return unplacedItems.isEmpty(); }
        
        /**
         * Calculate the total weight of all items in bins
         */
        public int getTotalWeight() {
            return bins.stream()
                .mapToInt(Bin::getCurrentWeight)
                .sum();
        }
        
        /**
         * Calculate the efficiency (total weight / total capacity)
         */
        public double getEfficiency() {
            int totalCapacity = bins.stream()
                .mapToInt(Bin::getCapacity)
                .sum();
            return totalCapacity > 0 ? (double) getTotalWeight() / totalCapacity : 0.0;
        }
    }
    
    /**
     * Solve the bin packing problem using the specific algorithm
     * @param items list of items to pack
     * @param binCapacity capacity of each bin
     * @return result containing the solution
     */
    public abstract BinPackingResult solve(List<Item> items, int binCapacity);
    
    /**
     * Get the name of the algorithm
     */
    public abstract String getAlgorithmName();
    
    /**
     * Get the time complexity of the algorithm
     */
    public abstract String getTimeComplexity();
}
