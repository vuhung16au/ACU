package com.acu.genericcli.generics;

/**
 * Integer matrix class that extends GenericMatrix.
 * Provides concrete implementations for Integer arithmetic operations.
 */
public class IntegerMatrix extends GenericMatrix<Integer> {
    
    @Override
    protected Integer add(Integer o1, Integer o2) {
        return o1 + o2;
    }
    
    @Override
    protected Integer multiply(Integer o1, Integer o2) {
        return o1 * o2;
    }
    
    @Override
    protected Integer zero() {
        return 0;
    }
} 