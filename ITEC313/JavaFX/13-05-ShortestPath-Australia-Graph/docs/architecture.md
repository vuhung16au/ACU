Architecture

Modules
- UI (JavaFX): `AustraliaShortestPathApp` builds the window, toolbar buttons and combo boxes.
- View: `GraphView` draws nodes/edges and highlights paths.
- Model: `Graph` stores cities and weighted edges; data is loaded from `australia-graph.json`.
- Algorithm: `Dijkstra` computes the shortest path.

Data flow
1. App loads `australia-graph.json` via `GraphView` on start.
2. User selects start/end; the app calls `Dijkstra.shortestPath(Graph, s, t)`.
3. `GraphView.showPath(path)` overlays the red path.

Design notes
- The graph is undirected; weights are symmetric road distances (km).
- Coordinates in JSON are view coordinates tied to the background image, not geo lat/long.
- Controls use the ACU palette; edges are neutral grey; paths are red for emphasis.

Build & Test
- Maven sub‑module with JavaFX plugin for GUI run.
- JUnit tests for the Dijkstra core (no GUI code in tests).


