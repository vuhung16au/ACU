/**
 * Vector API Demo - Java 25 Feature
 * 
 * Vector API (finalized in Java 25) enables platform-independent SIMD operations
 * for high-performance numerical computations, especially beneficial for
 * AI and machine learning workloads.
 * 
 * Course: ITEC313 - Advanced Programming Concepts
 * @author Java 25 Features Demo
 * Date: 2025
 */

import java.util.Arrays;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class VectorApiDemo {
    
    public static void main(String[] args) {
        System.out.println("=== Java 25 Vector API Demo ===\n");
        
        scalarVsVectorOperations();
        vectorArithmetic();
        matrixOperations();
        performanceBenchmark();
        machineLearningExample();
    }
    
    /**
     * Compares scalar operations with vector operations
     */
    private static void scalarVsVectorOperations() {
        System.out.println("1. Scalar vs Vector Operations:");
        
        int[] array1 = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] array2 = {2, 3, 4, 5, 6, 7, 8, 9};
        int[] result = new int[array1.length];
        
        System.out.println("Array 1: " + Arrays.toString(array1));
        System.out.println("Array 2: " + Arrays.toString(array2));
        
        // Scalar approach (traditional)
        System.out.println("\nScalar addition (traditional approach):");
        long startTime = System.nanoTime();
        for (int i = 0; i < array1.length; i++) {
            result[i] = array1[i] + array2[i];
        }
        long scalarTime = System.nanoTime() - startTime;
        System.out.println("Result: " + Arrays.toString(result));
        System.out.println("Time: " + scalarTime + " ns");
        
        // Vector approach (Java 25 concept)
        System.out.println("\nVector addition (Java 25 Vector API concept):");
        startTime = System.nanoTime();
        // In actual Java 25, this would be:
        // IntVector v1 = IntVector.fromArray(SPECIES, array1, 0);
        // IntVector v2 = IntVector.fromArray(SPECIES, array2, 0);
        // IntVector vResult = v1.add(v2);
        // vResult.intoArray(result, 0);
        
        // Simulated vector operation
        simulateVectorAddition(array1, array2, result);
        long vectorTime = System.nanoTime() - startTime;
        System.out.println("Result: " + Arrays.toString(result));
        System.out.println("Time: " + vectorTime + " ns");
        System.out.println("Note: Actual Vector API would be significantly faster on supported hardware");
        
        System.out.println();
    }
    
    /**
     * Demonstrates vector arithmetic operations
     */
    private static void vectorArithmetic() {
        System.out.println("2. Vector Arithmetic Operations:");
        
        float[] a = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f};
        float[] b = {0.5f, 1.5f, 2.5f, 3.5f, 4.5f, 5.5f, 6.5f, 7.5f};
        
        System.out.println("Vector A: " + Arrays.toString(a));
        System.out.println("Vector B: " + Arrays.toString(b));
        
        // Addition
        float[] addition = simulateVectorOperation(a, b, Float::sum);
        System.out.println("A + B:    " + Arrays.toString(addition));
        
        // Subtraction
        float[] subtraction = simulateVectorOperation(a, b, (x, y) -> x - y);
        System.out.println("A - B:    " + Arrays.toString(subtraction));
        
        // Multiplication
        float[] multiplication = simulateVectorOperation(a, b, (x, y) -> x * y);
        System.out.println("A * B:    " + Arrays.toString(multiplication));
        
        // Division
        float[] division = simulateVectorOperation(a, b, (x, y) -> x / y);
        System.out.println("A / B:    " + Arrays.toString(division));
        
        // Square root (unary operation)
        float[] sqrt = simulateUnaryVectorOperation(a, x -> (float) Math.sqrt(x));
        System.out.println("sqrt(A):  " + Arrays.toString(sqrt));
        
        System.out.println();
    }
    
    /**
     * Demonstrates matrix operations using Vector API
     */
    private static void matrixOperations() {
        System.out.println("3. Matrix Operations:");
        
        // 2x2 matrices for simplicity
        float[][] matrixA = {{1, 2}, {3, 4}};
        float[][] matrixB = {{5, 6}, {7, 8}};
        
        System.out.println("Matrix A:");
        printMatrix(matrixA);
        System.out.println("Matrix B:");
        printMatrix(matrixB);
        
        // Matrix multiplication
        System.out.println("Matrix A * B:");
        float[][] result = simulateMatrixMultiplication(matrixA, matrixB);
        printMatrix(result);
        
        // Element-wise operations
        System.out.println("Element-wise A + B:");
        float[][] elementSum = simulateElementWiseOperation(matrixA, matrixB, Float::sum);
        printMatrix(elementSum);
        
        System.out.println();
    }
    
    /**
     * Performance benchmark comparing scalar and vector approaches
     */
    private static void performanceBenchmark() {
        System.out.println("4. Performance Benchmark:");
        
        int size = 1_000_000;
        float[] array1 = new float[size];
        float[] array2 = new float[size];
        float[] result = new float[size];
        
        Random random = new Random(42);
        for (int i = 0; i < size; i++) {
            array1[i] = random.nextFloat() * 100;
            array2[i] = random.nextFloat() * 100;
        }
        
        System.out.println("Processing " + size + " elements:");
        
        // Scalar benchmark
        long startTime = System.nanoTime();
        for (int i = 0; i < size; i++) {
            result[i] = array1[i] * array1[i] + array2[i] * array2[i];  // dot product style
        }
        long scalarTime = System.nanoTime() - startTime;
        float scalarSum = 0;
        for (float value : result) {
            scalarSum += value;
        }
        
        // Vector benchmark (simulated)
        startTime = System.nanoTime();
        simulateVectorDotProduct(array1, array2, result);
        long vectorTime = System.nanoTime() - startTime;
        float vectorSum = 0;
        for (float value : result) {
            vectorSum += value;
        }
        
        System.out.println("Scalar approach:  " + String.format("%.2f ms", scalarTime / 1_000_000.0));
        System.out.println("Vector approach:  " + String.format("%.2f ms", vectorTime / 1_000_000.0));
        System.out.println("Scalar sum: " + String.format("%.2f", scalarSum));
        System.out.println("Vector sum: " + String.format("%.2f", vectorSum));
        System.out.println("Note: Real Vector API would show 2-8x performance improvement");
        
        System.out.println();
    }
    
    /**
     * Machine learning example using Vector API
     */
    private static void machineLearningExample() {
        System.out.println("5. Machine Learning Example:");
        
        // Simple neural network layer computation
        System.out.println("Neural network layer computation:");
        
        float[] inputs = {0.5f, 0.8f, 0.2f, 0.9f};
        float[][] weights = {
            {0.1f, 0.2f, 0.3f, 0.4f},
            {0.5f, 0.6f, 0.7f, 0.8f},
            {0.9f, 0.1f, 0.2f, 0.3f}
        };
        float[] biases = {0.1f, 0.2f, 0.3f};
        
        System.out.println("Inputs: " + Arrays.toString(inputs));
        System.out.println("Computing: output = weights * inputs + biases");
        
        // Traditional approach
        long startTime = System.nanoTime();
        float[] outputTraditional = computeLayerTraditional(inputs, weights, biases);
        long traditionalTime = System.nanoTime() - startTime;
        
        // Vector approach (simulated)
        startTime = System.nanoTime();
        float[] outputVector = computeLayerWithVectors(inputs, weights, biases);
        long vectorTime = System.nanoTime() - startTime;
        
        System.out.println("Traditional output: " + Arrays.toString(outputTraditional));
        System.out.println("Vector output:      " + Arrays.toString(outputVector));
        System.out.println("Traditional time: " + traditionalTime + " ns");
        System.out.println("Vector time:      " + vectorTime + " ns");
        
        // Apply activation function (ReLU)
        System.out.println("\nApplying ReLU activation:");
        float[] activated = simulateUnaryVectorOperation(outputVector, x -> Math.max(0, x));
        System.out.println("Activated output: " + Arrays.toString(activated));
        
        System.out.println();
    }
    
    // Helper methods for simulating Vector API operations
    
    private static void simulateVectorAddition(int[] a, int[] b, int[] result) {
        // This simulates what Vector API would do in a single SIMD instruction
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] + b[i];
        }
    }
    
    private static float[] simulateVectorOperation(float[] a, float[] b, 
                                                  BinaryOperator<Float> operation) {
        float[] result = new float[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = operation.apply(a[i], b[i]);
        }
        return result;
    }
    
    private static float[] simulateUnaryVectorOperation(float[] a, 
                                                       UnaryOperator<Float> operation) {
        float[] result = new float[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = operation.apply(a[i]);
        }
        return result;
    }
    
    private static float[][] simulateMatrixMultiplication(float[][] a, float[][] b) {
        int rows = a.length;
        int cols = b[0].length;
        float[][] result = new float[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }
    
    private static float[][] simulateElementWiseOperation(float[][] a, float[][] b, 
                                                         BinaryOperator<Float> operation) {
        float[][] result = new float[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = operation.apply(a[i][j], b[i][j]);
            }
        }
        return result;
    }
    
    private static void simulateVectorDotProduct(float[] a, float[] b, float[] result) {
        // Simulates vectorized computation of a[i]^2 + b[i]^2
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] * a[i] + b[i] * b[i];
        }
    }
    
    private static float[] computeLayerTraditional(float[] inputs, float[][] weights, float[] biases) {
        float[] output = new float[weights.length];
        for (int i = 0; i < weights.length; i++) {
            output[i] = biases[i];
            for (int j = 0; j < inputs.length; j++) {
                output[i] += weights[i][j] * inputs[j];
            }
        }
        return output;
    }
    
    private static float[] computeLayerWithVectors(float[] inputs, float[][] weights, float[] biases) {
        // In real Vector API, this would use vectorized operations
        return computeLayerTraditional(inputs, weights, biases);
    }
    
    private static void printMatrix(float[][] matrix) {
        for (float[] row : matrix) {
            System.out.println("  " + Arrays.toString(row));
        }
    }
}