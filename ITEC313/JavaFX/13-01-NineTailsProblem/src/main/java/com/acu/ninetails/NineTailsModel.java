package com.acu.ninetails;

import java.util.*;

/**
 * Core model for the N x N coin flipping problem.
 * Encoding: integer bitset with bit i = 0 for H and 1 for T.
 * A legal move flips an index i only if it is currently H (bit=0) and toggles
 * the coin at i and its 4-neighborhood (up/down/left/right).
 * Shortest path is found via BFS from target (all tails) back to any state.
 */
public final class NineTailsModel {
    private final int size;          // board dimension (N)
    private final int totalCells;    // N*N
    private final int targetState;   // all tails (all 1s)
    private final int[] neighborMask; // bit masks for flipping an index and its neighbors

    public NineTailsModel(int size) {
        if (size < 2 || size > 5) {
            // Keep it small to keep BFS memory reasonable for classroom use
            throw new IllegalArgumentException("Size must be between 2 and 5");
        }
        this.size = size;
        this.totalCells = size * size;
        this.targetState = (1 << totalCells) - 1;
        this.neighborMask = buildMasks();
    }

    public int getTargetState() {
        return targetState;
    }

    /** Build bit masks for each cell that include itself and the 4-adjacent cells. */
    private int[] buildMasks() {
        int[] masks = new int[totalCells];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                int idx = r * size + c;
                int m = 0;
                m |= 1 << idx; // self
                if (r > 0) m |= 1 << ((r - 1) * size + c);
                if (r + 1 < size) m |= 1 << ((r + 1) * size + c);
                if (c > 0) m |= 1 << (r * size + (c - 1));
                if (c + 1 < size) m |= 1 << (r * size + (c + 1));
                masks[idx] = m;
            }
        }
        return masks;
    }

    /**
     * Apply a legal move at index i if the coin is currently head (0).
     * If illegal, returns the unmodified state.
     */
    public int flip(int state, int index) {
        if (((state >> index) & 1) == 1) return state; // cannot flip a tail
        return state ^ neighborMask[index];
    }

    /** Flip regardless of legality (used by interactive UI per exercise spec). */
    public int flipAny(int state, int index) {
        return state ^ neighborMask[index];
    }

    /** Generate all states reachable from 'u' by one legal move. */
    public List<Integer> nextStates(int u) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < totalCells; i++) {
            if (((u >> i) & 1) == 0) { // head: legal move
                list.add(u ^ neighborMask[i]);
            }
        }
        return list;
    }

    /** Breadth-first search tree rooted at target, then path from s to target. */
    public List<Integer> shortestPath(int start) {
        if (start == targetState) return List.of(start);
        int total = 1 << totalCells;
        int[] parent = new int[total];
        Arrays.fill(parent, -1);
        boolean[] seen = new boolean[total];
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(targetState);
        seen[targetState] = true;

        while (!q.isEmpty()) {
            int v = q.removeFirst();
            // Reverse edges: from v, generate all u that can go to v with one legal move
            for (int i = 0; i < totalCells; i++) {
                int u = v ^ neighborMask[i];
                // An edge exists from v to u if move at i is legal in u, i.e., coin i is head in u
                if (((u >> i) & 1) == 0 && !seen[u]) {
                    seen[u] = true;
                    parent[u] = v;
                    if (u == start) return reconstructPath(parent, start);
                    q.addLast(u);
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Integer> reconstructPath(int[] parent, int s) {
        List<Integer> path = new ArrayList<>();
        int cur = s;
        while (cur != -1) {
            path.add(cur);
            if (cur == parent[cur]) break;
            cur = parent[cur];
        }
        return path;
    }

    /** Render state as 3x3 style text using H/T characters for teaching/demo. */
    public static String toBoardString(int state, int size) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                int idx = r * size + c;
                boolean tail = ((state >> idx) & 1) == 1;
                sb.append(tail ? 'T' : 'H');
            }
            if (r + 1 < size) sb.append('\n');
        }
        return sb.toString();
    }
}


