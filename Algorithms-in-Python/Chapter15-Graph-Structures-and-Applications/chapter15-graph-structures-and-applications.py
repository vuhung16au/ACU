## Chapter 15: Graph Structures and Applications
## This tutorial introduces graphs for beginners with simple, well-commented examples.

## 1. What is a Graph?
# A graph is a collection of nodes (vertices) and connections (edges) between them.
# Graphs can be directed or undirected, weighted or unweighted.

## 2. Graph Representation: Adjacency List
## We'll use a dictionary to represent a simple undirected graph.

graph = {
    'A': ['B', 'C'],
    'B': ['A', 'D', 'E'],
    'C': ['A', 'F'],
    'D': ['B'],
    'E': ['B', 'F'],
    'F': ['C', 'E']
}

## 3. Graph Traversal: Breadth-First Search (BFS)
from collections import deque

def bfs_traversal(graph, start_node):
    """Traverse the graph using BFS and print the order of nodes visited."""
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

bfs_traversal(graph, 'A')

## 4. Graph Traversal: Depth-First Search (DFS)
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

dfs_traversal(graph, 'A')

## 5. Shortest Path: BFS for Unweighted Graphs
def shortest_path_bfs(graph, start, goal):
    """Find the shortest path from start to goal using BFS."""
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

path = shortest_path_bfs(graph, 'A', 'F')
print("Shortest path from A to F:", path)

## 6. Minimum Spanning Tree (MST): Simple Example with Kruskal's Algorithm
## For simplicity, we'll use a small weighted undirected graph and a basic Kruskal's algorithm implementation.

edges = [
    ('A', 'B', 4),
    ('A', 'C', 2),
    ('B', 'C', 1),
    ('B', 'D', 5),
    ('C', 'D', 8),
    ('C', 'E', 10),
    ('D', 'E', 2),
    ('D', 'F', 6),
    ('E', 'F', 3)
]

# Helper class for Disjoint Set (Union-Find)
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

# Kruskal's algorithm for MST
def kruskal_mst(edges):
    # Get all unique vertices
    vertices = set()
    for u, v, w in edges:
        vertices.add(u)
        vertices.add(v)
    ds = DisjointSet(vertices)
    mst = []
    # Sort edges by weight
    sorted_edges = sorted(edges, key=lambda x: x[2])
    for u, v, weight in sorted_edges:
        if ds.find(u) != ds.find(v):
            mst.append((u, v, weight))
            ds.union(u, v)
    return mst

mst = kruskal_mst(edges)
print("Minimum Spanning Tree (Kruskal's algorithm):")
for u, v, w in mst:
    print(f"Edge {u}-{v} with weight {w}")

## 7. Key Points
## - Graphs are used in navigation, social networks, scheduling, and more.
## - Traversal (BFS, DFS) is fundamental for exploring graphs.
## - BFS finds shortest paths in unweighted graphs.
## - MST algorithms (like Kruskal's) find the cheapest way to connect all nodes.

## End of Chapter 15: Graph Structures and Applications tutorial
