package com.screendead.capital.gameplay;

import com.screendead.capital.graphics.Mesh;
import com.screendead.capital.levels.Brick;
import com.screendead.capital.levels.Bricks;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Player {
    private static float SPEED = 1.0f / 1024.0f;
    private Vector2f pos, vel, acc;
    private Mesh mesh;
    private Matrix4f transform;

    public Player() {
        this.pos = new Vector2f(-0.0f, -0.0f);
        this.vel = new Vector2f();
        this.acc = new Vector2f();

        this.mesh = Mesh.generate(new Brick[] { Brick.generate(Bricks.PLAYER, 0, 0) });

        update();
    }

    public void update() {
        update(0.0f, 1.0f);
    }

    public void update(float r, float s) {
        update(r, s, s);
    }

    /**
     * Update `pos`, `vel`, `acc` values
     */
    public void update(float r, float sx, float sy) {
        this.vel.add(acc);
        this.pos.add(vel);
        // TODO: Add collision detection
//        this.pos = pos.max(this.min).min(this.max);
        this.vel.mul(0.9f);
        this.acc.zero();

        this.transform = new Matrix4f().translation(pos.x, pos.y, 0.0f)
                .rotateYXZ(0.0f, 0.0f, (float) Math.toRadians(r))
                .scale(sx, sy, 1.0f);
    }

    /**
     * @param h The horizontal aspect of movement
     * @param v The vertical aspect of movement
     */
    public void move(int h, int v) {
        this.acc.add(h * SPEED, v * SPEED);
    }

    public void render() {
        this.mesh.render();
    }

    public Matrix4f matrix() {
        return this.transform;
    }
}
