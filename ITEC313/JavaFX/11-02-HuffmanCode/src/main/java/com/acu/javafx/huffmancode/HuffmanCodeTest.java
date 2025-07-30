package com.acu.javafx.huffmancode;

/**
 * Simple test class to verify Huffman algorithm functionality
 */
public class HuffmanCodeTest {
    
    public static void main(String[] args) {
        System.out.println("=== Huffman Code Algorithm Test ===");
        
        // Test with a simple string
        String testText = "Hello World!";
        System.out.println("Test text: " + testText);
        
        // Get character frequencies
        int[] counts = HuffmanCode.getCharacterFrequency(testText);
        System.out.println("\nCharacter frequencies:");
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > 0) {
                System.out.printf("'%c' (ASCII %d): %d times%n", (char)i, i, counts[i]);
            }
        }
        
        // Create Huffman tree and get codes
        HuffmanCode.Tree tree = HuffmanCode.getHuffmanTree(counts);
        String[] codes = HuffmanCode.getCode(tree.root);
        
        System.out.println("\nHuffman codes:");
        for (int i = 0; i < codes.length; i++) {
            if (counts[i] > 0 && codes[i] != null) {
                System.out.printf("'%c': %s%n", (char)i, codes[i]);
            }
        }
        
        // Encode the text
        StringBuilder encoded = new StringBuilder();
        for (char c : testText.toCharArray()) {
            if (codes[c] != null) {
                encoded.append(codes[c]);
            }
        }
        
        System.out.println("\nEncoded text: " + encoded.toString());
        
        // Calculate compression
        int originalBits = testText.length() * 8;
        int encodedBits = encoded.length();
        double compressionRatio = (double) encodedBits / originalBits * 100;
        
        System.out.printf("\nCompression analysis:");
        System.out.printf("\nOriginal bits: %d", originalBits);
        System.out.printf("\nEncoded bits: %d", encodedBits);
        System.out.printf("\nCompression ratio: %.1f%%", compressionRatio);
        
        System.out.println("\n\nTest completed successfully!");
    }
} 