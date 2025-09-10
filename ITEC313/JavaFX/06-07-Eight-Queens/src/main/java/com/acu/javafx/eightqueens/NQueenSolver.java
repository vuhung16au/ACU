package com.acu.javafx.eightqueens;

import java.util.ArrayList;
import java.util.List;

/**
 * Backtracking solver for the N-Queens problem.
 * Exposes a minimal API returning one valid placement and a step trace
 * that can be used by the UI to animate the search.
 */
public final class NQueenSolver {
    private NQueenSolver() {}

    public enum StepType { PLACE, REMOVE, MARK }

    public static final class Step {
        public final StepType type;
        public final int row;
        public final int col;
        Step(StepType type, int row, int col) { this.type = type; this.row = row; this.col = col; }
    }

    /** Solve and return one solution as an array of columns for each row. */
    public static int[] solve(int n) {
        List<Step> ignore = new ArrayList<>();
        return solveWithSteps(n, ignore);
    }

    /** Solve and append step trace into the provided list. */
    public static int[] solveWithSteps(int n, List<Step> steps) {
        int[] cols = new int[n];
        for (int i = 0; i < n; i++) cols[i] = -1;
        backtrack(0, cols, n, steps);
        return cols;
    }

    private static boolean backtrack(int row, int[] cols, int n, List<Step> steps) {
        if (row == n) return true; // placed all queens

        for (int c = 0; c < n; c++) {
            if (isSafe(row, c, cols)) {
                cols[row] = c;
                steps.add(new Step(StepType.PLACE, row, c));
                steps.add(new Step(StepType.MARK, row, c));
                if (backtrack(row + 1, cols, n, steps)) return true;
                // backtrack
                steps.add(new Step(StepType.REMOVE, row, c));
                cols[row] = -1;
            }
        }
        return false;
    }

    /** True if position (row2,col2) is attacked by queen at (row1,col1). */
    public static boolean attacks(int row1, int col1, int row2, int col2) {
        return row1 == row2 || col1 == col2 || (Math.abs(row1 - row2) == Math.abs(col1 - col2));
    }

    /** Check if a queen can be placed at (row,col) given previously placed queens. */
    public static boolean isSafe(int row, int col, int[] cols) {
        for (int r = 0; r < row; r++) {
            int c = cols[r];
            if (c == -1) continue;
            if (attacks(r, c, row, col)) return false;
        }
        return true;
    }
}


