## External Sort

Short and sharp: Generate a large file of integers and sort it using a simple two-phase external sort.

- Create: `CreateLargeFile` writes N random integers (one per line).
- Sort: `SortLargeFile` does run generation (read chunk, sort, write temp run) and a k-way merge with a min-heap.
- UI: `ExternalSortApp` offers buttons to create and sort files for demonstration.

Run:
- `mvn clean compile`
- `mvn test`
- `mvn javafx:run`


