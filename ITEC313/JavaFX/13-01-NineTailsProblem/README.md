# Nine Tails Problem

A simple JavaFX app for the classic Nine Tails problem.

![Nine Tails Game](images/NineTails.png)

# What is Nine Tails?

Nine Tails is a puzzle consisting of a 3x3 grid of coins, each showing either Heads or Tails. The goal is to flip all coins to Tails by clicking on coins showing Heads. Clicking a coin flips it and its adjacent coins (up, down, left, right).

> The game board consists of a \(3 \times 3\) grid (or generally, \(N \times N\)), where each cell is a coin. Each coin is either:
>
> - \(0\) = Head (white)
> - \(1\) = Tail (purple)

# How to Play Nine Tails

- Click on a coin showing Heads (white) to flip it and its adjacent coins (up, down, left, right).

The objective is to turn all coins to \(\mathrm{Tail}\) (purple) in the fewest moves possible.
Use the "Solve" button to find the minimum steps from the current configuration to the goal state.

## What it shows
- The board is an \(N \times N\) grid of coins (default \(3 \times 3\)).
- \(0 = \mathrm{Head}\) (white), \(1 = \mathrm{Tail}\) (purple).
- A legal move: click a coin showing \(\mathrm{Head}\) to flip it and its 4-neighbors (up, down, left, right).
- The goal: all coins \(\mathrm{Tail}\). The Solve button finds the minimum steps.

## How it works (short and sharp)

Each board state is encoded as an integer bitset: bit \(i\) corresponds to cell \(i\).
Total number of possible board states is \(2^{N \times N}\).
Masks are precomputed to toggle a cell and its neighbors.
A BFS tree is built rooted at the target state (all tails); from that tree, we read the shortest path from any start to the target.

## Run
```
mvn clean javafx:run
```

## Documentation

See [Documentation](docs/README.md) for more details. 

## Structure
- `src/main/java/com/acu/ninetails/NineTailsModel.java` – core model + BFS.
- `src/main/java/com/acu/ninetails/App.java` – JavaFX UI.
- `src/test/java/com/acu/ninetails/NineTailsModelTest.java` – unit tests for model.


