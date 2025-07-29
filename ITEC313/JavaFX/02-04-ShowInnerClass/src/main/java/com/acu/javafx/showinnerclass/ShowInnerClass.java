package com.acu.javafx.showinnerclass;

/**
 * ShowInnerClass.java: Demonstrate using inner classes
 * 
 * This class demonstrates the concept of inner classes in Java.
 * An inner class is a class that is defined within another class.
 * 
 * Key concepts demonstrated:
 * - Inner class definition
 * - Access to outer class members from inner class
 * - Creating instances of inner classes
 * 
 * Based on the example from: https://liveexample.pearsoncmg.com/html/ShowInnerClass.html
 */
public class ShowInnerClass {
    private int data;

    /**
     * A method in the outer class
     */
    public void m() {
        // Do something
        System.out.println("Outer class method called");
        InnerClass instance = new InnerClass();
        instance.mi();
    }

    /**
     * An inner class
     * This class has access to all members of the outer class,
     * including private members
     */
    class InnerClass {
        /**
         * A method in the inner class
         */
        public void mi() {
            // Directly reference data and method defined in its outer class
            data++;
            System.out.println("Inner class method called, data = " + data);
            // Note: We can call m() here, but it would create infinite recursion
            // So we'll just print a message instead
            System.out.println("Inner class can access outer class members");
        }
    }
    
    /**
     * Getter for data field
     */
    public int getData() {
        return data;
    }
    
    /**
     * Setter for data field
     */
    public void setData(int data) {
        this.data = data;
    }
} 