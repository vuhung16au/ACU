# Graph Theory: Mathematical Foundations and Proofs

## Table of Contents
1. [Mathematical Definitions](#mathematical-definitions)
2. [Fundamental Theorems](#fundamental-theorems)
3. [Proof Techniques](#proof-techniques)
4. [Combinatorial Properties](#combinatorial-properties)
5. [Algebraic Graph Theory](#algebraic-graph-theory)
6. [Advanced Mathematical Concepts](#advanced-mathematical-concepts)

---

## Mathematical Definitions

### 1. Formal Graph Definition

A **graph** \(G\) is an ordered pair \(G = (V, E)\) where:
- \(V\) is a finite, non-empty set called the **vertex set**
- \(E\) is a set of unordered pairs of distinct vertices called the **edge set**

**Notation**: 
- \(|V| = n\) (order of the graph)
- \(|E| = m\) (size of the graph)
- \(G\) is called an \((n, m)\)-graph

### 2. Adjacency and Incidence

**Adjacency**: Two vertices \(u, v \in V\) are **adjacent** if \(\{u, v\} \in E\)
**Incidence**: An edge \(e = \{u, v\}\) is **incident** to vertices \(u\) and \(v\)

### 3. Degree of a Vertex

For a vertex \(v \in V\), the **degree** of \(v\), denoted \(\deg(v)\), is the number of edges incident to \(v\).

**Handshaking Lemma**:
\[
\sum_{v \in V} \deg(v) = 2\lvert E \rvert
\]

**Proof**: Each edge contributes exactly 2 to the sum of degrees (one for each endpoint).

**Corollary**: In any graph, the number of vertices with odd degree is even.

### 4. Subgraphs

A **subgraph** \(H = (V', E')\) of \(G = (V, E)\) is a graph where:
- \(V' \subseteq V\)
- \(E' \subseteq E\) and every edge in \(E'\) has both endpoints in \(V'\)

An **induced subgraph** \(G[V']\) is a subgraph where \(E'\) contains all edges of \(E\) with both endpoints in \(V'\).

---

## Fundamental Theorems

### 1. Euler's Theorem (1736)

**Theorem**: A connected graph has an Eulerian circuit if and only if every vertex has even degree.

**Proof**:
- **Necessity**: If G has an Eulerian circuit, then each time we enter a vertex, we must leave it. Therefore, each vertex has even degree.
- **Sufficiency**: If every vertex has even degree, we can construct an Eulerian circuit using the following algorithm:
  1. Start at any vertex v
  2. Follow edges to form a trail until we return to v
  3. If the trail is not a circuit, there exists a vertex with unused edges
  4. Start a new trail from that vertex and merge it with the existing trail
  5. Repeat until all edges are used

### 2. Handshaking Lemma

**Theorem**: In any graph, the sum of all vertex degrees equals twice the number of edges.

**Proof**: Each edge contributes exactly 2 to the sum of degrees (one for each endpoint).

### 3. Pigeonhole Principle in Graphs

**Theorem**: In any graph with n ≥ 2 vertices, there exist two vertices with the same degree.

**Proof**: 
- Possible degrees in a graph with n vertices: 0, 1, 2, ..., n-1
- If all degrees are different, we must have one vertex of each degree
- But if there's a vertex of degree n-1, it's connected to all other vertices
- This means no vertex can have degree 0
- Contradiction! Therefore, at least two vertices must have the same degree.

### 4. Tree Properties

**Theorem**: For a tree T with n vertices, |E(T)| = n - 1.

**Proof by induction**:
- **Base case**: n = 1, |E| = 0 = 1 - 1 ✓
- **Inductive step**: Assume true for trees with k vertices
- For a tree with k+1 vertices, remove a leaf (vertex of degree 1)
- The remaining graph is a tree with k vertices and k-1 edges
- Adding back the leaf adds one edge: (k-1) + 1 = k = (k+1) - 1 ✓

**Corollary**: A connected graph with n vertices and n-1 edges is a tree.

---

## Proof Techniques

### 1. Direct Proof

**Example**: Prove that in a complete graph \(K_n\), the number of edges is \(\frac{n(n-1)}{2}\).

**Proof**: 
- Each vertex is connected to \(n-1\) other vertices
- Total connections: \(n(n-1)\)
- Each edge is counted twice (once for each endpoint)
- Therefore: \(|E| = \frac{n(n-1)}{2}\)

### 2. Proof by Contradiction

**Example**: Prove that a graph with \(n\) vertices and more than \(n-1\) edges contains a cycle.

**Proof**:
- Assume \(G\) has \(n\) vertices, more than \(n-1\) edges, and no cycles
- Then G is a forest (collection of trees)
- Each tree component with \(k_i\) vertices has \(k_i - 1\) edges
- Total edges: \(\sum (k_i - 1) = \sum k_i - \text{number of components} = n - c\)
- Since \(c \ge 1\), we have \(|E| \le n - 1\)
- This contradicts our assumption that |E| > n - 1
- Therefore, G must contain a cycle

### 3. Proof by Induction

**Example**: Prove that a tree with n vertices has n-1 edges.

**Proof** (as shown above):
- Base case: n = 1
- Inductive hypothesis: Assume true for k vertices
- Inductive step: Prove for k+1 vertices

### 4. Proof by Construction

**Example**: Prove that every graph with maximum degree \(\Delta\) can be colored with \(\Delta+1\) colors.

**Proof**:
- Order vertices arbitrarily: \(v_1, v_2, \dots, v_n\)
- Color \(v_i\) with the smallest color not used by its already-colored neighbors
- Since \(v_i\) has at most \(\Delta\) neighbors, at most \(\Delta\) colors are forbidden
- Therefore, color \(\Delta+1\) is always available

---

## Combinatorial Properties

### 1. Counting Subgraphs

**Complete Graph \(K_n\)**:
- Number of edges: \(\binom{n}{2} = \frac{n(n-1)}{2}\)
- Number of triangles: \(\binom{n}{3} = \frac{n(n-1)(n-2)}{6}\)
- Number of spanning trees: \(n^{n-2}\) (Cayley's formula)

**Cycle \(C_n\)**:
- Number of edges: \(n\)
- Number of spanning trees: \(n\) (remove any edge)

**Path \(P_n\)**:
- Number of edges: \(n-1\)
- Number of spanning trees: \(1\)

### 2. Graph Isomorphism

Two graphs \(G_1 = (V_1, E_1)\) and \(G_2 = (V_2, E_2)\) are **isomorphic** if there exists a bijection \(f: V_1 \to V_2\) such that \(\{u,v\} \in E_1\) if and only if \(\{f(u), f(v)\} \in E_2\).

**Isomorphism Invariants**:
- Number of vertices
- Number of edges
- Degree sequence
- Number of cycles of each length
- Chromatic number
- Independence number

### 3. Ramsey Theory

**Ramsey's Theorem**: For any positive integers \(r\) and \(s\), there exists a number \(R(r,s)\) such that any graph with \(R(r,s)\) vertices contains either a clique of size \(r\) or an independent set of size \(s\).

**Examples**:
- \(R(3,3) = 6\) (any 6-vertex graph contains a triangle or 3 independent vertices)
- \(R(4,4) = 18\)
- \(R(5,5)\) is unknown (between 43 and 48)

---

## Algebraic Graph Theory

### 1. Adjacency Matrix

For a graph \(G\) with vertices \(v_1, v_2, \dots, v_n\), the **adjacency matrix** \(A\) is an \(n\times n\) matrix where:
- \(A[i,j] = 1\) if \(\{v_i, v_j\} \in E\)
- \(A[i,j] = 0\) otherwise

**Properties**:
- A is symmetric for undirected graphs
- A is not necessarily symmetric for directed graphs
- The sum of entries in row i equals deg(v_i)

### 2. Eigenvalues and Eigenvectors

The **eigenvalues** of a graph are the eigenvalues of its adjacency matrix.

**Theorem**: If \(\lambda\) is an eigenvalue of a graph \(G\), then \(|\lambda| \le \Delta(G)\), where \(\Delta(G)\) is the maximum degree.

**Proof**: Let \(x\) be an eigenvector corresponding to \(\lambda\), and let \(v\) be a vertex with maximum absolute value in \(x\).
- \(|\lambda x_v| = \left|\sum_{u\in N(v)} x_u\right| \le \sum_{u\in N(v)} |x_u| \le \deg(v)\,|x_v| \le \Delta(G)\,|x_v|\)
- Therefore: \(|\lambda| \le \Delta(G)\)

### 3. Laplacian Matrix

The **Laplacian matrix** \(L = D - A\), where \(D\) is the diagonal degree matrix.

**Properties**:
- L is positive semidefinite
- The smallest eigenvalue is 0 with multiplicity equal to the number of connected components
- The second smallest eigenvalue (algebraic connectivity) measures how well-connected the graph is

### 4. Chromatic Polynomial

The **chromatic polynomial** \(P(G, k)\) counts the number of proper \(k\)-colorings of \(G\).

**Recurrence relation**:
\[
P(G, k) = P(G - e, k) - P(G / e, k)
\]
where \(G - e\) is \(G\) with edge \(e\) removed, and \(G / e\) is \(G\) with edge \(e\) contracted.

**Examples**:
- \(P(K_n, k) = k(k-1)(k-2)\cdots(k-n+1)\)
- \(P(T_n, k) = k\,(k-1)^{n-1}\) for a tree \(T_n\) with \(n\) vertices

---

## Advanced Mathematical Concepts

### 1. Planar Graphs

A graph is **planar** if it can be drawn in the plane without edge crossings.

**Euler's Formula**: For a connected planar graph with \(n\) vertices, \(m\) edges, and \(f\) faces:
\[
n - m + f = 2
\]

**Corollary**: For a planar graph with \(n \ge 3\) vertices: \(m \le 3n - 6\)

**Proof**:
- Each face is bounded by at least 3 edges
- Each edge bounds at most 2 faces
- Therefore: \(2m \ge 3f\)
- From Euler's formula: \(f = 2 - n + m\)
- Substituting: \(2m \ge 3(2 - n + m) = 6 - 3n + 3m\)
- Rearranging: \(m \le 3n - 6\)

### 2. Graph Coloring

The **chromatic number** \(\chi(G)\) is the minimum number of colors needed to properly color \(G\).

**Brooks' Theorem**: For a connected graph \(G\) that is neither complete nor an odd cycle:
\(\chi(G) \le \Delta(G)\)

**Proof outline**:
- If G is not regular, we can find a proper coloring
- If G is regular, we can use Kempe chains to reduce the degree

### 3. Matching Theory

A **matching** is a set of edges with no common vertices.

**König's Theorem**: In a bipartite graph, the size of a maximum matching equals the size of a minimum vertex cover.

**Hall's Marriage Theorem**: A bipartite graph \(G = (X \cup Y, E)\) has a matching that covers \(X\) if and only if \(|N(S)| \ge |S|\) for every subset \(S \subseteq X\).

### 4. Network Flow

**Max-Flow Min-Cut Theorem**: In a flow network, the maximum value of a flow equals the minimum capacity of a cut.

**Applications**:
- Bipartite matching
- Image segmentation
- Network reliability

### 5. Random Graphs

**Erdős-Rényi Model**: \(G(n,p)\) is a random graph with \(n\) vertices where each edge exists with probability \(p\).

**Threshold Functions**:
- For connectivity: \(p = \frac{\ln n}{n}\)
- For Hamilton cycle: \(p = \frac{\ln n + \ln \ln n}{n}\)
- For perfect matching: \(p = \frac{\ln n}{n}\)

---

## Computational Complexity

### 1. P vs NP Problems

**P**: Problems solvable in polynomial time
- Shortest path
- Minimum spanning tree
- Maximum matching

**NP**: Problems verifiable in polynomial time
- Graph coloring
- Hamiltonian cycle
- Clique

**NP-Complete**: Problems in NP that are at least as hard as any NP problem
- 3-SAT
- Traveling salesman problem
- Graph isomorphism (not known to be NP-complete)

### 2. Approximation Algorithms

**Vertex Cover**: Greedy algorithm gives 2-approximation
**Traveling Salesman**: Christofides algorithm gives 1.5-approximation for metric TSP
**Graph Coloring**: Greedy algorithm uses at most Δ+1 colors

---

## Conclusion

Graph theory provides a rich mathematical framework for studying discrete structures and their properties. The mathematical foundations presented here form the theoretical backbone for understanding and analyzing graph algorithms, complexity classes, and structural properties.

Key mathematical insights include:
1. **Combinatorial properties** reveal fundamental relationships between vertices and edges
2. **Algebraic methods** connect graph theory to linear algebra and spectral theory
3. **Topological concepts** like planarity provide geometric insights
4. **Probabilistic methods** help understand random graph behavior
5. **Computational complexity** classifies the inherent difficulty of graph problems

These mathematical foundations enable the development of efficient algorithms and provide deep insights into the structure and behavior of complex networks in both theoretical and practical applications.

---

*This document provides the mathematical rigor underlying graph theory, complementing the practical applications and examples in the other documents in this series.*
