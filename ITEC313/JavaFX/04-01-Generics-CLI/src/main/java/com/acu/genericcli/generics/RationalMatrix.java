package com.acu.genericcli.generics;

/**
 * Rational matrix class that extends GenericMatrix.
 * Provides concrete implementations for Rational arithmetic operations.
 */
public class RationalMatrix extends GenericMatrix<Rational> {
    
    @Override
    protected Rational add(Rational o1, Rational o2) {
        return o1.add(o2);
    }
    
    @Override
    protected Rational multiply(Rational o1, Rational o2) {
        return o1.multiply(o2);
    }
    
    @Override
    protected Rational zero() {
        return new Rational(0, 1);
    }
} 