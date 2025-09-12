## 07-11 — Hangman Game

JavaFX Hangman: type letters to guess the word; seven misses draw the full hangman. Press ENTER to start a new word when done.

### Logic (short and sharp)

- Keep target `word`, `guessed` letters and `missed` letters.
- On key press, if letter in word → add to `guessed`; else add to `missed` (ignore duplicates).
- Display masked word using `*` for unknown letters; win when no `*` remains.
- Lose after 7 misses; draw hangman parts progressively.

### Run

- `mvn clean compile`
- `mvn test`
- `mvn -pl 07-11-Hangman-Game javafx:run`


