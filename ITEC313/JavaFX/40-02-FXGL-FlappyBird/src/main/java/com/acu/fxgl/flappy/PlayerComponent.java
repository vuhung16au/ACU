package com.acu.fxgl.flappy;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 */
public class PlayerComponent extends Component {

    private Vec2 acceleration = new Vec2(2, 0);

    @Override
    public void onUpdate(double tpf) {
        acceleration.x += tpf * 0.05;
        acceleration.y += tpf * 5;

        if (acceleration.y < -3)
            acceleration.y = -3;

        if (acceleration.y > 3)
            acceleration.y = 3;

        entity.translate(acceleration.x, acceleration.y);

        if (entity.getBottomY() > getAppHeight()) {
            FXGL.<FlappyBirdApp>getAppCast().requestNewGame();
        }
    }

    public void jump() {
        acceleration.addLocal(0, -3);

        play("jump.wav");
    }
}
