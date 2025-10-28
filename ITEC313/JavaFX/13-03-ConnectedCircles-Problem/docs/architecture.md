# Architecture

## Overview
A simple MVC-style separation:
- UI (App.java): layout, controls, interactions
- Model (CircleItem): circle properties
- Logic (GraphUtils): graph build + DFS

## UI
- Top controls bar: radius, color, Same Color, buttons (Reset, Randomize, Check, Algorithm, Help, About)
- Center canvas: 800x600 pane with Soft Ivory background
- ACU palette for strokes/fills

## Flow
1. User adds/moves circles → model updates
2. Check triggers DFS via GraphUtils
3. Filled state applied per connectivity result (per color if enabled)
4. Dialog reports final status

## Key Decisions
- Iterative DFS for clarity
- Plain JavaFX shapes for simplicity
- Minimal state: list of CircleItem, derived graph on demand
