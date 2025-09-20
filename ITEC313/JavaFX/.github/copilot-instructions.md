# GitHub Copilot Project Instructions

This file guides AI assistants (and contributors) on how to interact with this repository effectively and consistently.

---
## 1. Project Theme & Purpose
A curated, multi-module collection of JavaFX + core CS concept demos (data structures, algorithms, UI/animation, networking, persistence, Spring REST/JPA). Each numbered folder is an independent learning unit. The root `pom.xml` is an aggregator (packaging: pom) – do not add executable code at the root.

---
## 2. Repository Conventions
- Folder naming: `NN-XX-TopicName` (progressive curriculum ordering).
- Each module: standalone Maven project with its own `pom.xml` and optional `run.sh` / `run.bat`.
- Java version: 21 (upgrade path to 24 when all modules aligned – verify before changing).
- JavaFX version: usually declared as `javafx.version` property (currently 24.x where set – keep consistent when updating).
- Preferred style: simple, instructional, minimal abstraction unless teaching a pattern.
- GUI entry point: single `Application` subclass (`start(Stage stage)`).
- Avoid heavy frameworks inside beginner modules (keep Spring & persistence isolated in higher-numbered folders).

---
## 3. When Adding a New Module
1. Copy a minimal starter (e.g., `01-01-JavaFX-HelloWorld`).
2. Update:
   - `artifactId` in `pom.xml` (no spaces; use `CamelCase` or hyphens).
   - `groupId` stays consistent unless demonstrating packaging.
   - Main class package: `com.acu.<topic>`.
3. Add to parent `pom.xml <modules>` list (keep numeric ordering).
4. Provide: `README.md` (what it teaches), `Prompt.md` (optional exercise prompts), screenshots if helpful.
5. Include at least one runnable scenario: `mvn clean javafx:run` must work.

---
## 4. Build & Run Rules
- Root-level build (all modules): `mvn -q clean package`.
- Individual module: run inside that folder only.
- Do NOT invoke shade for every module unless pedagogically necessary (only where distribution packaging is being taught). If using `maven-shade-plugin`, document why.
- For UI demos: prefer `javafx-maven-plugin` (`javafx:run`).
- For packaging examples: may add `jpackage-maven-plugin` (keep config concise, no OS-specific hardcoding beyond what's required).

---
## 5. Coding Style Guidance for Copilot
- Favor clarity > cleverness. Add brief comments ONLY where a student might get stuck conceptually (e.g., event handling, animation timeline, data structure invariants).
- Use meaningful but short names (e.g., `insertNode`, `balance`, `drawBoard`).
- Keep each example under ~250 lines per class unless demonstrating complex logic (AVL, Huffman, etc.). Split logically if longer.
- For algorithms: include a one-line Big-O comment.
- For recursion demos: avoid tail-call micro-optimizations; show canonical form.
- For data structure visualizers: separate model (logic) and view (JavaFX) when it helps teaching; otherwise, inline for smallest examples.

### 5.1 Java Coding Guidelines (Google Java Style Guide)
- Source of truth: Google Java Style Guide — `https://google.github.io/styleguide/javaguide.html`.
- Conflict resolution:
  - Follow project conventions in this file first (pedagogical choices for this repo).
  - Where this file is silent, default to Google Java Style.
  - Never contradict public APIs or signatures already present in a module unless the task is a refactor with rationale.
- Key enforceable rules for Copilot/Cursor generation:
  - Formatting
    - Indentation: 2 spaces per level (do not use tabs).
    - Line length: 100 chars max preferred; wrap earlier if readability improves.
    - Braces: K&R style; opening brace on the same line; always use braces, even for single statements.
    - Blank lines: group logically related code; one blank line between methods.
  - Naming
    - Classes/Interfaces: UpperCamelCase; methods/fields/locals: lowerCamelCase; constants: UPPER_SNAKE_CASE.
    - Packages: all lowercase, dot-separated (e.g., `com.acu.module`).
  - Imports
    - No wildcard imports; order: static imports first, then regular imports; keep sorted.
  - Javadoc
    - Public types and methods should have Javadoc when they are part of the teaching surface or non-trivial.
    - Use `@param`, `@return`, `@throws` selectively when not obvious.
  - Annotations
    - Place annotations on their own line above the declaration; order common ones reasonably (e.g., `@Override` closest to the declaration).
  - Nullability & Optionals
    - Avoid returning `null` from methods when a collection or array is expected; return empty instead.
    - Prefer explicit null checks over deep optional chains in beginner modules.
  - Exceptions
    - Use checked exceptions where it clarifies recoverability; do not swallow exceptions; include meaningful messages.
  - Equality & Hashing
    - When overriding `equals`, also override `hashCode`; use `Objects.equals`/`Objects.hash` where appropriate.
  - Ordering of class contents
    - Static fields, instance fields, constructors, public methods, protected, package-private, private; keep related methods adjacent.
  - Misc
    - Use `final` for constants and unchanging references where helpful for clarity.
    - Prefer enhanced for-loops and streams only when they improve readability for students.
    - Avoid premature generics complexity in early modules; introduce gradually per module intent.

Notes for this repository:
- Keep examples minimal and readable even if that means diverging from enterprise conventions; however, still adhere to Google style for fundamentals (formatting, naming, imports, braces).
- If an example intentionally demonstrates a “bad smell” for teaching, clearly comment it as such.

---
## 6. Testing Strategy (Planned / Incremental)
Some modules currently lack tests. When adding tests:
- Use JUnit 5 (if adding test dependency, scope = test).
- Focus on algorithm/data structure correctness (e.g., AVL rotations, hash probing behavior, expression evaluation).
- Keep GUI logic untested unless trivial (avoid brittle UI tests). Future extension: add TestFX for a few illustrative cases.

Add this dependency when first needed:
```xml
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter</artifactId>
  <version>5.10.2</version>
  <scope>test</scope>
</dependency>
```
And (if not already present) in the module's `build`:
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <version>3.2.5</version>
</plugin>
```

---
## 7. Dependency Management Rules
- Keep JavaFX artifacts explicit (`javafx-controls`, `javafx-fxml`, add others only when used: `javafx-media`, `javafx-web`).
- No transitive-heavy libraries unless teaching them (e.g., only add Gson/Jackson in serialization-focused module; only add Spring Boot inside its dedicated folder).
- Avoid version drift: if you bump JavaFX or JUnit in one module, propagate across all modules in the same PR or open a tracking issue.

---
## 8. Documentation Patterns
Each module README ideally contains:
1. Concept Focus
2. Learning Outcomes
3. How to Run
4. Key Classes
5. Extension Ideas
6. (Optional) Screenshot / GIF

`Prompt.md` = challenge tasks (e.g., "Add pause/resume to animation", "Implement delete in AVL").

---
## 9. AI Assistance Guardrails
When generating code:
- Do not remove existing instructional comments without reason.
- Preserve public method signatures unless enhancing backward-compatibly.
- If refactoring, explain rationale in PR/commit message (e.g., "Extracted TreeNode to clarify balance logic").
- If adding advanced patterns (MVVM, DI), restrict to higher-level modules (>30 or explicitly architecture-focused).

When answering user questions:
- Default to simplest runnable example first.
- Offer optional enhancements after the base answer.

---
## 10. Performance & Pedagogy
- Prefer O(n log n) vs premature micro-optimizations unless a module's goal is performance analysis.
- Provide clear contrast examples (e.g., naive vs optimized search) where educational.
- For animation demos: keep frame logic lightweight; use JavaFX `Timeline` or `AnimationTimer` appropriately.

---
## 11. Adding Advanced Topics (From TODO)
Prioritize upcoming modules (open tasks):
- JUnit Testing infrastructure
- REST API + JavaFX client integration
- TableView showcase (sorting, cell factories)
- Charts & live update
- Theming (dark mode toggle via CSS)
- Hash probing visualization improvements

When implementing one, cross-link from existing earlier modules where relevant.

---
## 12. Suggested Commit Message Formats
- feat(module-08-02): add binary search iterative variant
- refactor(module-12-01): extract AVL rotation helpers
- docs(module-07-08): clarify operator precedence parsing
- test(module-11-01): add BST delete edge case tests
- chore(build): bump JavaFX to 24.0.2 across modules

---
## 13. Common Pitfalls to Avoid
| Pitfall | Avoid By |
|---------|----------|
| Mixing unrelated dependency upgrades in feature PRs | Separate into dedicated chore PR |
| Adding platform-specific paths to poms | Use relative paths / Maven properties |
| Bundling JavaFX natives in uber-jar blindly | Prefer `javafx:run` or jpackage for distribution |
| Over-engineering beginner examples | Keep early modules linear & readable |
| Silent behavior changes during refactor | Add note in commit / README diff |

---
## 14. Release & Distribution (Optional Modules)
If demonstrating packaging:
- Use `jpackage-maven-plugin` with `APP_IMAGE` first.
- Document run command for generated bundle.
- Avoid signing/notarization steps (out-of-scope for course basics).

---
## 15. Issue & PR Guidance
When opening an Issue for a new educational enhancement include:
- Title: concise objective
- Rationale: what students learn
- Acceptance: bullet list (e.g., "Runs via mvn javafx:run", "README lists 3 outcomes").

---
## 16. Minimal Example Template (Starter Main Class)
```java
package com.acu.sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Sample Module");
        stage.setScene(new Scene(new Label("Hello JavaFX"), 320, 120));
        stage.show();
    }

    public static void main(String[] args) { launch(args); }
}
```

---
## 17. Quick Reference
- Build all: `mvn clean package`
- Run one module: `mvn clean javafx:run`
- Add module: create folder + pom + add to parent + simple App class
- Add test: add JUnit dependency + `src/test/java` with `*Test` classes

---
## 18. How Copilot Should Decline
If user asks for: proprietary licensed content, unrelated large frameworks, or non-educational obfuscation — respond with a brief refusal or redirection.

---
## 19. Future Enhancements (Meta)
- Introduce a shared parent for common plugin/version management (BOM-style) when duplication grows.
- Add a script to verify each module runs headless (CI with xvfb for Linux pipeline).
- Provide a central `INDEX.md` auto-generated from module READMEs.

---
## 20. ACU Branding Guidelines
When creating visual elements, documentation, or UI components, use the official ACU logo colors when possible:

- **Purple**: `#582C67` (background color)
- **White**: `#FFFFFF` (text "ACU ONLINE" and shield symbol)
- **Red**: `#C60C30` (shield outline and inner cross)

Apply these colors to:
- UI themes and styling
- Documentation headers and branding
- Screenshots and visual materials
- Any custom graphics or diagrams

---
## 21. Contact & Attribution
Educational scaffold authored/curated by repository maintainer. Contributions should stay aligned with teaching goals.

---
End of instructions.

---
## 22. Copilot/Cursor PR Checklist (Paste into PR description)

- [ ] Uses Java 21 and matches module `pom.xml`.
- [ ] Follows Google Java Style: 2-space indents, ≤100 char lines, K&R braces, no wildcard imports.
- [ ] Naming: UpperCamelCase types; lowerCamelCase methods/fields; UPPER_SNAKE_CASE constants; lowercase packages.
- [ ] Imports sorted; static imports first; no unused imports.
- [ ] Javadoc added for public APIs or non-trivial methods.
- [ ] No `null` returns for collections/arrays; return empty instead.
- [ ] Exceptions not swallowed; include meaningful messages.
- [ ] `equals`/`hashCode` overridden together when applicable.
- [ ] Class member order: static fields → instance fields → constructors → public → protected → package-private → private.
- [ ] Example code kept minimal and pedagogical; comments only where students might get stuck.
- [ ] GUI entry point remains a single `Application` subclass where applicable.
- [ ] Algorithms include a one-line Big-O note where relevant.
- [ ] No new heavy deps; JavaFX artifacts explicit and used.
- [ ] Module builds and runs via `mvn clean javafx:run`.
