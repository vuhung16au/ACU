## Generic Sort (Bubble, Merge, Quick)

This module demonstrates generic sorting algorithms in Java with two variants each:

- Comparable-based: `E extends Comparable<E>`
- Comparator-based: `Comparator<? super E>`

The JavaFX app shows a tiny UI to pick algorithm and order, then sorts a sample list.

Run:

```bash
mvn clean compile
mvn test
mvn javafx:run
```

Structure follows other modules like `05-02-BinaryIO` (docs, images, src, test).


