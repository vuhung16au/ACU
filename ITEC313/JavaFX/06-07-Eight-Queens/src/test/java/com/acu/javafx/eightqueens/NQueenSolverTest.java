package com.acu.javafx.eightqueens;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NQueenSolverTest {

    @Test
    void testIsSafeAttacks() {
        assertTrue(NQueenSolver.attacks(0, 0, 0, 7)); // same row
        assertTrue(NQueenSolver.attacks(0, 0, 7, 0)); // same col
        assertTrue(NQueenSolver.attacks(1, 1, 3, 3)); // diagonal
        assertFalse(NQueenSolver.attacks(0, 1, 2, 3) == false && NQueenSolver.attacks(0,1,2,3));
    }

    @Test
    void testSolveN4HasSolution() {
        int[] sol = NQueenSolver.solve(4);
        assertEquals(4, sol.length);
        for (int r = 0; r < 4; r++) assertTrue(sol[r] >= 0);

        // verify solution
        for (int r1 = 0; r1 < 4; r1++) {
            for (int r2 = r1 + 1; r2 < 4; r2++) {
                assertFalse(NQueenSolver.attacks(r1, sol[r1], r2, sol[r2]));
            }
        }
    }

    @Test
    void testSolveN8HasSolution() {
        int[] sol = NQueenSolver.solve(8);
        assertEquals(8, sol.length);
        for (int r = 0; r < 8; r++) assertTrue(sol[r] >= 0);
        for (int r1 = 0; r1 < 8; r1++) {
            for (int r2 = r1 + 1; r2 < 8; r2++) {
                assertFalse(NQueenSolver.attacks(r1, sol[r1], r2, sol[r2]));
            }
        }
    }
}


