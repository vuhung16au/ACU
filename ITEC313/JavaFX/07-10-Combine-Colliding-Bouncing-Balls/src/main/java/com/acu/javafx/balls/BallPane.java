package com.acu.javafx.balls;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Pane that hosts and updates bouncing balls.
 */
class BallPane extends javafx.scene.layout.Pane {
    private final List<BouncingBall> balls = new ArrayList<>();
    private final Random rnd = new Random();

    BallPane() {
        setPrefSize(640, 360);
        for (int i = 0; i < 6; i++) addRandomBall();
        setOnMousePressed(e -> {
            BouncingBall hit = null;
            for (BouncingBall b : balls) {
                if (b.contains(e.getX(), e.getY())) { hit = b; break; }
            }
            if (hit != null) removeBall(hit);
        });
    }

    void addRandomBall() {
        double r = 10 + rnd.nextDouble() * 20;
        double x = r + rnd.nextDouble() * Math.max(1, getWidth() - 2 * r);
        double y = r + rnd.nextDouble() * Math.max(1, getHeight() - 2 * r);
        double dx = (rnd.nextDouble() - 0.5) * 4;
        double dy = (rnd.nextDouble() - 0.5) * 4;
        Color c = Color.gray(rnd.nextDouble() * 0.6 + 0.2);
        BouncingBall ball = new BouncingBall(x, y, r, dx, dy, c);
        balls.add(ball);
        getChildren().add(ball.node);
    }

    void removeOneBall() {
        if (balls.isEmpty()) return;
        BouncingBall b = balls.remove(balls.size() - 1);
        getChildren().remove(b.node);
    }

    void removeBall(BouncingBall b) {
        balls.remove(b);
        getChildren().remove(b.node);
    }

    void step() {
        double w = getWidth(), h = getHeight();
        for (BouncingBall b : balls) b.move(w, h);
        for (int i = 0; i < balls.size(); i++) {
            for (int j = balls.size() - 1; j > i; j--) {
                BouncingBall a = balls.get(i), b = balls.get(j);
                if (a.collides(b)) {
                    a.absorbRadius(b.radius);
                    removeBall(b);
                }
            }
        }
    }
}


