package com.acu.javafx.linkedlist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for MyLinkedList implementation.
 * This class provides comprehensive testing for all LinkedList operations.
 */
public class MyLinkedListTest {
    
    private MyLinkedList<String> list;
    
    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
    }
    
    @Test
    void testAddFirst() {
        list.addFirst("first");
        assertEquals("first", list.getFirst());
        assertEquals(1, list.size());
        
        list.addFirst("second");
        assertEquals("second", list.getFirst());
        assertEquals(2, list.size());
    }
    
    @Test
    void testAddLast() {
        list.addLast("last");
        assertEquals("last", list.getLast());
        assertEquals(1, list.size());
        
        list.addLast("another");
        assertEquals("another", list.getLast());
        assertEquals(2, list.size());
    }
    
    @Test
    void testAddAtIndex() {
        list.add(0, "first");
        list.add(1, "second");
        list.add(1, "middle");
        
        assertEquals("first", list.get(0));
        assertEquals("middle", list.get(1));
        assertEquals("second", list.get(2));
        assertEquals(3, list.size());
    }
    
    @Test
    void testRemoveFirst() {
        list.addFirst("first");
        list.addFirst("second");
        
        String removed = list.removeFirst();
        assertEquals("second", removed);
        assertEquals("first", list.getFirst());
        assertEquals(1, list.size());
    }
    
    @Test
    void testRemoveLast() {
        list.addLast("first");
        list.addLast("second");
        
        String removed = list.removeLast();
        assertEquals("second", removed);
        assertEquals("first", list.getLast());
        assertEquals(1, list.size());
    }
    
    @Test
    void testRemoveAtIndex() {
        list.add("first");
        list.add("second");
        list.add("third");
        
        String removed = list.remove(1);
        assertEquals("second", removed);
        assertEquals("first", list.get(0));
        assertEquals("third", list.get(1));
        assertEquals(2, list.size());
    }
    
    @Test
    void testGet() {
        list.add("first");
        list.add("second");
        
        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
        assertNull(list.get(2)); // Out of bounds
        assertNull(list.get(-1)); // Negative index
    }
    
    @Test
    void testContains() {
        list.add("first");
        list.add("second");
        
        assertTrue(list.contains("first"));
        assertTrue(list.contains("second"));
        assertFalse(list.contains("third"));
    }
    
    @Test
    void testIndexOf() {
        list.add("first");
        list.add("second");
        list.add("first"); // Duplicate
        
        assertEquals(0, list.indexOf("first"));
        assertEquals(1, list.indexOf("second"));
        assertEquals(-1, list.indexOf("third"));
    }
    
    @Test
    void testLastIndexOf() {
        list.add("first");
        list.add("second");
        list.add("first"); // Duplicate
        
        assertEquals(2, list.lastIndexOf("first"));
        assertEquals(1, list.lastIndexOf("second"));
        assertEquals(-1, list.lastIndexOf("third"));
    }
    
    @Test
    void testSet() {
        list.add("first");
        list.add("second");
        
        String oldValue = list.set(1, "updated");
        assertEquals("second", oldValue);
        assertEquals("updated", list.get(1));
    }
    
    @Test
    void testClear() {
        list.add("first");
        list.add("second");
        
        list.clear();
        assertEquals(0, list.size());
        assertNull(list.getFirst());
        assertNull(list.getLast());
    }
    
    @Test
    void testToString() {
        assertEquals("[]", list.toString());
        
        list.add("first");
        assertEquals("[first]", list.toString());
        
        list.add("second");
        assertEquals("[first, second]", list.toString());
    }
    
    @Test
    void testIterator() {
        list.add("first");
        list.add("second");
        list.add("third");
        
        StringBuilder result = new StringBuilder();
        for (String item : list) {
            result.append(item).append(" ");
        }
        
        assertEquals("first second third ", result.toString());
    }
    
    @Test
    void testEmptyListOperations() {
        assertNull(list.getFirst());
        assertNull(list.getLast());
        assertNull(list.removeFirst());
        assertNull(list.removeLast());
        assertEquals(0, list.size());
        assertEquals("[]", list.toString());
    }
    
    @Test
    void testSingleElementOperations() {
        list.add("single");
        
        assertEquals("single", list.getFirst());
        assertEquals("single", list.getLast());
        assertEquals(1, list.size());
        
        String removed = list.removeFirst();
        assertEquals("single", removed);
        assertEquals(0, list.size());
        assertNull(list.getFirst());
        assertNull(list.getLast());
    }
} 