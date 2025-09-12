## 07-09 — Store Numbers in a Linked List

A small JavaFX app to enter integers, store them in a LinkedList without duplicates, and Sort / Shuffle / Reverse the list.

### Run

- `mvn clean compile`
- `mvn test`
- `mvn javafx:run`

### Core logic (short and sharp)

```java
LinkedList<Integer> data = new LinkedList<>();
void addUnique(int v){ if(!data.contains(v)) data.add(v); }
void sort(){ java.util.Collections.sort(data); }
void shuffle(){ java.util.Collections.shuffle(data); }
void reverse(){ java.util.Collections.reverse(data); }
String join(){ return data.stream().map(String::valueOf).collect(java.util.stream.Collectors.joining(" ")); }
```

### Notes

- Minimal code, with comments for learning purposes.
- UI mimics the exercise screenshot: input box, text area, and three buttons.


