package com.acu.genericcli.generics;

/**
 * Generic matrix class that can work with any numeric type.
 * Demonstrates how generics can be used for mathematical operations.
 * 
 * @param <E> the type of elements in the matrix
 */
public abstract class GenericMatrix<E extends Number> {
    
    /**
     * Abstract method for adding two elements.
     * @param o1 the first element
     * @param o2 the second element
     * @return the sum of the two elements
     */
    protected abstract E add(E o1, E o2);
    
    /**
     * Abstract method for multiplying two elements.
     * @param o1 the first element
     * @param o2 the second element
     * @return the product of the two elements
     */
    protected abstract E multiply(E o1, E o2);
    
    /**
     * Abstract method for defining zero element.
     * @return the zero element for this type
     */
    protected abstract E zero();
    
    /**
     * Add two matrices.
     * @param matrix1 the first matrix
     * @param matrix2 the second matrix
     * @return the resulting matrix
     */
    public E[][] addMatrix(E[][] matrix1, E[][] matrix2) {
        // Check bounds
        if (matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            throw new RuntimeException("The matrices do not have the same size");
        }
        
        @SuppressWarnings("unchecked")
        E[][] result = (E[][]) new Number[matrix1.length][matrix1[0].length];
        
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = add(matrix1[i][j], matrix2[i][j]);
            }
        }
        
        return result;
    }
    
    /**
     * Multiply two matrices.
     * @param matrix1 the first matrix
     * @param matrix2 the second matrix
     * @return the resulting matrix
     */
    public E[][] multiplyMatrix(E[][] matrix1, E[][] matrix2) {
        // Check bounds
        if (matrix1[0].length != matrix2.length) {
            throw new RuntimeException("The matrices do not have compatible size");
        }
        
        @SuppressWarnings("unchecked")
        E[][] result = (E[][]) new Number[matrix1.length][matrix2[0].length];
        
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                result[i][j] = zero();
                
                for (int k = 0; k < matrix1[0].length; k++) {
                    result[i][j] = add(result[i][j], multiply(matrix1[i][k], matrix2[k][j]));
                }
            }
        }
        
        return result;
    }
    
    /**
     * Print matrices, the operator, and their operation result.
     * @param m1 the first matrix
     * @param m2 the second matrix
     * @param m3 the result matrix
     * @param op the operator
     */
    public static void printResult(Number[][] m1, Number[][] m2, Number[][] m3, char op) {
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[0].length; j++) {
                System.out.print(" " + m1[i][j]);
            }
            
            if (i == m1.length / 2) {
                System.out.print("  " + op + "  ");
            } else {
                System.out.print("     ");
            }
            
            for (int j = 0; j < m2.length; j++) {
                System.out.print(" " + m2[i][j]);
            }
            
            if (i == m1.length / 2) {
                System.out.print("  =  ");
            } else {
                System.out.print("     ");
            }
            
            for (int j = 0; j < m3.length; j++) {
                System.out.print(" " + m3[i][j]);
            }
            
            System.out.println();
        }
    }
} 