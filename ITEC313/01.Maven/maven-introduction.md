# Overview of Maven

Apache Maven is a build automation and project management tool centered around a **Project Object Model (POM)**. It standardizes how Java projects are structured, built, tested, packaged, versioned, and distributed. Maven emphasizes **convention over configuration**: if you follow its standard directory layout and naming, you need only minimal configuration.

Core value points:
- Declarative: You describe _what_ (dependencies, plugins, metadata), Maven handles _how_.
- Reproducible builds: Fixed coordinates + versioned dependencies = consistent artifacts.
- Dependency management: Automatic resolution (including transitive dependencies) from repositories (local / remote / central).
- Extensible: Rich plugin ecosystem + custom extensions.
- Lifecycle abstraction: Unified phases (`validate`, `compile`, `test`, `package`, `verify`, `install`, `deploy`).
- Standard metadata: groupId / artifactId / version (GAV) uniquely identify artifacts.

---
 
## Maven in 5 mins

Based on: [Maven in Five Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)

Ultra‑condensed flow:

1. Confirm install: `mvn -v`
2. Generate skeleton (example):

   ```bash
   mvn -q archetype:generate \
     -DgroupId=com.example.app \
     -DartifactId=demo-app \
     -DarchetypeArtifactId=maven-archetype-quickstart \
     -DarchetypeVersion=1.5 \
     -DinteractiveMode=false
   ```

3. Build & run tests: `mvn clean package`
4. Run your jar (if has main): `java -jar target/demo-app-1.0-SNAPSHOT.jar`
5. Inspect dependencies: `mvn dependency:tree`
6. Install into local repo: `mvn install`
7. (Optional) Deploy to a remote (or file) repo: `mvn deploy`

Key takeaway: The **POM** drives everything; the directory layout + coordinates + dependencies + plugins are enough for Maven to produce repeatable artifacts.

---
 
## Maven + JavaFX How‑To

Reference: [OpenJFX Maven Docs](https://openjfx.io/openjfx-docs/#maven)

JavaFX modules are not on the standard JDK module path; you must:

- Declare dependencies (`org.openjfx:javafx-controls`, `javafx-fxml`, etc.) with a matching Java version/OS classifier when needed for modular runtime.
- Provide runtime JVM args for the module path if using `--module-path` outside of the shaded jar approach.

Typical run via the JavaFX Maven Plugin:

```bash
mvn clean javafx:run
```

Plugin config minimal form:

```xml
<plugin>
  <groupId>org.openjfx</groupId>
  <artifactId>javafx-maven-plugin</artifactId>
  <version>0.0.8</version>
  <configuration>
    <mainClass>com.example.App</mainClass>
  </configuration>
</plugin>
```

Packaging options for distribution:

- Plain jar + external JavaFX modules (supply `--module-path` at runtime).
- Shaded ("fat") jar (convenient, but bundles platform-specific binaries; less portable across OS/architectures).
- `jlink` custom runtime image (size + startup optimization).
- `jpackage` native bundle / app image (as in this project using `jpackage-maven-plugin`).

Runtime flags (manual execution example):

```bash
java \
  --module-path lib:$(dirname "$JAVA_HOME")/lib \
  --add-modules javafx.controls,javafx.fxml \
  -jar target/JavaFX-HelloWorld-1.0.jar
```

(Your project’s build already copies JavaFX jars into `target/lib`.)

---
 
## What is Maven

Full article: [What is Maven?](https://maven.apache.org/what-is-maven.html)

Conceptually, Maven is:

- A **model** (POM) describing a project.
- A **dependency resolver** pulling artifacts by coordinates from repositories.
- A **lifecycle executor** mapping abstract phases to concrete plugin goals.
- An **extensible platform** via plugins & extensions.


It is NOT just an "XML-based script"; instead of imperative steps (like Ant/Make), you rely on standard lifecycles and let plugins supply behavior.

---
 
## Installation

Official guide: [Install Maven](https://maven.apache.org/install.html)

Quick summary (macOS):

1. Ensure a JDK (e.g., `java -version` → 21 in this project).
2. Install (choices):
   - Homebrew: `brew install maven`
   - Manual: download binary zip → unpack → add `bin` to PATH.
3. Verify: `mvn -v` (should show Java & Maven versions + local repo path).
4. (Optional) Configure `~/.m2/settings.xml` for mirrors, proxies, repositories, credentials.

Local repository default: `~/.m2/repository` (cache of all downloaded & installed artifacts).

---
 
## Extension

Docs: [Maven Extensions](https://maven.apache.org/extensions/index.html)

Extensions modify core Maven behavior (loaded from `.mvn/extensions.xml`), examples:

- Polyglot POMs (alternative syntaxes: YAML, Groovy).
- Build acceleration / remote caches.
- Toolchains enhancements.

Difference vs plugin:

- Plugins bind goals to lifecycle phases (operate _during_ a build).
- Extensions can alter Maven core mechanics (class loading, wagon transports, etc.).

Usage sketch (`.mvn/extensions.xml`):
 
```xml
<extensions>
  <extension>
    <groupId>io.takari.aether</groupId>
    <artifactId>takari-local-repository</artifactId>
    <version>0.11.3</version>
  </extension>
</extensions>
```

---
 
## Plugins

Catalog: [Maven Plugin Index](https://maven.apache.org/plugins/index.html)

Plugins provide goals. Goals can:

- Produce artifacts (jar, war, javadoc, sources).
- Run code quality tools (surefire, failsafe, checkstyle, spotbugs).
- Manipulate dependencies (shade, dependency plugin).
- Package distributions (assembly, jpackage, appassembler).

Binding: A phase (e.g., `package`) triggers goals bound to it. You can also invoke goals directly (`mvn dependency:tree`).

In this project:

- `maven-compiler-plugin` compiles sources (`compile` phase).
- `maven-jar-plugin` packages the main jar (`package`).
- `maven-shade-plugin` creates an uber jar (attached classifier `fat`).
- `javafx-maven-plugin` runs the app (executed when calling its goal) with proper JavaFX args.
- `jpackage-maven-plugin` creates an application image via the JDK `jpackage` tool.
- `maven-dependency-plugin` copies runtime dependencies to `target/lib` (used for runtime & jpackage input).
- `maven-antrun-plugin` does pre-build cleanup (removing old .app bundle).

---
 
## Maven Build Config Fundamentals

Key POM elements (applied in this project):

| Concept | Purpose | Example (from project) |
|---------|---------|------------------------|
| groupId | Organizational namespace | `com.example` |
| artifactId | Project name (artifact) | `JavaFX-HelloWorld` |
| version | Artifact version | `1.0` |
| packaging | Type (`jar`, `pom`, etc.) | (defaults to `jar`) |
| properties | Reusable variables | `javafx.version`, compiler release |
| dependencies | Direct libs your code needs | JavaFX modules |
| dependencyManagement | Version control (esp. in multi-module) | (empty scaffold here) |
| build/plugins | Customize lifecycle behavior | compiler, shade, jpackage |
| distributionManagement | Where to deploy artifacts | local file-based repos |

Important notions:

- Effective POM: Result after inheritance + dependencyManagement resolution: `mvn help:effective-pom`.
- Scopes: `compile` (default), `provided`, `runtime`, `test`, `system`, `import` (BOM). JavaFX uses default `compile`.
- Transitive dependencies: Maven resolves nested dependencies automatically; manage versions via `dependencyManagement` or exclusions.
- Plugin executions: Each execution may have an `id`, bound `phase`, goals, and configuration block.
- Profiles: Conditional configuration (activate by JDK version, OS, property, or explicit `-P`). You could, for example, have OS-specific JavaFX classifiers in OS-bound profiles.
- Repositories: Remote artifact sources; by default includes Maven Central. You added a **file-based distributionManagement** for local deploys (`mvn deploy`).
- Local deploy vs install: `install` puts artifacts in local cache for _this machine_; `deploy` sends to a (possibly local file) remote repository for sharing.
- Reproducibility: Pin versions; avoid version ranges unless necessary.

Typical lifecycle mapping snapshot:
 
```text
validate -> (pre-flight checks)
compile  -> maven-compiler-plugin
test     -> surefire (not configured here because no tests) 
package  -> jar + shade + jpackage execution + copy-dependencies
verify   -> (additional checks) 
install  -> install jar(s) to ~/.m2/repository
deploy   -> publish to distributionManagement repository
```

Diagnostics / Insight commands:
 
```bash
mvn -q help:effective-pom
mvn -q help:effective-settings
mvn dependency:tree -Dincludes=org.openjfx
mvn dependency:analyze
mvn help:describe -Dplugin=shade -Ddetail
```

Managing JavaFX across platforms:

- Use classifiers (e.g., `javafx-controls:21.0.2:mac-aarch64`) only when assembling platform-specific bundles.
- For cross-platform source distribution, omit classifiers; let runtime selection happen via module path provisioning per OS.

When to use Shade vs jlink/jpackage:

- Shade: quick, single-jar convenience, larger & platform-tied when native libs included.
- jlink: custom runtime (JRE subset) for performance and size optimization.
- jpackage: user-friendly native app image/installer bundling your runtime image.

---
 
## Version History

See: [Maven Version History](https://maven.apache.org/docs/history.html)

Highlights (abridged perspective):

- 1.x: Early adoption; less robust transitive resolution.
- 2.x: Improved dependency resolution & build stability.
- 3.x: Rewrite for performance, better extension model, incremental improvements (current major line).
- Ongoing 3.x releases: Performance, parallel builds, improved resolver, reproducible build metadata.

Why it matters: Understanding the evolution helps interpret older guides or legacy POM patterns (e.g., explicit plugin versioning best practiced post-3.x to ensure reproducibility).

---
 
## References

- [Maven Homepage](https://maven.apache.org/)
- [Maven in Five Minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
- [Getting Started Guide Index](https://maven.apache.org/guides/getting-started/index.html)
- [Install Maven](https://maven.apache.org/install.html)
- [OpenJFX Maven Docs](https://openjfx.io/openjfx-docs/#maven)
- [What is Maven?](https://maven.apache.org/what-is-maven.html)
- [Maven Extensions](https://maven.apache.org/extensions/index.html)
- [Maven Plugin Index](https://maven.apache.org/plugins/index.html)
- [Maven Version History](https://maven.apache.org/docs/history.html)

---
_This document is a concise, original summary tailored to this JavaFX demo project; consult official docs for authoritative details and updates._
