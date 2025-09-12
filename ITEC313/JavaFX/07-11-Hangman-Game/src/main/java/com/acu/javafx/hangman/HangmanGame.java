package com.acu.javafx.hangman;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Core hangman logic: track word, guessed letters, misses and status.
 * Designed to be tiny and easy to test.
 */
public class HangmanGame {
    private static final String[] WORDS = {
            "receive", "computer", "science", "java", "university", "education"
    };

    private final String word;
    private final Set<Character> guessed = new HashSet<>();
    private final Set<Character> missed = new HashSet<>();

    public HangmanGame() { this(randomWord()); }
    public HangmanGame(String word) { this.word = word.toLowerCase(); }

    public static String randomWord() {
        return WORDS[new Random().nextInt(WORDS.length)];
    }

    /** Guess one letter; returns true if the guess is in the word. */
    public boolean guess(char ch) {
        ch = Character.toLowerCase(ch);
        if (!Character.isLetter(ch)) return false;
        if (guessed.contains(ch) || missed.contains(ch)) return contains(ch);
        if (contains(ch)) { guessed.add(ch); return true; }
        missed.add(ch); return false;
    }

    private boolean contains(char ch) { return word.indexOf(ch) >= 0; }

    /** Display form like r*ce*ve for the word receive. */
    public String getMaskedWord() {
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) sb.append(guessed.contains(c) ? c : '*');
        return sb.toString();
    }

    public String getMissedLetters() {
        StringBuilder sb = new StringBuilder();
        for (char c : missed) sb.append(c);
        return sb.toString();
    }

    public boolean isWon() { return !getMaskedWord().contains("*"); }
    public boolean isLost() { return missed.size() >= 7; }
    public String getWord() { return word; }
    public int missedCount() { return missed.size(); }
}


