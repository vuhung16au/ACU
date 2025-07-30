package com.acu.javafx.stack;

import java.util.ArrayList;

/**
 * GenericStack class - A generic stack implementation using ArrayList
 * Based on the code from https://liveexample.pearsoncmg.com/html/GenericStack.html
 * 
 * @param <E> the type of elements in this stack
 */
public class GenericStack<E> {
    private ArrayList<E> list = new ArrayList<>();

    /**
     * Returns the number of elements in the stack
     * @return the size of the stack
     */
    public int getSize() {
        return list.size();
    }

    /**
     * Returns the top element without removing it
     * @return the top element
     */
    public E peek() {
        return list.get(getSize() - 1);
    }

    /**
     * Pushes an element onto the top of the stack
     * @param o the element to push
     */
    public void push(E o) {
        list.add(o);
    }

    /**
     * Removes and returns the top element from the stack
     * @return the top element
     */
    public E pop() {
        E o = list.get(getSize() - 1);
        list.remove(getSize() - 1);
        return o;
    }

    /**
     * Checks if the stack is empty
     * @return true if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    /**
     * Returns a string representation of the stack
     * @return string representation
     */
    @Override
    public String toString() {
        return "stack: " + list.toString();
    }
} 