# PR Checklist

Paste and complete this checklist before requesting review:

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

References:

- Google Java Style Guide: [google.github.io/styleguide/javaguide.html](https://google.github.io/styleguide/javaguide.html)
