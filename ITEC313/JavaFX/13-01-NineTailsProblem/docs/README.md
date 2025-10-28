# Docs – Nine Tails Problem

This module demonstrates mapping a puzzle to a shortest-path problem.

- Nodes: all 2^(N*N) boards encoded as bitsets.
- Edge u→v: one legal flip at a head in u transforms u to v.
- BFS rooted at the target (all tails) gives shortest paths from any start.

Open the app and press "Solve" to see the minimal sequence animated.


