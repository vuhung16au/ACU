package com.acu.javafx.generics;

import java.util.ArrayList;

/**
 * Generic stack implementation that can work with any type.
 * This demonstrates the power of generics in Java.
 * 
 * @param <E> the type of elements in this stack
 */
public class GenericStack<E> {
    private ArrayList<E> list = new ArrayList<>();

    /**
     * Returns the number of elements in this stack.
     * @return the number of elements in this stack
     */
    public int getSize() {
        return list.size();
    }

    /**
     * Returns the top element in this stack.
     * @return the top element in this stack
     */
    public E peek() {
        return list.get(getSize() - 1);
    }

    /**
     * Adds an element to the top of this stack.
     * @param o the element to add
     */
    public void push(E o) {
        list.add(o);
    }

    /**
     * Removes and returns the top element in this stack.
     * @return the top element in this stack
     */
    public E pop() {
        E o = list.get(getSize() - 1);
        list.remove(getSize() - 1);
        return o;
    }

    /**
     * Returns true if this stack is empty.
     * @return true if this stack is empty
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return "stack: " + list.toString();
    }
} 