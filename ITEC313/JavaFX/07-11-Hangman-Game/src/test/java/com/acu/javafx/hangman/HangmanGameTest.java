package com.acu.javafx.hangman;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HangmanGameTest {
    @Test
    void testMaskAndGuessing() {
        HangmanGame g = new HangmanGame("receive");
        assertEquals("*******", g.getMaskedWord());
        assertTrue(g.guess('r'));
        assertTrue(g.guess('e'));
        assertEquals("re*e**e", g.getMaskedWord());
        assertFalse(g.guess('t'));
        assertEquals(1, g.missedCount());
    }

    @Test
    void testWinLose() {
        HangmanGame g = new HangmanGame("java");
        for (char c : new char[]{'j','a','v'}) g.guess(c);
        assertTrue(g.isWon());

        HangmanGame h = new HangmanGame("java");
        for (char c : new char[]{'x','y','z','q','w','t','p'}) h.guess(c);
        assertTrue(h.isLost());
    }
}


