/**
 * Advanced.java - Advanced Multi-Dimensional Array Concepts
 * 
 * This example demonstrates advanced concepts:
 * - Jagged arrays (irregular 2D arrays)
 * - 3D arrays
 * - Array algorithms and patterns
 * - Memory-efficient array operations
 * - Complex array manipulations
 */

public class Advanced {
    
    public static void main(String[] args) {
        System.out.println("=== Advanced Multi-Dimensional Array Concepts ===\n");
        
        // 1. Jagged Arrays
        System.out.println("1. Jagged Arrays (Irregular 2D Arrays):");
        System.out.println("========================================");
        
        // Create a jagged array with different row lengths
        int[][] jaggedArray = {
            {1, 2, 3, 4, 5},           // Row 0 has 5 elements
            {6, 7},                    // Row 1 has 2 elements
            {8, 9, 10},                // Row 2 has 3 elements
            {11},                      // Row 3 has 1 element
            {12, 13, 14, 15, 16, 17}   // Row 4 has 6 elements
        };
        
        System.out.println("Jagged Array:");
        printJaggedArray(jaggedArray);
        
        System.out.println("Array properties:");
        System.out.println("- Number of rows: " + jaggedArray.length);
        for (int i = 0; i < jaggedArray.length; i++) {
            System.out.println("- Row " + i + " has " + jaggedArray[i].length + " elements");
        }
        
        // 2. 3D Arrays
        System.out.println("\n2. 3D Arrays:");
        System.out.println("=============");
        
        // Create a 3D array (2x3x4)
        int[][][] threeDArray = {
            {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
            },
            {
                {13, 14, 15, 16},
                {17, 18, 19, 20},
                {21, 22, 23, 24}
            }
        };
        
        System.out.println("3D Array (2x3x4):");
        print3DArray(threeDArray);
        
        System.out.println("3D Array properties:");
        System.out.println("- Number of layers: " + threeDArray.length);
        System.out.println("- Number of rows per layer: " + threeDArray[0].length);
        System.out.println("- Number of columns per row: " + threeDArray[0][0].length);
        
        // 3. Array Algorithms
        System.out.println("\n3. Array Algorithms:");
        System.out.println("====================");
        
        int[][] testMatrix = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        };
        
        System.out.println("Test Matrix:");
        printMatrix(testMatrix);
        
        // Spiral traversal
        System.out.println("Spiral traversal:");
        spiralTraversal(testMatrix);
        
        // Zigzag traversal
        System.out.println("\nZigzag traversal:");
        zigzagTraversal(testMatrix);
        
        // 4. Memory-Efficient Operations
        System.out.println("\n4. Memory-Efficient Operations:");
        System.out.println("===============================");
        
        // In-place matrix operations
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        System.out.println("Original Matrix:");
        printMatrix(matrix);
        
        // In-place transpose (only for square matrices)
        inPlaceTranspose(matrix);
        System.out.println("After in-place transpose:");
        printMatrix(matrix);
        
        // 5. Complex Array Manipulations
        System.out.println("\n5. Complex Array Manipulations:");
        System.out.println("===============================");
        
        // Create a magic square
        int[][] magicSquare = createMagicSquare(3);
        System.out.println("3x3 Magic Square:");
        printMatrix(magicSquare);
        
        System.out.println("Magic square properties:");
        System.out.println("- Is magic square: " + isMagicSquare(magicSquare));
        System.out.println("- Magic constant: " + getMagicConstant(magicSquare));
        
        // 6. Array Patterns
        System.out.println("\n6. Array Patterns:");
        System.out.println("==================");
        
        // Create patterns
        int[][] pattern1 = createNumberPattern(4);
        System.out.println("Number Pattern:");
        printMatrix(pattern1);
        
        int[][] pattern2 = createSpiralPattern(4);
        System.out.println("Spiral Pattern:");
        printMatrix(pattern2);
        
        // 7. Array Statistics
        System.out.println("\n7. Array Statistics:");
        System.out.println("====================");
        
        int[][] dataMatrix = {
            {85, 92, 78, 95},
            {67, 88, 91, 73},
            {82, 79, 85, 90},
            {94, 76, 89, 87}
        };
        
        System.out.println("Data Matrix:");
        printMatrix(dataMatrix);
        
        System.out.println("Statistics:");
        System.out.println("- Row sums: " + java.util.Arrays.toString(getRowSums(dataMatrix)));
        System.out.println("- Column sums: " + java.util.Arrays.toString(getColumnSums(dataMatrix)));
        System.out.println("- Row averages: " + java.util.Arrays.toString(getRowAverages(dataMatrix)));
        System.out.println("- Column averages: " + java.util.Arrays.toString(getColumnAverages(dataMatrix)));
    }
    
    /**
     * Print a jagged array
     */
    public static void printJaggedArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print("Row " + i + ": [ ");
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
                if (j < array[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(" ]");
        }
        System.out.println();
    }
    
    /**
     * Print a 3D array
     */
    public static void print3DArray(int[][][] array) {
        for (int layer = 0; layer < array.length; layer++) {
            System.out.println("Layer " + layer + ":");
            for (int row = 0; row < array[layer].length; row++) {
                System.out.print("  [ ");
                for (int col = 0; col < array[layer][row].length; col++) {
                    System.out.print(array[layer][row][col]);
                    if (col < array[layer][row].length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println(" ]");
            }
            System.out.println();
        }
    }
    
    /**
     * Spiral traversal of a matrix
     */
    public static void spiralTraversal(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int top = 0, bottom = rows - 1;
        int left = 0, right = cols - 1;
        
        while (top <= bottom && left <= right) {
            // Print top row
            for (int i = left; i <= right; i++) {
                System.out.print(matrix[top][i] + " ");
            }
            top++;
            
            // Print right column
            for (int i = top; i <= bottom; i++) {
                System.out.print(matrix[i][right] + " ");
            }
            right--;
            
            // Print bottom row
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    System.out.print(matrix[bottom][i] + " ");
                }
                bottom--;
            }
            
            // Print left column
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    System.out.print(matrix[i][left] + " ");
                }
                left++;
            }
        }
        System.out.println();
    }
    
    /**
     * Zigzag traversal of a matrix
     */
    public static void zigzagTraversal(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        for (int i = 0; i < rows; i++) {
            if (i % 2 == 0) {
                // Left to right
                for (int j = 0; j < cols; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
            } else {
                // Right to left
                for (int j = cols - 1; j >= 0; j--) {
                    System.out.print(matrix[i][j] + " ");
                }
            }
        }
        System.out.println();
    }
    
    /**
     * In-place transpose (only for square matrices)
     */
    public static void inPlaceTranspose(int[][] matrix) {
        int size = matrix.length;
        
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                // Swap elements
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }
    
    /**
     * Create a magic square
     */
    public static int[][] createMagicSquare(int size) {
        int[][] magicSquare = new int[size][size];
        int row = 0, col = size / 2;
        
        for (int num = 1; num <= size * size; num++) {
            magicSquare[row][col] = num;
            
            int nextRow = (row - 1 + size) % size;
            int nextCol = (col + 1) % size;
            
            if (magicSquare[nextRow][nextCol] != 0) {
                nextRow = (row + 1) % size;
                nextCol = col;
            }
            
            row = nextRow;
            col = nextCol;
        }
        
        return magicSquare;
    }
    
    /**
     * Check if matrix is a magic square
     */
    public static boolean isMagicSquare(int[][] matrix) {
        if (!isSquare(matrix)) {
            return false;
        }
        
        int size = matrix.length;
        int magicConstant = size * (size * size + 1) / 2;
        
        // Check rows
        for (int i = 0; i < size; i++) {
            int rowSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += matrix[i][j];
            }
            if (rowSum != magicConstant) {
                return false;
            }
        }
        
        // Check columns
        for (int j = 0; j < size; j++) {
            int colSum = 0;
            for (int i = 0; i < size; i++) {
                colSum += matrix[i][j];
            }
            if (colSum != magicConstant) {
                return false;
            }
        }
        
        // Check diagonals
        int diag1Sum = 0, diag2Sum = 0;
        for (int i = 0; i < size; i++) {
            diag1Sum += matrix[i][i];
            diag2Sum += matrix[i][size - 1 - i];
        }
        
        return diag1Sum == magicConstant && diag2Sum == magicConstant;
    }
    
    /**
     * Get magic constant
     */
    public static int getMagicConstant(int[][] matrix) {
        int size = matrix.length;
        return size * (size * size + 1) / 2;
    }
    
    /**
     * Create number pattern
     */
    public static int[][] createNumberPattern(int size) {
        int[][] pattern = new int[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                pattern[i][j] = i + j + 1;
            }
        }
        
        return pattern;
    }
    
    /**
     * Create spiral pattern
     */
    public static int[][] createSpiralPattern(int size) {
        int[][] pattern = new int[size][size];
        int num = 1;
        int top = 0, bottom = size - 1;
        int left = 0, right = size - 1;
        
        while (top <= bottom && left <= right) {
            // Fill top row
            for (int i = left; i <= right; i++) {
                pattern[top][i] = num++;
            }
            top++;
            
            // Fill right column
            for (int i = top; i <= bottom; i++) {
                pattern[i][right] = num++;
            }
            right--;
            
            // Fill bottom row
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    pattern[bottom][i] = num++;
                }
                bottom--;
            }
            
            // Fill left column
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    pattern[i][left] = num++;
                }
                left++;
            }
        }
        
        return pattern;
    }
    
    /**
     * Check if matrix is square
     */
    public static boolean isSquare(int[][] matrix) {
        return matrix.length == matrix[0].length;
    }
    
    /**
     * Get row sums
     */
    public static int[] getRowSums(int[][] matrix) {
        int[] sums = new int[matrix.length];
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sums[i] += matrix[i][j];
            }
        }
        
        return sums;
    }
    
    /**
     * Get column sums
     */
    public static int[] getColumnSums(int[][] matrix) {
        int[] sums = new int[matrix[0].length];
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sums[j] += matrix[i][j];
            }
        }
        
        return sums;
    }
    
    /**
     * Get row averages
     */
    public static double[] getRowAverages(int[][] matrix) {
        int[] sums = getRowSums(matrix);
        double[] averages = new double[sums.length];
        
        for (int i = 0; i < sums.length; i++) {
            averages[i] = (double) sums[i] / matrix[i].length;
        }
        
        return averages;
    }
    
    /**
     * Get column averages
     */
    public static double[] getColumnAverages(int[][] matrix) {
        int[] sums = getColumnSums(matrix);
        double[] averages = new double[sums.length];
        
        for (int i = 0; i < sums.length; i++) {
            averages[i] = (double) sums[i] / matrix.length;
        }
        
        return averages;
    }
    
    /**
     * Print a matrix in a formatted way
     */
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
                if (j < matrix[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(" ]");
        }
        System.out.println();
    }
}
