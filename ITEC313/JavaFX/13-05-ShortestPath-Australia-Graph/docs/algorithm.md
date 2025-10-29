Algorithm: Dijkstra's Shortest Path

Model
- Cities → nodes. Roads → undirected edges weighted by distance (km).
- Since weights are non‑negative, Dijkstra is optimal and efficient.

Pseudo‑steps
1) Set dist[source] = 0; for all other nodes, dist = ∞. prev[node] = null.
2) Push source into a min‑priority queue (keyed by dist).
3) While queue not empty:
   - Pop the node u with the smallest dist.
   - For each outgoing edge (u, v, w): if dist[u] + w < dist[v], update dist[v] and set prev[v] = u; decrease‑key v in the queue.
4) Stop once target is popped (its dist is final). Reconstruct the path by following prev from target back to source.

Complexity
- With a binary heap: O((V + E) log V) time, O(V) space.

Why it works
- Dijkstra grows a frontier of finalized nodes in order of increasing distance. With non‑negative weights, once a node leaves the queue its shortest distance is proven minimal.

Alternatives
- A*: Dijkstra + heuristic h(v) ≤ true remaining cost. Great for point‑to‑point routing at scale.
- Bellman–Ford: supports negative edges; detects negative cycles. O(V·E).
- Floyd–Warshall: all‑pairs APSP, O(V^3). Good for small dense graphs when many queries are needed.


