Australia Graph

Nodes (cities)
- Sydney, Melbourne, Brisbane, Perth, Adelaide, Darwin, Canberra, Gold Coast, Redlands, Logan, Newcastle, Alice Springs, Cairns

Edges (distance in km, undirected)
- Sydney–Melbourne 878
- Sydney–Canberra 286
- Sydney–Brisbane 915
- Sydney–Newcastle 168
- Sydney–Adelaide 1374
- Sydney–Gold Coast 850
- Melbourne–Adelaide 727
- Melbourne–Canberra 662
- Melbourne–Newcastle 1030
- Brisbane–Gold Coast 78
- Brisbane–Logan 59
- Brisbane–Redlands 35
- Brisbane–Newcastle 740
- Brisbane–Cairns 1705
- Adelaide–Perth 2696
- Adelaide–Alice Springs 1533
- Adelaide–Canberra 1160
- Perth–Alice Springs 2415
- Perth–Darwin 4020
- Alice Springs–Darwin 1497
- Alice Springs–Cairns 2340
- Darwin–Cairns 2450
- Canberra–Newcastle 422

Coordinates: each city also stores an (x,y) position used for drawing; values are proportioned to the background map and need not be geographic.

Rationale
- Edges reflect commonly used inter‑city driving routes and realistic distances (rounded). The set is intentionally sparse to keep the visualisation readable, but you can extend the network by adding entries to `src/main/resources/australia-graph.json`.

Editing the graph
1. Open the JSON file and add a new node to the `nodes` array with a unique `name` and drawing coordinates `x`,`y`.
2. Add one or more `edges` with `from`, `to`, and `distance`. The app treats edges as undirected, so one entry is enough.


