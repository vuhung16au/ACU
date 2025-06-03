# Chapter 14: Disjoint Set Data Structures (Union-Find)

## Introduction
This chapter introduces the disjoint set (union-find) data structure, which keeps track of a set of elements partitioned into non-overlapping subsets. You will learn how to efficiently join sets and find which set an element belongs to.

## Algorithms Implemented

### 1. Disjoint Set Class
Implements the union-find data structure with path compression and union by rank.
```python
class DisjointSet:
    def __init__(self, size):
        self.parent = [i for i in range(size)]
        self.rank = [0] * size

    def find(self, x):
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])
        return self.parent[x]

    def union(self, x, y):
        root_x = self.find(x)
        root_y = self.find(y)
        if root_x == root_y:
            return
        if self.rank[root_x] < self.rank[root_y]:
            self.parent[root_x] = root_y
        elif self.rank[root_x] > self.rank[root_y]:
            self.parent[root_y] = root_x
        else:
            self.parent[root_y] = root_x
            self.rank[root_x] += 1
```
Rust
### 2. Example Usage
Shows how to create sets, join them, and find representatives.
```python
num_elements = 5
sets = DisjointSet(num_elements)
sets.union(0, 1)
sets.union(1, 2)
sets.union(3, 4)
for i in range(num_elements):
    print(f"Element {i} is in set with root:", sets.find(i))
```

### 3. Application: Check if Two Elements Are in the Same Set
```python
x, y = 0, 2
if sets.find(x) == sets.find(y):
    print(f"Elements {x} and {y} are in the same set.")
else:
    print(f"Elements {x} and {y} are in different sets.")
```

## Algorithm Complexity (Time & Space)

### What is Time and Space Complexity?
- **Time complexity:** How the number of steps grows as the input size increases.
- **Space complexity:** How much extra memory is needed as the input grows.

### Complexity Analysis
- **Find/Union (with path compression and union by rank):** Nearly $O(1)$ per operation (amortized)
- **Space:** $O(n)$

#### Proof & Cases
- Path compression and union by rank keep the trees flat, making operations very fast.

## Important Notes
- Disjoint sets are used to track connected components.
- Path compression and union by rank are key optimizations.
- Useful for Kruskal's algorithm (see Chapter 15).

## Real-World Applications
- Network connectivity
- Image processing (finding connected regions)
- Kruskal's minimum spanning tree algorithm

## Ideas for Self-Practicing
- Implement disjoint sets without path compression. Compare speeds.
- Use disjoint sets to detect cycles in a graph.
- Try visualizing the parent array after each union.

## Further Readings & Connections
- [Khan Academy: Disjoint Sets](https://www.khanacademy.org/computing/computer-science/algorithms/graph-representation/a/disjoint-sets)
- [GeeksforGeeks: Disjoint Set (Union-Find)](https://www.geeksforgeeks.org/disjoint-set-data-structures/)
- Learn about graphs and minimum spanning trees (see Chapter 15).

---
**Key Terms:**
- **Disjoint Set:** Data structure for tracking a set of elements split into non-overlapping subsets.
- **Union-Find:** Another name for the disjoint set structure.
- **Path Compression:** Optimization to flatten the structure for fast finds.
- **Union by Rank:** Optimization to keep trees shallow. 