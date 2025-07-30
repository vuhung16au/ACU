package com.acu.javafx.stack;

import java.util.ArrayList;

/**
 * Stack class - A simple stack implementation using ArrayList
 * Based on the JavaScript code from https://liveexample.pearsoncmg.com/dsanimation/StackeBook.html
 */
public class Stack {
    private ArrayList<Object> list = new ArrayList<>();

    /**
     * Pushes an element to the stack
     * @param e the element to push
     */
    public void push(Object e) {
        list.add(e);
    }
      
    /**
     * Pops the top element from the stack
     * @return the top element
     */
    public Object pop() {
        if (isEmpty()) {
            return null;
        }
        return list.remove(list.size() - 1);
    }
  
    /**
     * Peeks the top element from the stack without removing it
     * @return the top element
     */
    public Object peek() {
        if (isEmpty()) {
            return null;
        }
        return list.get(getSize() - 1);
    }

    /**
     * Returns the stack size
     * @return the number of elements in the stack
     */
    public int getSize() {
        return list.size();
    }
   
    /**
     * Returns true if stack is empty
     * @return true if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return list.size() == 0;
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