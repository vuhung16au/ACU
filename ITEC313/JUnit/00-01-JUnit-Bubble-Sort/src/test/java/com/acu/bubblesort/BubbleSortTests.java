package com.acu.bubblesort;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the BubbleSort class demonstrating JUnit 5 features.
 */
@DisplayName("Bubble Sort Tests")
class BubbleSortTests {
    
    private BubbleSort bubbleSort;
    
    @BeforeEach
    void setUp() {
        bubbleSort = new BubbleSort();
    }
    
    @Nested
    @DisplayName("Sort Tests")
    class SortTests {
        
        @Test
        @DisplayName("Should sort a normal array")
        void shouldSortNormalArray() {
            int[] arr = {64, 34, 25, 12, 22, 11, 90};
            int[] expected = {11, 12, 22, 25, 34, 64, 90};
            
            int[] result = bubbleSort.sort(arr);
            
            assertArrayEquals(expected, result);
            assertTrue(bubbleSort.isSorted(result));
        }
        
        @Test
        @DisplayName("Should handle already sorted array")
        void shouldHandleAlreadySortedArray() {
            int[] arr = {1, 2, 3, 4, 5};
            int[] expected = {1, 2, 3, 4, 5};
            
            int[] result = bubbleSort.sort(arr);
            
            assertArrayEquals(expected, result);
            assertTrue(bubbleSort.isSorted(result));
        }
        
        @Test
        @DisplayName("Should handle reverse sorted array")
        void shouldHandleReverseSortedArray() {
            int[] arr = {5, 4, 3, 2, 1};
            int[] expected = {1, 2, 3, 4, 5};
            
            int[] result = bubbleSort.sort(arr);
            
            assertArrayEquals(expected, result);
            assertTrue(bubbleSort.isSorted(result));
        }
        


        
        @Test
        @DisplayName("Should handle single element array")
        void shouldHandleSingleElementArray() {
            int[] arr = {42};
            int[] expected = {42};
            
            int[] result = bubbleSort.sort(arr);
            
            assertArrayEquals(expected, result);
            assertTrue(bubbleSort.isSorted(result));
        }
        
        @Test
        @DisplayName("Should handle empty array")
        void shouldHandleEmptyArray() {
            int[] arr = {};
            int[] expected = {};
            
            int[] result = bubbleSort.sort(arr);
            
            assertArrayEquals(expected, result);
            assertTrue(bubbleSort.isSorted(result));
        }
        

 
    }
    

}
