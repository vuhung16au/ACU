# AI (Copilot) Usage Guide (Concise)

Canonical full guide: see [copilot-instructions.md](../copilot-instructions.md)
(If you relocate the full guide into `.github/`, update the link accordingly.)

## Goal

Keep examples pedagogical: small, clear JavaFX + core CS concepts. Each numbered folder = independent Maven module.

## When Generating Code

- Provide the smallest runnable example first.
- Stay on Java 21; keep JavaFX versions consistent across modules.
- One clear `Application` subclass entry point per module.
- Add brief comments only where students may struggle (events, invariants, algorithm complexity).

## Creating a New Module

1. Copy a minimal existing module.
2. Adjust `artifactId`, package (`com.acu.<topic>`), main class name.
3. Add folder to parent `<modules>` in root `pom.xml` (keep sorted numerically).
4. Ensure `mvn javafx:run` works.
5. Add a short README (concept, outcomes, run instructions).

## Tests

- Use JUnit 5 for algorithm/data structure logic.
- Skip complex UI automation unless explicitly requested.

## Dependencies

- Only add what the lesson needs (`javafx-controls`, optional `javafx-fxml`, etc.).
- Avoid large frameworks in beginner modules.

## Refactoring Rules

- Do not change public method signatures silently.
- Explain non-trivial refactors in commit messages.
- Keep early examples simple (no DI/MVVM unless advanced module).

## Commit Message Style

- feat(module-08-02): add binary search iterative variant
- refactor(module-12-01): extract AVL rotation helpers
- test(module-11-01): add AVL double rotation tests
- chore(build): bump JavaFX to 24.0.2 across modules

## Do

- Clarify Big-O in algorithm demos.
- Separate model vs view only when it aids learning.
- Keep classes < ~250 lines unless justified.

## Don’t

- Introduce unrelated dependency upgrades in feature PRs.
- Over-engineer beginner modules.
- Hardcode platform-specific paths.
- Remove instructional comments.

## Decline / Redirect

If asked for proprietary content, code obfuscation, or unrelated tech stacks, give a brief refusal.

## Quick Commands

- Build all: `mvn clean package`
- Run module: `mvn clean javafx:run`
- First test setup: add JUnit 5 dependency + (if missing) Surefire plugin.

End.
