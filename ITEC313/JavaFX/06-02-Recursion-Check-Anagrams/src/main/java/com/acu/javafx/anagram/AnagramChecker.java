package com.acu.javafx.anagram;

import java.util.*;

/**
 * Anagram Checker - Recursive Implementation
 * 
 * This class provides methods to check if two words are anagrams of each other
 * using a recursive approach. Anagrams are words that contain the same letters
 * arranged in different orders.
 * 
 * The main algorithm uses recursion to:
 * 1. Normalize both words (remove spaces, convert to lowercase)
 * 2. Check if they have the same length
 * 3. Recursively check if all characters in one word exist in the other
 * 
 * @author ACU JavaFX Team
 * @version 1.0.0
 */
public class AnagramChecker {
    
    /**
     * Checks if two words are anagrams of each other
     * 
     * @param word1 The first word to check
     * @param word2 The second word to check
     * @return true if the words are anagrams, false otherwise
     */
    public boolean areAnagrams(String word1, String word2) {
        // Normalize both words (remove spaces, convert to lowercase)
        String normalized1 = normalizeWord(word1);
        String normalized2 = normalizeWord(word2);
        
        // If lengths are different, they cannot be anagrams
        if (normalized1.length() != normalized2.length()) {
            return false;
        }
        
        // If both words are empty, they are considered anagrams
        if (normalized1.isEmpty() && normalized2.isEmpty()) {
            return true;
        }
        
        // Use recursive approach to check if all characters match
        return areAnagramsRecursive(normalized1, normalized2);
    }
    
    /**
     * Recursive method to check if two normalized words are anagrams
     * 
     * Base case: If word1 is empty, return true (all characters matched)
     * Recursive case: 
     * 1. Find the first character of word1 in word2
     * 2. If found, remove that character from both words and recurse
     * 3. If not found, return false
     * 
     * @param word1 The first normalized word
     * @param word2 The second normalized word
     * @return true if the words are anagrams, false otherwise
     */
    private boolean areAnagramsRecursive(String word1, String word2) {
        // Base case: if word1 is empty, all characters have been matched
        if (word1.isEmpty()) {
            return true;
        }
        
        // Get the first character of word1
        char firstChar = word1.charAt(0);
        
        // Find the index of this character in word2
        int index = word2.indexOf(firstChar);
        
        // If character not found in word2, they are not anagrams
        if (index == -1) {
            return false;
        }
        
        // Remove the first character from word1 and the found character from word2
        String remainingWord1 = word1.substring(1);
        String remainingWord2 = word2.substring(0, index) + word2.substring(index + 1);
        
        // Recursively check the remaining characters
        return areAnagramsRecursive(remainingWord1, remainingWord2);
    }
    
    /**
     * Alternative recursive implementation using character frequency counting
     * This approach is more efficient for longer words
     * 
     * @param word1 The first word to check
     * @param word2 The second word to check
     * @return true if the words are anagrams, false otherwise
     */
    public boolean areAnagramsWithFrequency(String word1, String word2) {
        String normalized1 = normalizeWord(word1);
        String normalized2 = normalizeWord(word2);
        
        if (normalized1.length() != normalized2.length()) {
            return false;
        }
        
        if (normalized1.isEmpty() && normalized2.isEmpty()) {
            return true;
        }
        
        // Count character frequencies recursively
        Map<Character, Integer> freq1 = countCharactersRecursive(normalized1, new HashMap<>());
        Map<Character, Integer> freq2 = countCharactersRecursive(normalized2, new HashMap<>());
        
        return freq1.equals(freq2);
    }
    
    /**
     * Recursively counts character frequencies in a word
     * 
     * @param word The word to count characters in
     * @param frequencyMap The map to store character frequencies
     * @return Map of character frequencies
     */
    private Map<Character, Integer> countCharactersRecursive(String word, Map<Character, Integer> frequencyMap) {
        // Base case: if word is empty, return the frequency map
        if (word.isEmpty()) {
            return frequencyMap;
        }
        
        // Get the first character
        char firstChar = word.charAt(0);
        
        // Update frequency count
        frequencyMap.put(firstChar, frequencyMap.getOrDefault(firstChar, 0) + 1);
        
        // Recursively process the remaining characters
        return countCharactersRecursive(word.substring(1), frequencyMap);
    }
    
    /**
     * Normalizes a word by removing spaces and converting to lowercase
     * 
     * @param word The word to normalize
     * @return The normalized word
     */
    public String normalizeWord(String word) {
        if (word == null) {
            return "";
        }
        return word.replaceAll("\\s+", "").toLowerCase();
    }
    
    /**
     * Gets a string representation of character counts for display purposes
     * 
     * @param word The word to analyze
     * @return String representation of character counts
     */
    public String getCharacterCounts(String word) {
        Map<Character, Integer> counts = countCharactersRecursive(word, new HashMap<>());
        StringBuilder result = new StringBuilder();
        
        // Sort characters for consistent output
        List<Character> sortedChars = new ArrayList<>(counts.keySet());
        Collections.sort(sortedChars);
        
        for (char c : sortedChars) {
            result.append(c).append(":").append(counts.get(c)).append(" ");
        }
        
        return result.toString().trim();
    }
    
    /**
     * Finds all anagrams of a given word from a list of words
     * 
     * @param targetWord The word to find anagrams for
     * @param wordList The list of words to search in
     * @return List of anagrams found
     */
    public List<String> findAllAnagrams(String targetWord, List<String> wordList) {
        List<String> anagrams = new ArrayList<>();
        String normalizedTarget = normalizeWord(targetWord);
        
        for (String word : wordList) {
            if (areAnagrams(normalizedTarget, word) && !normalizedTarget.equals(normalizeWord(word))) {
                anagrams.add(word);
            }
        }
        
        return anagrams;
    }
    
    /**
     * Checks if a word is a palindrome (reads the same forwards and backwards)
     * This is a bonus recursive method for educational purposes
     * 
     * @param word The word to check
     * @return true if the word is a palindrome, false otherwise
     */
    public boolean isPalindrome(String word) {
        String normalized = normalizeWord(word);
        return isPalindromeRecursive(normalized, 0, normalized.length() - 1);
    }
    
    /**
     * Recursive method to check if a word is a palindrome
     * 
     * @param word The word to check
     * @param left The left index
     * @param right The right index
     * @return true if the word is a palindrome, false otherwise
     */
    private boolean isPalindromeRecursive(String word, int left, int right) {
        // Base case: if left >= right, we've checked all characters
        if (left >= right) {
            return true;
        }
        
        // If characters at left and right don't match, it's not a palindrome
        if (word.charAt(left) != word.charAt(right)) {
            return false;
        }
        
        // Recursively check the inner characters
        return isPalindromeRecursive(word, left + 1, right - 1);
    }
}
