package com.acu.javafx.hanoi;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

/**
 * Non-recursive Tower of Hanoi solver using an explicit stack.
 * This class generates the sequence of moves to transfer n disks
 * from the source peg to the target peg using an auxiliary peg.
 *
 * The algorithm simulates the classic recursive solution by
 * pushing stack frames representing method calls.
 */
public final class HanoiTowerSolver {

    /** Represents a single move from one peg to another. */
    public static final class Move {
        public final char from;
        public final char to;

        public Move(char from, char to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return from + " -> " + to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Move m)) return false;
            return from == m.from && to == m.to;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }

    /** Internal frame to simulate recursion. */
    private static final class Frame {
        final int n;
        final char from;
        final char to;
        final char aux;
        int state; // 0: before left call, 1: between left and right (do move)

        Frame(int n, char from, char to, char aux, int state) {
            this.n = n;
            this.from = from;
            this.to = to;
            this.aux = aux;
            this.state = state;
        }
    }

    /**
     * Solve Tower of Hanoi non-recursively.
     * @param n number of disks (>= 0)
     * @param from source peg label, e.g., 'A'
     * @param to target peg label, e.g., 'C'
     * @param aux auxiliary peg label, e.g., 'B'
     * @return list of moves, in order
     */
    public List<Move> solve(int n, char from, char to, char aux) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");

        List<Move> moves = new ArrayList<>();
        Deque<Frame> stack = new ArrayDeque<>();
        stack.push(new Frame(n, from, to, aux, 0));

        while (!stack.isEmpty()) {
            Frame f = stack.pop();
            if (f.n == 0) {
                // Base case: no action
                continue;
            }

            if (f.state == 0) {
                // Simulate: hanoi(n-1, from, aux, to); then move; then hanoi(n-1, aux, to, from)
                f.state = 1;
                stack.push(f); // Return here after left side
                stack.push(new Frame(f.n - 1, f.from, f.aux, f.to, 0));
            } else {
                moves.add(new Move(f.from, f.to));
                stack.push(new Frame(f.n - 1, f.aux, f.to, f.from, 0));
            }
        }
        return moves;
    }
}


