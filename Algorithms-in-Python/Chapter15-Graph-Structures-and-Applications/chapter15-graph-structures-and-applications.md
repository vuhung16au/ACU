# Chapter 15: Graph Structures and Applications

## Introduction
This chapter introduces graphs, a powerful way to represent relationships between objects. You will learn about graph representations, traversals, shortest paths, and minimum spanning trees, with practical examples and real-world applications.

## Algorithms Implemented

### 1. Graph Representation: Adjacency List
A dictionary where each key is a node and its value is a list of neighbors.
```python
graph = {
    'A': ['B', 'C'],
    'B': ['A', 'D', 'E'],
    'C': ['A', 'F'],
    'D': ['B'],
    'E': ['B', 'F'],
    'F': ['C', 'E']
}
```

### 2. Breadth-First Search (BFS)
Visits nodes layer by layer, using a queue.
```python
from collections import deque
def bfs_traversal(graph, start_node):
    visited = set()
    queue = deque([start_node])
    print("BFS traversal order:", end=" ")
    while queue:
        node = queue.popleft()
        if node not in visited:
            print(node, end=" ")
            visited.add(node)
            for neighbor in graph[node]:
                if neighbor not in visited:
                    queue.append(neighbor)
    print()
```

### 3. Depth-First Search (DFS)
Visits as far as possible along each branch before backtracking.
```python
def dfs_traversal(graph, start_node, visited=None):
    if visited is None:
        visited = set()
        print("DFS traversal order:", end=" ")
    print(start_node, end=" ")
    visited.add(start_node)
    for neighbor in graph[start_node]:
        if neighbor not in visited:
            dfs_traversal(graph, neighbor, visited)
    if start_node == 'A':
        print()
```

### 4. Shortest Path (BFS for Unweighted Graphs)
Finds the shortest path between two nodes using BFS.
```python
def shortest_path_bfs(graph, start, goal):
    queue = deque([(start, [start])])
    visited = set()
    while queue:
        (current, path) = queue.popleft()
        if current == goal:
            return path
        visited.add(current)
        for neighbor in graph[current]:
            if neighbor not in visited:
                queue.append((neighbor, path + [neighbor]))
                visited.add(neighbor)
    return None
```

### 5. Minimum Spanning Tree (Kruskal's Algorithm)
Finds a set of edges connecting all nodes with minimum total weight.
```python
class DisjointSet:
    def __init__(self, vertices):
        self.parent = {v: v for v in vertices}
    def find(self, v):
        if self.parent[v] != v:
            self.parent[v] = self.find(self.parent[v])
        return self.parent[v]
    def union(self, v1, v2):
        root1 = self.find(v1)
        root2 = self.find(v2)
        if root1 != root2:
            self.parent[root2] = root1

def kruskal_mst(edges):
    vertices = set()
    for u, v, w in edges:
        vertices.add(u)
        vertices.add(v)
    ds = DisjointSet(vertices)
    mst = []
    sorted_edges = sorted(edges, key=lambda x: x[2])
    for u, v, weight in sorted_edges:
        if ds.find(u) != ds.find(v):
            mst.append((u, v, weight))
            ds.union(u, v)
    return mst
```

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Analysis
- **BFS/DFS traversal:** $O(V + E)$ ($V$ = vertices, $E$ = edges)
- **Shortest path (BFS):** $O(V + E)$
- **Kruskal's MST:** $O(E \log E)$ (sorting edges dominates)

#### Proof & Cases
- BFS/DFS visit every node and edge once.
- Kruskal's sorts all edges, then processes each edge.

## Important Notes
- Graphs can be directed/undirected, weighted/unweighted.
- BFS finds shortest paths in unweighted graphs.
- Kruskal's algorithm uses disjoint sets for cycle detection.

## Real-World Applications
- Social networks (friend connections)
- Maps and navigation (shortest path)
- Network design (minimum cost connections)

## Ideas for Self-Practicing
- Implement DFS iteratively (using a stack).
- Modify BFS to return all nodes at a certain distance.
- Try Prim's algorithm for minimum spanning tree.

## Further Readings & Connections
- [GeeksforGeeks: Graph Data Structure](https://www.geeksforgeeks.org/graph-data-structure-and-algorithms/)
- Learn about disjoint sets (see Chapter 14) and Dijkstra's algorithm (shortest path in weighted graphs).

---
**Key Terms:**
- **Graph:** A set of nodes (vertices) and connections (edges).
- **Traversal:** Visiting nodes in a graph.
- **Minimum Spanning Tree:** The lowest-cost set of edges connecting all nodes. 