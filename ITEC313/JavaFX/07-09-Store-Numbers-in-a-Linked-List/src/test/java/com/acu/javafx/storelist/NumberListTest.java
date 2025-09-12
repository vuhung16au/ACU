package com.acu.javafx.storelist;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumberListTest {
    @Test
    void testUniqueInsert() {
        NumberList n = new NumberList();
        n.addUnique(3);
        n.addUnique(3);
        n.addUnique(2);
        assertEquals(2, n.size());
        assertTrue(n.joinWithSpaces().contains("3"));
        assertTrue(n.joinWithSpaces().contains("2"));
    }

    @Test
    void testSortReverseShuffle() {
        NumberList n = new NumberList();
        n.addUnique(5);
        n.addUnique(1);
        n.addUnique(3);
        n.sort();
        assertEquals("1 3 5", n.joinWithSpaces());

        n.reverse();
        assertEquals("5 3 1", n.joinWithSpaces());

        // Shuffle should have same elements and size
        n.shuffle();
        assertEquals(3, n.size());
        String s = n.joinWithSpaces();
        for (String v : new String[]{"1","3","5"}) {
            assertTrue(s.contains(v));
        }
    }
}


