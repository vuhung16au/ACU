# Graph Theory: Practical Examples and Code Implementations

## Table of Contents
1. [Basic Graph Implementation](#basic-graph-implementation)
2. [Traversal Examples](#traversal-examples)
3. [Shortest Path Examples](#shortest-path-examples)
4. [Real-World Problem Examples](#real-world-problem-examples)
5. [Advanced Algorithm Examples](#advanced-algorithm-examples)
6. [Visualization Examples](#visualization-examples)

---

## Basic Graph Implementation

### Java Implementation

```java
import java.util.*;

public class Graph {
    private Map<Integer, List<Integer>> adjacencyList;
    private boolean isDirected;
    
    public Graph(boolean isDirected) {
        this.adjacencyList = new HashMap<>();
        this.isDirected = isDirected;
    }
    
    public void addVertex(int vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }
    
    public void addEdge(int from, int to) {
        addVertex(from);
        addVertex(to);
        adjacencyList.get(from).add(to);
        
        if (!isDirected) {
            adjacencyList.get(to).add(from);
        }
    }
    
    public List<Integer> getNeighbors(int vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }
    
    public void printGraph() {
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
```

### Python Implementation

```python
from collections import defaultdict, deque

class Graph:
    def __init__(self, directed=False):
        self.graph = defaultdict(list)
        self.directed = directed
    
    def add_vertex(self, vertex):
        if vertex not in self.graph:
            self.graph[vertex] = []
    
    def add_edge(self, from_vertex, to_vertex):
        self.add_vertex(from_vertex)
        self.add_vertex(to_vertex)
        self.graph[from_vertex].append(to_vertex)
        
        if not self.directed:
            self.graph[to_vertex].append(from_vertex)
    
    def get_neighbors(self, vertex):
        return self.graph.get(vertex, [])
    
    def print_graph(self):
        for vertex, neighbors in self.graph.items():
            print(f"{vertex} -> {neighbors}")
```

---

## Traversal Examples

### 1. Depth-First Search (DFS)

#### Java Implementation
```java
public class GraphTraversal {
    private Set<Integer> visited;
    
    public void dfs(Graph graph, int start) {
        visited = new HashSet<>();
        dfsHelper(graph, start);
    }
    
    private void dfsHelper(Graph graph, int vertex) {
        visited.add(vertex);
        System.out.print(vertex + " ");
        
        for (int neighbor : graph.getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                dfsHelper(graph, neighbor);
            }
        }
    }
    
    // Iterative DFS using stack
    public void dfsIterative(Graph graph, int start) {
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        
        stack.push(start);
        
        while (!stack.isEmpty()) {
            int vertex = stack.pop();
            
            if (!visited.contains(vertex)) {
                visited.add(vertex);
                System.out.print(vertex + " ");
                
                for (int neighbor : graph.getNeighbors(vertex)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }
}
```

#### Python Implementation
```python
def dfs_recursive(graph, start, visited=None):
    if visited is None:
        visited = set()
    
    visited.add(start)
    print(start, end=" ")
    
    for neighbor in graph.get_neighbors(start):
        if neighbor not in visited:
            dfs_recursive(graph, neighbor, visited)

def dfs_iterative(graph, start):
    visited = set()
    stack = [start]
    
    while stack:
        vertex = stack.pop()
        
        if vertex not in visited:
            visited.add(vertex)
            print(vertex, end=" ")
            
            for neighbor in graph.get_neighbors(vertex):
                if neighbor not in visited:
                    stack.append(neighbor)
```

### 2. Breadth-First Search (BFS)

#### Java Implementation
```java
public void bfs(Graph graph, int start) {
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();
    
    visited.add(start);
    queue.offer(start);
    
    while (!queue.isEmpty()) {
        int vertex = queue.poll();
        System.out.print(vertex + " ");
        
        for (int neighbor : graph.getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                queue.offer(neighbor);
            }
        }
    }
}
```

#### Python Implementation
```python
def bfs(graph, start):
    visited = set()
    queue = deque([start])
    visited.add(start)
    
    while queue:
        vertex = queue.popleft()
        print(vertex, end=" ")
        
        for neighbor in graph.get_neighbors(vertex):
            if neighbor not in visited:
                visited.add(neighbor)
                queue.append(neighbor)
```

---

## Shortest Path Examples

### 1. BFS for Unweighted Graphs

```java
public class ShortestPath {
    public List<Integer> shortestPathBFS(Graph graph, int start, int end) {
        Map<Integer, Integer> parent = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        
        visited.add(start);
        queue.offer(start);
        parent.put(start, -1);
        
        while (!queue.isEmpty()) {
            int current = queue.poll();
            
            if (current == end) {
                return reconstructPath(parent, start, end);
            }
            
            for (int neighbor : graph.getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parent.put(neighbor, current);
                    queue.offer(neighbor);
                }
            }
        }
        
        return new ArrayList<>(); // No path found
    }
    
    private List<Integer> reconstructPath(Map<Integer, Integer> parent, int start, int end) {
        List<Integer> path = new ArrayList<>();
        int current = end;
        
        while (current != -1) {
            path.add(current);
            current = parent.get(current);
        }
        
        Collections.reverse(path);
        return path;
    }
}
```

### 2. Dijkstra's Algorithm for Weighted Graphs

```java
public class Dijkstra {
    public Map<Integer, Integer> dijkstra(WeightedGraph graph, int start) {
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        
        // Initialize distances
        for (int vertex : graph.getVertices()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        
        pq.offer(new int[]{start, 0});
        
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int vertex = current[0];
            int distance = current[1];
            
            if (distance > distances.get(vertex)) {
                continue;
            }
            
            for (Map.Entry<Integer, Integer> neighbor : graph.getNeighbors(vertex).entrySet()) {
                int neighborVertex = neighbor.getKey();
                int edgeWeight = neighbor.getValue();
                int newDistance = distance + edgeWeight;
                
                if (newDistance < distances.get(neighborVertex)) {
                    distances.put(neighborVertex, newDistance);
                    parent.put(neighborVertex, vertex);
                    pq.offer(new int[]{neighborVertex, newDistance});
                }
            }
        }
        
        return distances;
    }
}
```

---

## Real-World Problem Examples

### 1. Social Network Analysis

```java
public class SocialNetwork {
    private Graph friendshipGraph;
    
    public SocialNetwork() {
        this.friendshipGraph = new Graph(false); // Undirected graph
    }
    
    public void addFriendship(int person1, int person2) {
        friendshipGraph.addEdge(person1, person2);
    }
    
    // Find mutual friends between two people
    public List<Integer> findMutualFriends(int person1, int person2) {
        Set<Integer> friends1 = new HashSet<>(friendshipGraph.getNeighbors(person1));
        Set<Integer> friends2 = new HashSet<>(friendshipGraph.getNeighbors(person2));
        
        friends1.retainAll(friends2);
        return new ArrayList<>(friends1);
    }
    
    // Find shortest path between two people (degrees of separation)
    public int degreesOfSeparation(int person1, int person2) {
        ShortestPath sp = new ShortestPath();
        List<Integer> path = sp.shortestPathBFS(friendshipGraph, person1, person2);
        return path.isEmpty() ? -1 : path.size() - 1;
    }
    
    // Find all connected components (friend groups)
    public List<Set<Integer>> findFriendGroups() {
        List<Set<Integer>> groups = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        
        for (int person : friendshipGraph.getVertices()) {
            if (!visited.contains(person)) {
                Set<Integer> group = new HashSet<>();
                dfsGroup(friendshipGraph, person, visited, group);
                groups.add(group);
            }
        }
        
        return groups;
    }
    
    private void dfsGroup(Graph graph, int person, Set<Integer> visited, Set<Integer> group) {
        visited.add(person);
        group.add(person);
        
        for (int friend : graph.getNeighbors(person)) {
            if (!visited.contains(friend)) {
                dfsGroup(graph, friend, visited, group);
            }
        }
    }
}
```

### 2. Maze Solver

```java
public class MazeSolver {
    private char[][] maze;
    private int rows, cols;
    
    public MazeSolver(char[][] maze) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
    }
    
    public List<int[]> solveMaze(int[] start, int[] end) {
        Queue<int[]> queue = new LinkedList<>();
        Map<String, int[]> parent = new HashMap<>();
        Set<String> visited = new HashSet<>();
        
        queue.offer(start);
        visited.add(start[0] + "," + start[1]);
        parent.put(start[0] + "," + start[1], null);
        
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            
            if (current[0] == end[0] && current[1] == end[1]) {
                return reconstructPath(parent, start, end);
            }
            
            for (int[] dir : directions) {
                int newRow = current[0] + dir[0];
                int newCol = current[1] + dir[1];
                
                if (isValid(newRow, newCol) && !visited.contains(newRow + "," + newCol)) {
                    visited.add(newRow + "," + newCol);
                    parent.put(newRow + "," + newCol, current);
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
        
        return new ArrayList<>(); // No solution found
    }
    
    private boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && maze[row][col] != '#';
    }
    
    private List<int[]> reconstructPath(Map<String, int[]> parent, int[] start, int[] end) {
        List<int[]> path = new ArrayList<>();
        int[] current = end;
        
        while (current != null) {
            path.add(current);
            current = parent.get(current[0] + "," + current[1]);
        }
        
        Collections.reverse(path);
        return path;
    }
}
```

### 3. Course Prerequisites (Topological Sort)

```java
public class CourseScheduler {
    private Graph courseGraph;
    
    public CourseScheduler() {
        this.courseGraph = new Graph(true); // Directed graph
    }
    
    public void addPrerequisite(int course, int prerequisite) {
        courseGraph.addEdge(prerequisite, course);
    }
    
    public List<Integer> getValidCourseOrder() {
        Map<Integer, Integer> inDegree = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> result = new ArrayList<>();
        
        // Calculate in-degrees
        for (int course : courseGraph.getVertices()) {
            inDegree.put(course, 0);
        }
        
        for (int course : courseGraph.getVertices()) {
            for (int neighbor : courseGraph.getNeighbors(course)) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }
        
        // Add courses with no prerequisites
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }
        
        // Process courses
        while (!queue.isEmpty()) {
            int course = queue.poll();
            result.add(course);
            
            for (int neighbor : courseGraph.getNeighbors(course)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        
        return result.size() == courseGraph.getVertices().size() ? result : new ArrayList<>();
    }
}
```

---

## Advanced Algorithm Examples

### 1. Minimum Spanning Tree (Kruskal's Algorithm)

```java
public class MinimumSpanningTree {
    public List<Edge> kruskal(List<Edge> edges, int numVertices) {
        Collections.sort(edges, (a, b) -> a.weight - b.weight);
        
        UnionFind uf = new UnionFind(numVertices);
        List<Edge> mst = new ArrayList<>();
        
        for (Edge edge : edges) {
            if (uf.find(edge.from) != uf.find(edge.to)) {
                mst.add(edge);
                uf.union(edge.from, edge.to);
            }
        }
        
        return mst;
    }
    
    static class Edge {
        int from, to, weight;
        
        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
    
    static class UnionFind {
        int[] parent, rank;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        public void union(int x, int y) {
            int px = find(x), py = find(y);
            if (px == py) return;
            
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
        }
    }
}
```

### 2. Graph Coloring

```java
public class GraphColoring {
    public Map<Integer, Integer> colorGraph(Graph graph) {
        Map<Integer, Integer> colors = new HashMap<>();
        int maxColors = 0;
        
        for (int vertex : graph.getVertices()) {
            Set<Integer> usedColors = new HashSet<>();
            
            for (int neighbor : graph.getNeighbors(vertex)) {
                if (colors.containsKey(neighbor)) {
                    usedColors.add(colors.get(neighbor));
                }
            }
            
            int color = 0;
            while (usedColors.contains(color)) {
                color++;
            }
            
            colors.put(vertex, color);
            maxColors = Math.max(maxColors, color + 1);
        }
        
        System.out.println("Minimum colors needed: " + maxColors);
        return colors;
    }
}
```

---

## Visualization Examples

### 1. Simple Graph Visualization (ASCII)

```java
public class GraphVisualizer {
    public void visualizeGraph(Graph graph) {
        System.out.println("Graph Visualization:");
        System.out.println("==================");
        
        for (int vertex : graph.getVertices()) {
            System.out.print(vertex + " -> ");
            List<Integer> neighbors = graph.getNeighbors(vertex);
            
            if (neighbors.isEmpty()) {
                System.out.println("[]");
            } else {
                System.out.print("[");
                for (int i = 0; i < neighbors.size(); i++) {
                    System.out.print(neighbors.get(i));
                    if (i < neighbors.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println("]");
            }
        }
    }
    
    public void visualizePath(List<Integer> path) {
        if (path.isEmpty()) {
            System.out.println("No path found");
            return;
        }
        
        System.out.print("Path: ");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }
}
```

### 2. Usage Example

```java
public class GraphExample {
    public static void main(String[] args) {
        // Create a sample graph
        Graph graph = new Graph(false);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        
        // Visualize the graph
        GraphVisualizer visualizer = new GraphVisualizer();
        visualizer.visualizeGraph(graph);
        
        // Test DFS
        System.out.println("\nDFS traversal starting from 0:");
        GraphTraversal traversal = new GraphTraversal();
        traversal.dfs(graph, 0);
        
        // Test BFS
        System.out.println("\n\nBFS traversal starting from 0:");
        traversal.bfs(graph, 0);
        
        // Test shortest path
        System.out.println("\n\nShortest path from 0 to 4:");
        ShortestPath sp = new ShortestPath();
        List<Integer> path = sp.shortestPathBFS(graph, 0, 4);
        visualizer.visualizePath(path);
    }
}
```

---

## Performance Analysis

### Time Complexity Summary

| Algorithm | Time Complexity | Space Complexity | Best For |
|-----------|----------------|------------------|----------|
| DFS | O(V + E) | O(V) | Path finding, cycle detection |
| BFS | O(V + E) | O(V) | Shortest path (unweighted) |
| Dijkstra | O((V + E) log V) | O(V) | Shortest path (weighted) |
| Kruskal | O(E log E) | O(V) | Minimum spanning tree |
| Topological Sort | O(V + E) | O(V) | Dependency resolution |

### Space Complexity Considerations

- **Adjacency List**: O(V + E) - Best for sparse graphs
- **Adjacency Matrix**: O(V²) - Best for dense graphs
- **DFS/BFS**: O(V) for visited set + O(V) for recursion/queue

---

This comprehensive collection of examples demonstrates the practical implementation of graph theory concepts in real-world scenarios. Each example includes both Java and Python implementations where applicable, making it accessible to developers with different language preferences.
