## Javadoc in this project

### What is Javadoc?

**Javadoc** is the standard tool for generating API documentation from specially formatted comments in Java source code. It produces HTML pages describing packages, classes, methods, fields, parameters, return types, and exceptions.

Well‑written Javadoc helps others discover and use your code without reading the implementation.

### Minimal Javadoc example

Add comments above classes and methods using `/** ... */` and Javadoc tags like `@param`, `@return`, `@throws`.

```java
/**
 * Greets a user by name.
 */
public final class Greeter {

    /**
     * Builds a greeting message.
     *
     * @param name the user's name
     * @return a friendly greeting
     * @throws IllegalArgumentException if name is null or blank
     */
    public String greet(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        return "Hello, " + name + "!";
    }
}
```

This project now includes Javadoc for `com.example.App`, `PrimaryController`, `SecondaryController`, and a `package-info.java` overview.

### How to generate Javadoc for this project

- **With Maven (recommended):**
  - Generate HTML docs into `target/site/apidocs`:
    ```bash
    mvn -q -e -DskipTests javadoc:javadoc
    ```
  - Also attach a `-javadoc.jar` to the build (configured already):
    ```bash
    mvn -q -DskipTests package
    ```
    After packaging, find the jar under `target/JavaFX-HelloWorld-1.0-javadoc.jar`.

- **Open in a browser:**
  - Open `target/site/apidocs/index.html`.

### Notes specific to this project

- Javadoc links to JavaFX API are configured; online references will point to the OpenJFX docs.
- Doclint is relaxed to avoid warnings from FXML-injected members and private handlers.
- Public APIs are documented; private methods include brief comments where appropriate for clarity.

### Tips for writing good Javadoc

- **Start with a one‑line summary** in the first sentence.
- **Document each parameter and return value** with `@param` and `@return`.
- **Declare thrown exceptions** with `@throws` and state when they occur.
- Prefer describing the behavior and contracts, not implementation details.
- Use `{@link Type}` to reference other classes and methods.


