package com.acu.javafx.linkedlist;

/**
 * MyList interface defines the contract for a generic list implementation.
 * This interface provides basic list operations like adding, removing, and accessing elements.
 * 
 * @param <E> the type of elements in this list
 */
public interface MyList<E> {
    /**
     * Add a new element at the specified index in this list.
     * The index of the head element is 0.
     * 
     * @param index the index where the element should be added
     * @param e the element to add
     */
    void add(int index, E e);
    
    /**
     * Add a new element to the end of this list.
     * 
     * @param e the element to add
     */
    void add(E e);
    
    /**
     * Remove the element at the specified position in this list.
     * Return the element that was removed from the list.
     * 
     * @param index the index of the element to remove
     * @return the element that was removed, or null if index is out of range
     */
    E remove(int index);
    
    /**
     * Clear the list.
     */
    void clear();
    
    /**
     * Return true if this list contains the element e.
     * 
     * @param e the element to search for
     * @return true if the element is found, false otherwise
     */
    boolean contains(Object e);
    
    /**
     * Return the element at the specified index.
     * 
     * @param index the index of the element to return
     * @return the element at the specified index, or null if index is out of range
     */
    E get(int index);
    
    /**
     * Return the index of the first matching element in this list.
     * Return -1 if no match.
     * 
     * @param e the element to search for
     * @return the index of the first occurrence, or -1 if not found
     */
    int indexOf(Object e);
    
    /**
     * Return the index of the last matching element in this list.
     * Return -1 if no match.
     * 
     * @param e the element to search for
     * @return the index of the last occurrence, or -1 if not found
     */
    int lastIndexOf(E e);
    
    /**
     * Replace the element at the specified position in this list with the specified element.
     * 
     * @param index the index of the element to replace
     * @param e the new element
     * @return the element that was replaced, or null if index is out of range
     */
    E set(int index, E e);
    
    /**
     * Return the number of elements in this list.
     * 
     * @return the number of elements in this list
     */
    int size();
    
    /**
     * Return an iterator over the elements in this list.
     * 
     * @return an iterator over the elements in this list
     */
    java.util.Iterator<E> iterator();
}
