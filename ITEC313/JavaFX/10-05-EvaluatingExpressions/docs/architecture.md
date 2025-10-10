### Architecture

- `EvaluateExpression` contains the pure evaluation logic (no UI). It parses characters, manages two stacks, and computes the result.
- `EvaluateExpressionApp` provides a tiny JavaFX UI: a text field, a button, and a label for showing the result or an error message.
- `Launcher` is a main entry point to avoid JavaFX module path issues.


