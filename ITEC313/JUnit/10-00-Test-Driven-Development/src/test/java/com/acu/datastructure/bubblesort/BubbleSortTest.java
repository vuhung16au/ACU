package com.acu.datastructure.bubblesort;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test-Driven Development example for Bubble Sort algorithm.
 * 
 * Students should write these tests FIRST, then implement the BubbleSort class.
 * Follow the TDD cycle: Red → Green → Refactor
 */
@DisplayName("Bubble Sort TDD Tests")
class BubbleSortTest {
    
    private BubbleSort bubbleSort;
    
    @BeforeEach
    void setUp() {
        bubbleSort = new BubbleSort();
    }
    
    @Nested
    @DisplayName("Basic Sorting Tests")
    class BasicSortingTests {
        
        @Test
        @DisplayName("Should sort an unsorted array in ascending order")
        void shouldSortUnsortedArray() {
            // Given
            int[] unsortedArray = {64, 34, 25, 12, 22, 11, 90};
            int[] expectedArray = {11, 12, 22, 25, 34, 64, 90};
            
            // When
            int[] result = bubbleSort.sort(unsortedArray);
            
            // Then
            assertArrayEquals(expectedArray, result);
        }
        
        @Test
        @DisplayName("Should handle already sorted array")
        void shouldHandleAlreadySortedArray() {
            // Given
            int[] sortedArray = {1, 2, 3, 4, 5};
            int[] expectedArray = {1, 2, 3, 4, 5};
            
            // When
            int[] result = bubbleSort.sort(sortedArray);
            
            // Then
            assertArrayEquals(expectedArray, result);
        }
        
        @Test
        @DisplayName("Should sort array with duplicate elements")
        void shouldSortArrayWithDuplicates() {
            // Given
            int[] arrayWithDuplicates = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
            int[] expectedArray = {1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9};
            
            // When
            int[] result = bubbleSort.sort(arrayWithDuplicates);
            
            // Then
            assertArrayEquals(expectedArray, result);
        }
        
        @ParameterizedTest
        @CsvSource({
            "'1,2,3,4,5', '1,2,3,4,5'",
            "'5,4,3,2,1', '1,2,3,4,5'",
            "'3,1,4,1,5', '1,1,3,4,5'",
            "'42', '42'"
        })
        @DisplayName("Should sort various array configurations")
        void shouldSortVariousArrayConfigurations(String input, String expected) {
            // Given
            int[] inputArray = parseArray(input);
            int[] expectedArray = parseArray(expected);
            
            // When
            int[] result = bubbleSort.sort(inputArray);
            
            // Then
            assertArrayEquals(expectedArray, result);
        }
    }
    
    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {
        
        @Test
        @DisplayName("Should handle empty array")
        void shouldHandleEmptyArray() {
            // Given
            int[] emptyArray = {};
            int[] expectedArray = {};
            
            // When
            int[] result = bubbleSort.sort(emptyArray);
            
            // Then
            assertArrayEquals(expectedArray, result);
        }
        
        @Test
        @DisplayName("Should handle single element array")
        void shouldHandleSingleElementArray() {
            // Given
            int[] singleElementArray = {42};
            int[] expectedArray = {42};
            
            // When
            int[] result = bubbleSort.sort(singleElementArray);
            
            // Then
            assertArrayEquals(expectedArray, result);
        }
        
        @Test
        @DisplayName("Should handle null array")
        void shouldHandleNullArray() {
            // Given
            int[] nullArray = null;
            
            // When & Then
            assertThrows(IllegalArgumentException.class, () -> {
                bubbleSort.sort(nullArray);
            });
        }
        
        @Test
        @DisplayName("Should handle array with negative numbers")
        void shouldHandleArrayWithNegativeNumbers() {
            // Given
            int[] arrayWithNegatives = {-3, -1, -4, -1, -5};
            int[] expectedArray = {-5, -4, -3, -1, -1};
            
            // When
            int[] result = bubbleSort.sort(arrayWithNegatives);
            
            // Then
            assertArrayEquals(expectedArray, result);
        }
        
        @Test
        @DisplayName("Should handle array with mixed positive and negative numbers")
        void shouldHandleArrayWithMixedNumbers() {
            // Given
            int[] mixedArray = {-3, 1, -4, 1, 5, -2};
            int[] expectedArray = {-4, -3, -2, 1, 1, 5};
            
            // When
            int[] result = bubbleSort.sort(mixedArray);
            
            // Then
            assertArrayEquals(expectedArray, result);
        }
    }
    
    @Nested
    @DisplayName("Descending Sort Tests")
    class DescendingSortTests {
        
        @Test
        @DisplayName("Should sort array in descending order")
        void shouldSortArrayInDescendingOrder() {
            // Given
            int[] unsortedArray = {64, 34, 25, 12, 22, 11, 90};
            int[] expectedArray = {90, 64, 34, 25, 22, 12, 11};
            
            // When
            int[] result = bubbleSort.sortDescending(unsortedArray);
            
            // Then
            assertArrayEquals(expectedArray, result);
        }
        
        @Test
        @DisplayName("Should handle already descending sorted array")
        void shouldHandleAlreadyDescendingSortedArray() {
            // Given
            int[] descendingArray = {5, 4, 3, 2, 1};
            int[] expectedArray = {5, 4, 3, 2, 1};
            
            // When
            int[] result = bubbleSort.sortDescending(descendingArray);
            
            // Then
            assertArrayEquals(expectedArray, result);
        }
    }
    
    @Nested
    @DisplayName("Utility Method Tests")
    class UtilityMethodTests {
        
        @Test
        @DisplayName("Should correctly identify sorted array")
        void shouldCorrectlyIdentifySortedArray() {
            // Given
            int[] sortedArray = {1, 2, 3, 4, 5};
            
            // When
            boolean isSorted = bubbleSort.isSorted(sortedArray);
            
            // Then
            assertTrue(isSorted);
        }
        
        @Test
        @DisplayName("Should correctly identify unsorted array")
        void shouldCorrectlyIdentifyUnsortedArray() {
            // Given
            int[] unsortedArray = {1, 3, 2, 4, 5};
            
            // When
            boolean isSorted = bubbleSort.isSorted(unsortedArray);
            
            // Then
            assertFalse(isSorted);
        }
        
        @Test
        @DisplayName("Should handle empty array in isSorted check")
        void shouldHandleEmptyArrayInIsSortedCheck() {
            // Given
            int[] emptyArray = {};
            
            // When
            boolean isSorted = bubbleSort.isSorted(emptyArray);
            
            // Then
            assertTrue(isSorted);
        }
        
        @Test
        @DisplayName("Should handle null array in isSorted check")
        void shouldHandleNullArrayInIsSortedCheck() {
            // Given
            int[] nullArray = null;
            
            // When & Then
            assertThrows(IllegalArgumentException.class, () -> {
                bubbleSort.isSorted(nullArray);
            });
        }
    }
    
    @Nested
    @DisplayName("Performance and Optimization Tests")
    class PerformanceTests {
        
        @Test
        @DisplayName("Should handle large array efficiently")
        void shouldHandleLargeArrayEfficiently() {
            // Given
            int[] largeArray = new int[1000];
            for (int i = 0; i < largeArray.length; i++) {
                largeArray[i] = largeArray.length - i; // Reverse sorted
            }
            
            // When
            long startTime = System.nanoTime();
            int[] result = bubbleSort.sort(largeArray);
            long endTime = System.nanoTime();
            
            // Then
            assertTrue(bubbleSort.isSorted(result));
            assertTrue((endTime - startTime) < 1_000_000_000); // Should complete within 1 second
        }
        
        @Test
        @DisplayName("Should optimize for already sorted arrays")
        void shouldOptimizeForAlreadySortedArrays() {
            // Given
            int[] sortedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            
            // When
            long startTime = System.nanoTime();
            int[] result = bubbleSort.sort(sortedArray);
            long endTime = System.nanoTime();
            
            // Then
            assertArrayEquals(sortedArray, result);
            // Should be very fast for already sorted arrays due to early termination
            assertTrue((endTime - startTime) < 100_000); // Should be very fast
        }
    }
    
    /**
     * Helper method to parse comma-separated string into integer array
     */
    private int[] parseArray(String arrayString) {
        if (arrayString == null || arrayString.trim().isEmpty()) {
            return new int[0];
        }
        
        String[] parts = arrayString.split(",");
        int[] result = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            result[i] = Integer.parseInt(parts[i].trim());
        }
        return result;
    }
}
