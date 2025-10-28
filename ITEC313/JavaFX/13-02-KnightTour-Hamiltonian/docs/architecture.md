# Knight's Tour Application Architecture (Hamiltonian Cycle)

## Overview

The application follows a clean, modular architecture that separates concerns and promotes maintainability. The solver now targets a closed Knight’s Tour by reducing the problem to a Hamiltonian cycle in the knight-move graph.

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    KnightTourDemo                          │
│                   (Main Application)                       │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │   UI Controls   │  │   Event Handlers│  │   Status    │ │
│  │   - Buttons     │  │   - Click       │  │   - Label   │ │
│  │   - Layout      │  │   - Animation   │  │   - Updates │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                    ChessBoard                              │
│                (Custom JavaFX Component)                   │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │   Rendering     │  │   State Mgmt    │  │   Events    │ │
│  │   - Canvas      │  │   - Knight Pos  │  │   - Click   │ │
│  │   - Graphics    │  │   - Path Track  │  │             │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                  KnightTourSolver                          │
│          (Hamiltonian Cycle Search Engine)                 │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │   Algorithm     │  │   Animation     │  │   State     │ │
│  │   - Backtrack   │  │   - Timing      │  │   - Track   │ │
│  │   - Closure     │  │   - Callbacks   │  │   - Reset   │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

## Component Notes

### KnightTourDemo
- Coordinates UI and solver
- Status strings updated to “closed tour (cycle)”

### ChessBoard
- Renders the path in red
- When the cycle is closed (last equals first), renders the closing edge in green

### KnightTourSolver
- Uses backtracking over the knight-move graph
- Accepts only tours where the final vertex is a knight move from the start (closed tour)
- Provides `solveClosedTourSync()` for non-animated access

## Data Flow

1. User places the knight → `ChessBoard` updates state
2. User clicks Solve → `KnightTourDemo` calls `KnightTourSolver.solveTour`
3. Solver searches for Hamiltonian cycle → on success, animates
4. `ChessBoard` visualizes the path; final closure edge emphasized

## Design Patterns
- MVC, Observer, Strategy (algorithm variations), Command (animation)

## Extensibility
- Plug in heuristics (e.g., Warnsdorff) to improve ordering
- Support open tours by relaxing the closure check
- Support different board sizes (graph is generated implicitly by move rules)

## Testing
- Unit tests for knight move validity and board state
- Add assertions to confirm the produced tour is closed when solutions are found
