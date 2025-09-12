## 07-10 — Combine Colliding Bouncing Balls

JavaFX app showing bouncing balls that combine when they collide. Controls: Suspend, Resume, +, -.

### Logic (short and sharp)

- Each ball has position, velocity, and radius; walls cause velocity sign flip.
- On each frame, move all balls, then check pairs (i<j). If overlapping, keep the earlier ball and increase its radius by the later ball's radius; remove the later ball from the pane.
- Mouse press removes the clicked ball.
- Buttons pause/resume animation and add/remove balls.

### Run

- `mvn clean compile`
- `mvn test`
- `mvn -pl 07-10-Combine-Colliding-Bouncing-Balls javafx:run`


