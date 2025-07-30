package com.acu.javafx.probing;

import java.util.ArrayList;
import java.util.List;

/**
 * HashSet implementation using Separate Chaining based on the original JavaScript version.
 * This class provides a hash table implementation that uses separate chaining for collision resolution.
 */
public class MyHashSet {
    // Current hash table capacity. Capacity is a power of 2
    private int capacity = 11;

    // Specify a load factor threshold used in the hash table
    private double loadFactorThreshold = 0.5;

    // The number of elements in the set
    private int size = 0;

    // Hash table is an array with each cell that is a linked list
    private LinkedList<Integer>[] table;

    private static final int DEFAULT_INITIAL_CAPACITY = 4;
    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private static final double DEFAULT_MAX_LOAD_FACTOR = 0.75;

    /**
     * Constructor for MyHashSet.
     */
    @SuppressWarnings("unchecked")
    public MyHashSet() {
        this.table = new LinkedList[this.capacity];
        for (int i = 0; i < this.capacity; i++) {
            this.table[i] = null;
        }
    }

    /**
     * Clear all elements from the set.
     */
    public void clear() {
        this.size = 0;
        removeElements();
    }

    /**
     * Set the load factor threshold.
     */
    public void setLoadFactorThreshold(double factor) {
        if (factor >= 1) {
            throw new IllegalArgumentException("The load factor threshold must be less than 1.0");
        } else {
            this.loadFactorThreshold = factor;

            if (this.size + 1 > this.capacity * this.loadFactorThreshold) {
                rehash();
            }
        }
    }

    /**
     * Set the capacity of the hash table.
     */
    public void setCapacity(int capacity) {
        if (this.size + 1 > capacity * this.loadFactorThreshold) {
            throw new IllegalArgumentException("Please set a larger capacity. The capacity cannot be " +
                    " set to a smaller value that causes rehashing.");
        } else {
            List<Integer> list = setToList(); // Copy to a list
            this.capacity = capacity;
            this.table = new LinkedList[this.capacity];
            for (int i = 0; i < this.capacity; i++) {
                this.table[i] = null;
            }
            this.size = 0;

            for (Integer element : list) {
                this.add(element);
            }
        }
    }

    /**
     * Return true if the element is in the set.
     */
    public boolean contains(Integer e) {
        int bucketIndex = hash(e);
        if (this.table[bucketIndex] != null) {
            LinkedList<Integer> bucket = this.table[bucketIndex];
            for (int i = 0; i < bucket.getSize(); i++) {
                if (e.equals(bucket.get(i))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Add an element to the set.
     */
    public boolean add(Integer e) {
        if (this.contains(e)) { // Duplicate element not stored
            return false;
        }

        if (this.size + 1 > this.capacity * this.loadFactorThreshold) {
            rehash();
        }

        int bucketIndex = hash(e);

        // Create a linked list for the bucket if it is not created
        if (this.table[bucketIndex] == null) {
            this.table[bucketIndex] = new LinkedList<>();
        }

        this.table[bucketIndex].add(e);
        this.size++; // Increase size

        return true;
    }

    /**
     * Remove the element from the set.
     */
    public boolean remove(Integer e) {
        if (!this.contains(e)) {
            return false;
        }

        int bucketIndex = hash(e);

        // Create a linked list for the bucket if it is not created
        if (this.table[bucketIndex] != null) {
            LinkedList<Integer> bucket = this.table[bucketIndex];
            bucket.remove(e);
        }

        this.size--; // Decrease size

        return true;
    }

    /**
     * Return true if the set contains no elements.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Return the number of elements in the set.
     */
    public int size() {
        return this.size;
    }

    /**
     * Hash function.
     */
    public int hash(Integer hashCode) {
        return hashCode % this.capacity;
    }

    /**
     * Ensure the hashing is evenly distributed.
     */
    private int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Return a power of 2 for initialCapacity.
     */
    private int trimToPowerOf2(int initialCapacity) {
        int capacity = 1;
        while (capacity < initialCapacity) {
            capacity <<= 1;
        }

        return capacity;
    }

    /**
     * Remove all elements from each bucket.
     */
    public void removeElements() {
        for (int i = 0; i < this.capacity; i++) {
            if (this.table[i] != null) {
                this.table[i].clear();
            }
        }
    }

    /**
     * Rehash the set.
     */
    @SuppressWarnings("unchecked")
    public void rehash() {
        List<Integer> list = setToList(); // Copy to a list
        this.capacity <<= 1; // Double capacity      
        this.table = new LinkedList[this.capacity]; // Create a new hash table
        this.size = 0; // Reset size 

        for (Integer element : list) {
            this.add(element); // Add from the old table to the new table
        }
    }

    /**
     * Copy elements in the hash set to an array list.
     */
    public List<Integer> setToList() {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < this.capacity; i++) {
            if (this.table[i] != null) {
                for (int j = 0; j < this.table[i].getSize(); j++) {
                    list.add(this.table[i].get(j));
                }
            }
        }

        return list;
    }

    @Override
    public String toString() {
        List<Integer> list = setToList();
        StringBuilder builder = new StringBuilder("[");

        // Add the elements except the last one to the string builder
        for (int i = 0; i < list.size() - 1; i++) {
            builder.append(list.get(i)).append(", ");
        }

        // Add the last element in the list to the string builder
        if (list.isEmpty()) {
            builder.append("]");
        } else {
            builder.append(list.get(list.size() - 1)).append("]");
        }

        return builder.toString();
    }

    /**
     * Get the current capacity.
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Get the load factor threshold.
     */
    public double getLoadFactorThreshold() {
        return this.loadFactorThreshold;
    }

    /**
     * Get the current load factor.
     */
    public double getCurrentLoadFactor() {
        return (double) this.size / this.capacity;
    }

    /**
     * Get the table array for visualization purposes.
     */
    public LinkedList<Integer>[] getTable() {
        return this.table.clone();
    }
} 