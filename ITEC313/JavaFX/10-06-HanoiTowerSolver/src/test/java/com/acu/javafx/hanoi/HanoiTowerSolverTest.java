package com.acu.javafx.hanoi;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HanoiTowerSolverTest {

    @Test
    void movesCountMatchesFormula() {
        HanoiTowerSolver solver = new HanoiTowerSolver();
        for (int n = 0; n <= 8; n++) {
            List<HanoiTowerSolver.Move> moves = solver.solve(n, 'A', 'C', 'B');
            long expected = (1L << n) - 1;
            assertEquals(expected, moves.size(), "Unexpected move count for n=" + n);
        }
    }

    @Test
    void firstAndLastMovesAreCorrectForSmallN() {
        HanoiTowerSolver solver = new HanoiTowerSolver();
        List<HanoiTowerSolver.Move> moves = solver.solve(3, 'A', 'C', 'B');
        assertEquals(new HanoiTowerSolver.Move('A', 'C'), moves.get(0));
        assertEquals(new HanoiTowerSolver.Move('A', 'C'), moves.get(moves.size() - 1));
    }
}


