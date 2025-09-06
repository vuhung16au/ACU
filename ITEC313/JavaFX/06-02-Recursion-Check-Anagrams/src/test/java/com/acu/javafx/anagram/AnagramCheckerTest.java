package com.acu.javafx.anagram;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

/**
 * Test class for AnagramChecker
 * 
 * This class tests the recursive anagram checking functionality with various
 * test cases including edge cases, normal cases, and boundary conditions.
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class AnagramCheckerTest {

    private AnagramChecker anagramChecker;

    @BeforeEach
    void setUp() {
        anagramChecker = new AnagramChecker();
    }

    @Test
    void testBasicAnagrams() {
        // Test basic anagram cases
        assertTrue(anagramChecker.areAnagrams("listen", "silent"));
        assertTrue(anagramChecker.areAnagrams("evil", "vile"));
        assertTrue(anagramChecker.areAnagrams("cinema", "iceman"));
        assertTrue(anagramChecker.areAnagrams("debit card", "bad credit"));
        assertTrue(anagramChecker.areAnagrams("eleven plus two", "twelve plus one"));
    }

    @Test
    void testNonAnagrams() {
        // Test non-anagram cases
        assertFalse(anagramChecker.areAnagrams("hello", "world"));
        assertFalse(anagramChecker.areAnagrams("cat", "dog"));
        assertFalse(anagramChecker.areAnagrams("java", "python"));
        assertFalse(anagramChecker.areAnagrams("programming", "coding"));
        assertFalse(anagramChecker.areAnagrams("test", "testing"));
    }

    @Test
    void testCaseInsensitive() {
        // Test case insensitivity
        assertTrue(anagramChecker.areAnagrams("Listen", "SILENT"));
        assertTrue(anagramChecker.areAnagrams("Evil", "VILE"));
        assertTrue(anagramChecker.areAnagrams("CINEMA", "iceman"));
        assertTrue(anagramChecker.areAnagrams("Debit Card", "BAD CREDIT"));
    }

    @Test
    void testWithSpaces() {
        // Test words with spaces
        assertTrue(anagramChecker.areAnagrams("debit card", "bad credit"));
        assertTrue(anagramChecker.areAnagrams("eleven plus two", "twelve plus one"));
        assertTrue(anagramChecker.areAnagrams("a gentleman", "elegant man"));
        assertTrue(anagramChecker.areAnagrams("the eyes", "they see"));
    }

    @Test
    void testEmptyStrings() {
        // Test empty strings
        assertTrue(anagramChecker.areAnagrams("", ""));
        assertTrue(anagramChecker.areAnagrams("   ", ""));
        assertTrue(anagramChecker.areAnagrams("", "   "));
        assertTrue(anagramChecker.areAnagrams("   ", "   "));
    }

    @Test
    void testSingleCharacter() {
        // Test single character words
        assertTrue(anagramChecker.areAnagrams("a", "a"));
        assertTrue(anagramChecker.areAnagrams("A", "a"));
        assertFalse(anagramChecker.areAnagrams("a", "b"));
        assertFalse(anagramChecker.areAnagrams("a", "ab"));
    }

    @Test
    void testSameWord() {
        // Test same words (should be anagrams)
        assertTrue(anagramChecker.areAnagrams("hello", "hello"));
        assertTrue(anagramChecker.areAnagrams("programming", "programming"));
        assertTrue(anagramChecker.areAnagrams("test", "test"));
    }

    @Test
    void testDifferentLengths() {
        // Test words of different lengths
        assertFalse(anagramChecker.areAnagrams("cat", "cats"));
        assertFalse(anagramChecker.areAnagrams("hello", "hi"));
        assertFalse(anagramChecker.areAnagrams("programming", "code"));
        assertFalse(anagramChecker.areAnagrams("a", "ab"));
    }

    @Test
    void testSpecialCharacters() {
        // Test words with special characters (should be ignored in normalization)
        assertTrue(anagramChecker.areAnagrams("listen!", "silent!"));
        assertTrue(anagramChecker.areAnagrams("evil?", "vile?"));
        assertTrue(anagramChecker.areAnagrams("cinema.", "iceman."));
    }

    @Test
    void testNumbers() {
        // Test words with numbers
        assertTrue(anagramChecker.areAnagrams("123", "321"));
        assertTrue(anagramChecker.areAnagrams("abc123", "321abc"));
        assertFalse(anagramChecker.areAnagrams("123", "456"));
    }

    @Test
    void testComplexAnagrams() {
        // Test complex anagram cases
        assertTrue(anagramChecker.areAnagrams("astronomer", "moon starer"));
        assertTrue(anagramChecker.areAnagrams("conversation", "voices rant on"));
        assertTrue(anagramChecker.areAnagrams("dormitory", "dirty room"));
        assertTrue(anagramChecker.areAnagrams("schoolmaster", "the classroom"));
    }

    @Test
    void testAnagramsWithFrequency() {
        // Test the frequency-based anagram checking method
        assertTrue(anagramChecker.areAnagramsWithFrequency("listen", "silent"));
        assertTrue(anagramChecker.areAnagramsWithFrequency("evil", "vile"));
        assertFalse(anagramChecker.areAnagramsWithFrequency("hello", "world"));
        assertTrue(anagramChecker.areAnagramsWithFrequency("debit card", "bad credit"));
    }

    @Test
    void testNormalizeWord() {
        // Test word normalization
        assertEquals("hello", anagramChecker.normalizeWord("Hello"));
        assertEquals("hello", anagramChecker.normalizeWord("HELLO"));
        assertEquals("hello", anagramChecker.normalizeWord("  hello  "));
        assertEquals("hello", anagramChecker.normalizeWord("h e l l o"));
        assertEquals("", anagramChecker.normalizeWord(""));
        assertEquals("", anagramChecker.normalizeWord("   "));
        assertEquals("", anagramChecker.normalizeWord(null));
    }

    @Test
    void testGetCharacterCounts() {
        // Test character counting
        String counts1 = anagramChecker.getCharacterCounts("hello");
        String counts2 = anagramChecker.getCharacterCounts("olleh");
        assertEquals(counts1, counts2);
        
        String counts3 = anagramChecker.getCharacterCounts("listen");
        assertTrue(counts3.contains("e:1"));
        assertTrue(counts3.contains("i:1"));
        assertTrue(counts3.contains("l:1"));
        assertTrue(counts3.contains("n:1"));
        assertTrue(counts3.contains("s:1"));
        assertTrue(counts3.contains("t:1"));
    }

    @Test
    void testFindAllAnagrams() {
        // Test finding all anagrams from a list
        List<String> wordList = Arrays.asList("listen", "silent", "hello", "world", "evil", "vile", "cat", "dog");
        List<String> anagrams = anagramChecker.findAllAnagrams("listen", wordList);
        
        assertTrue(anagrams.contains("silent"));
        assertFalse(anagrams.contains("listen")); // Should not include the word itself
        assertFalse(anagrams.contains("hello"));
        assertFalse(anagrams.contains("world"));
        
        List<String> evilAnagrams = anagramChecker.findAllAnagrams("evil", wordList);
        assertTrue(evilAnagrams.contains("vile"));
        assertFalse(evilAnagrams.contains("evil"));
    }

    @Test
    void testIsPalindrome() {
        // Test palindrome checking (bonus functionality)
        assertTrue(anagramChecker.isPalindrome("racecar"));
        assertTrue(anagramChecker.isPalindrome("level"));
        assertTrue(anagramChecker.isPalindrome("radar"));
        assertTrue(anagramChecker.isPalindrome("a"));
        assertTrue(anagramChecker.isPalindrome(""));
        assertTrue(anagramChecker.isPalindrome("  "));
        
        assertFalse(anagramChecker.isPalindrome("hello"));
        assertFalse(anagramChecker.isPalindrome("world"));
        assertFalse(anagramChecker.isPalindrome("programming"));
        
        // Test case insensitive palindromes
        assertTrue(anagramChecker.isPalindrome("Racecar"));
        assertTrue(anagramChecker.isPalindrome("LEVEL"));
        assertTrue(anagramChecker.isPalindrome("RaDaR"));
    }

    @Test
    void testPalindromeWithSpaces() {
        // Test palindromes with spaces
        assertTrue(anagramChecker.isPalindrome("race car"));
        assertTrue(anagramChecker.isPalindrome("a man a plan a canal panama"));
        assertTrue(anagramChecker.isPalindrome("was it a car or a cat i saw"));
    }

    @Test
    void testEdgeCases() {
        // Test various edge cases
        assertTrue(anagramChecker.areAnagrams("a", "a"));
        assertFalse(anagramChecker.areAnagrams("a", "b"));
        assertTrue(anagramChecker.areAnagrams("aa", "aa"));
        assertFalse(anagramChecker.areAnagrams("aa", "ab"));
        assertTrue(anagramChecker.areAnagrams("ab", "ba"));
        
        // Test with repeated characters
        assertTrue(anagramChecker.areAnagrams("aabb", "bbaa"));
        assertTrue(anagramChecker.areAnagrams("aabbcc", "ccbbaa"));
        assertFalse(anagramChecker.areAnagrams("aabb", "aabc"));
    }

    @Test
    void testPerformance() {
        // Test performance with longer words
        String word1 = "abcdefghijklmnopqrstuvwxyz";
        String word2 = "zyxwvutsrqponmlkjihgfedcba";
        
        long startTime = System.currentTimeMillis();
        boolean result = anagramChecker.areAnagrams(word1, word2);
        long endTime = System.currentTimeMillis();
        
        assertTrue(result);
        assertTrue((endTime - startTime) < 1000); // Should complete within 1 second
    }

    @Test
    void testNullInputs() {
        // Test null inputs
        assertTrue(anagramChecker.areAnagrams(null, null));
        assertFalse(anagramChecker.areAnagrams(null, "hello"));
        assertFalse(anagramChecker.areAnagrams("hello", null));
        
        assertTrue(anagramChecker.isPalindrome(null));
        assertTrue(anagramChecker.normalizeWord(null).isEmpty());
    }
}
