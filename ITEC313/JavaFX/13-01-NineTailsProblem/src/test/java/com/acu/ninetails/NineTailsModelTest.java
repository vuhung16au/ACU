package com.acu.ninetails;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class NineTailsModelTest {

    @Test
    void flip_is_legal_only_on_heads() {
        NineTailsModel m = new NineTailsModel(3);
        int start = 0; // all heads
        int after = m.flip(start, 0);
        assertNotEquals(start, after);
        // make it a tail at 0; cannot flip again
        assertEquals(after, m.flip(after, 0));
    }

    @Test
    void nextStates_size_matches_heads() {
        NineTailsModel m = new NineTailsModel(3);
        int state = 0b101010101; // 5 tails, 4 heads
        List<Integer> ns = m.nextStates(state);
        assertEquals(4, ns.size());
    }

    @Test
    void bfs_returns_path_including_start() {
        NineTailsModel m = new NineTailsModel(3);
        int state = 0; // all heads
        List<Integer> path = m.shortestPath(state);
        assertFalse(path.isEmpty());
        assertEquals(state, path.get(0));
        assertEquals(m.getTargetState(), path.get(path.size()-1));
    }
}


