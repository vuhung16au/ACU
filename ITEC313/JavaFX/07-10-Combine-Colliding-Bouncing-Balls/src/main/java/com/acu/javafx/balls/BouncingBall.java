package com.acu.javafx.balls;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A single bouncing ball with simple physics and hit testing.
 */
class BouncingBall {
    final Circle node;
    double dx, dy;
    double radius;

    BouncingBall(double x, double y, double r, double dx, double dy, Color color) {
        this.node = new Circle(x, y, r, color);
        this.radius = r;
        this.dx = dx; this.dy = dy;
    }

    void move(double w, double h) {
        double x = node.getCenterX() + dx;
        double y = node.getCenterY() + dy;
        if (x - radius < 0 || x + radius > w) { dx = -dx; x = clamp(x, radius, w - radius); }
        if (y - radius < 0 || y + radius > h) { dy = -dy; y = clamp(y, radius, h - radius); }
        node.setCenterX(x);
        node.setCenterY(y);
    }

    boolean collides(BouncingBall other) {
        double dx = node.getCenterX() - other.node.getCenterX();
        double dy = node.getCenterY() - other.node.getCenterY();
        double dist2 = dx*dx + dy*dy;
        double r = radius + other.radius;
        return dist2 <= r * r;
    }

    void absorbRadius(double add) {
        radius += add;
        node.setRadius(radius);
    }

    boolean contains(double x, double y) {
        double dx = node.getCenterX() - x;
        double dy = node.getCenterY() - y;
        return dx*dx + dy*dy <= radius*radius;
    }

    private static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }
}


