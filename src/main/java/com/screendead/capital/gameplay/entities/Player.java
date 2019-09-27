package com.screendead.capital.gameplay.entities;

import com.screendead.capital.graphics.Mesh;
import com.screendead.capital.levels.Bricks;
import org.joml.Vector2f;

public class Player extends Entity {
    public Player() {
        super.setSpeed(1.0f / 1024.0f);
        super.setScale(1.0f / 6.0f);

        this.pos = new Vector2f(-0.0f, -0.0f);
        this.vel = new Vector2f();
        this.acc = new Vector2f();

        this.meshes = Mesh.generate(new Bricks[] {
                Bricks.PLAYER_BACK,
                Bricks.PLAYER_LEFT,
                Bricks.PLAYER_FRONT,
                Bricks.PLAYER_RIGHT,
        });

        this.mesh = 0;

        this.damping = 0.9f;

        update();
    }

    public void move(float h, float v, int direction) {
        this.acc.add(h * speed, v * speed);

        this.mesh = direction;
    }
}
