package com.acu.javafx.closestpair;

import java.util.Random;

/**
 * Simplified StdRandom implementation.
 * This provides basic random number generation functionality.
 */
public class StdRandom {
    private static Random random = new Random();

    /**
     * Set the seed for reproducible results.
     */
    public static void setSeed(long seed) {
        random.setSeed(seed);
    }

    /**
     * Return a random real number uniformly in [0, 1).
     */
    public static double uniform() {
        return random.nextDouble();
    }

    /**
     * Return a random real number uniformly in [a, b).
     */
    public static double uniform(double a, double b) {
        return a + uniform() * (b - a);
    }

    /**
     * Return a random integer uniformly in [0, n).
     */
    public static int uniform(int n) {
        if (n <= 0) throw new IllegalArgumentException("argument must be positive: " + n);
        return random.nextInt(n);
    }

    /**
     * Return a random integer uniformly in [a, b).
     */
    public static int uniform(int a, int b) {
        if (b <= a) throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        if ((long) b - a >= Integer.MAX_VALUE) throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        return a + uniform(b - a);
    }

    /**
     * Return a random boolean, which is true with probability p.
     */
    public static boolean bernoulli(double p) {
        if (!(p >= 0.0 && p <= 1.0))
            throw new IllegalArgumentException("probability p must be between 0.0 and 1.0: " + p);
        return uniform() < p;
    }

    /**
     * Return a random boolean, which is true with probability 0.5.
     */
    public static boolean bernoulli() {
        return bernoulli(0.5);
    }

    /**
     * Return a random real number from a Gaussian distribution with mean 0 and standard deviation 1.
     */
    public static double gaussian() {
        return random.nextGaussian();
    }

    /**
     * Return a random real number from a Gaussian distribution with mean μ and standard deviation σ.
     */
    public static double gaussian(double mu, double sigma) {
        return mu + sigma * gaussian();
    }

    /**
     * Return a random integer uniformly in [0, n).
     * Alias for uniform(int n) for compatibility.
     */
    public static int uniformInt(int n) {
        return uniform(n);
    }
} 