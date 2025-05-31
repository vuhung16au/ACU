# Chapter 14: Disjoint Set Data Structures (Union-Find)
# This tutorial introduces the disjoint set (union-find) data structure for beginners with simple, well-commented examples.

# 1. What is a Disjoint Set?
# A disjoint set (or union-find) is a data structure that keeps track of a set of elements partitioned into non-overlapping subsets.
# It supports two main operations efficiently:
#   - find: Determine which subset a particular element is in.
#   - union: Join two subsets into a single subset.

class DisjointSet:
    def __init__(self, size):
        # Each element is initially its own parent (self root)
        self.parent = [i for i in range(size)]
        # Optionally, use rank to keep tree flat
        self.rank = [0] * size

    def find(self, x):
        # Find the root of the set containing x with path compression
        if self.parent[x] != x:
            self.parent[x] = self.find(self.parent[x])  # Path compression
        return self.parent[x]

    def union(self, x, y):
        # Union by rank: attach the smaller tree to the root of the larger tree
        root_x = self.find(x)
        root_y = self.find(y)
        if root_x == root_y:
            return  # Already in the same set
        if self.rank[root_x] < self.rank[root_y]:
            self.parent[root_x] = root_y
        elif self.rank[root_x] > self.rank[root_y]:
            self.parent[root_y] = root_x
        else:
            self.parent[root_y] = root_x
            self.rank[root_x] += 1

# 2. Example Usage
num_elements = 5
sets = DisjointSet(num_elements)

# Initially, each element is in its own set
print("Initial parents:", sets.parent)

# Union some sets
sets.union(0, 1)
sets.union(1, 2)
sets.union(3, 4)
print("Parents after some unions:", sets.parent)

# Find representatives (roots) of each set
for i in range(num_elements):
    print(f"Element {i} is in set with root:", sets.find(i))

# 3. Application Example: Check if two elements are in the same set
x, y = 0, 2
if sets.find(x) == sets.find(y):
    print(f"Elements {x} and {y} are in the same set.")
else:
    print(f"Elements {x} and {y} are in different sets.")

# 4. Key Points
# - Disjoint sets are useful for network connectivity, Kruskal's algorithm, and grouping problems.
# - Path compression and union by rank make operations very efficient (almost constant time).

# End of Chapter 14: Disjoint Set Data Structures tutorial
