# Concepts

## Problem
Determine whether all circles in a 2D plane are connected.

## Modeling
- Vertex = a circle on the canvas
- Edge = circles overlap or touch (distance between centers ≤ rA + rB)

## Algorithm (DFS)
1. Build adjacency list from overlaps
2. Run DFS from any vertex
3. If all vertices are visited, the graph is connected
4. With Same Color Check on, run the check per color group

## Notes
- Overlap check uses Euclidean distance
- DFS implemented iteratively with a stack for simplicity
