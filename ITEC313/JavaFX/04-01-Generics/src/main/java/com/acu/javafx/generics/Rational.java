package com.acu.javafx.generics;

/**
 * Rational number class for matrix operations.
 * Represents a rational number as a fraction with numerator and denominator.
 */
public class Rational extends Number {
    private long numerator = 0;
    private long denominator = 1;
    
    /**
     * Constructs a rational number with given numerator and denominator.
     * @param numerator the numerator
     * @param denominator the denominator
     */
    public Rational(long numerator, long denominator) {
        long gcd = gcd(numerator, denominator);
        this.numerator = ((denominator > 0) ? 1 : -1) * numerator / gcd;
        this.denominator = Math.abs(denominator) / gcd;
    }
    
    /**
     * Constructs a rational number with numerator 0 and denominator 1.
     */
    public Rational() {
        this(0, 1);
    }
    
    /**
     * Returns the greatest common divisor of two numbers.
     * @param n the first number
     * @param d the second number
     * @return the greatest common divisor
     */
    private static long gcd(long n, long d) {
        long n1 = Math.abs(n);
        long n2 = Math.abs(d);
        int gcd = 1;
        
        for (int k = 1; k <= n1 && k <= n2; k++) {
            if (n1 % k == 0 && n2 % k == 0) {
                gcd = k;
            }
        }
        
        return gcd;
    }
    
    /**
     * Returns the numerator.
     * @return the numerator
     */
    public long getNumerator() {
        return numerator;
    }
    
    /**
     * Returns the denominator.
     * @return the denominator
     */
    public long getDenominator() {
        return denominator;
    }
    
    /**
     * Adds this rational number with another.
     * @param secondRational the other rational number
     * @return the sum
     */
    public Rational add(Rational secondRational) {
        long n = numerator * secondRational.getDenominator() + denominator * secondRational.getNumerator();
        long d = denominator * secondRational.getDenominator();
        return new Rational(n, d);
    }
    
    /**
     * Subtracts another rational number from this one.
     * @param secondRational the other rational number
     * @return the difference
     */
    public Rational subtract(Rational secondRational) {
        long n = numerator * secondRational.getDenominator() - denominator * secondRational.getNumerator();
        long d = denominator * secondRational.getDenominator();
        return new Rational(n, d);
    }
    
    /**
     * Multiplies this rational number with another.
     * @param secondRational the other rational number
     * @return the product
     */
    public Rational multiply(Rational secondRational) {
        long n = numerator * secondRational.getNumerator();
        long d = denominator * secondRational.getDenominator();
        return new Rational(n, d);
    }
    
    /**
     * Divides this rational number by another.
     * @param secondRational the other rational number
     * @return the quotient
     */
    public Rational divide(Rational secondRational) {
        long n = numerator * secondRational.getDenominator();
        long d = denominator * secondRational.getNumerator();
        return new Rational(n, d);
    }
    
    @Override
    public String toString() {
        if (denominator == 1) {
            return numerator + "";
        } else {
            return numerator + "/" + denominator;
        }
    }
    
    @Override
    public boolean equals(Object other) {
        if ((this.subtract((Rational) (other))).getNumerator() == 0) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public int intValue() {
        return (int) doubleValue();
    }
    
    @Override
    public float floatValue() {
        return (float) doubleValue();
    }
    
    @Override
    public double doubleValue() {
        return numerator * 1.0 / denominator;
    }
    
    @Override
    public long longValue() {
        return (long) doubleValue();
    }
} 