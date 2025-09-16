# JavaFX Hello World – Javadoc Focus

This project demonstrates a minimal JavaFX app and how to generate its Javadoc.

## Generate Javadoc

HTML API docs are produced into `target/site/apidocs`.

```bash
mvn -q -DskipTests javadoc:javadoc
```

Then open `target/site/apidocs/index.html` in your browser.

## Javadoc Jar (optional)

Packaging also creates a `-javadoc.jar`:

```bash
mvn -q -DskipTests package
```

Find it under `target/JavaFX-HelloWorld-1.0-javadoc.jar`.

## More details

See the full guide with examples: [docs/javadoc.md](docs/javadoc.md)