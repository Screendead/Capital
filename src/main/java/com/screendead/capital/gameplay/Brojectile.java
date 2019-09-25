package com.screendead.capital.gameplay;

import com.screendead.capital.graphics.Mesh;
import com.screendead.capital.levels.Brick;
import com.screendead.capital.levels.Bricks;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Brojectile {
    private static float SPEED = 8.0f;
    private Vector2f pos, vel, acc;
    private float rotation = 0.0f;
    private Mesh mesh;
    private Matrix4f transform;

    public Brojectile(Vector2f pos, Vector2f vel) {
        this.pos = pos;
        this.vel = vel.mul(SPEED);
        this.acc = new Vector2f();

        this.mesh = Mesh.generate(new Brick[] { Brick.generate(Bricks.BROJECTILE, 0, 0) });

        rotate();
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
//        this.vel.mul(1.0f);
        this.acc.zero();

        this.transform = new Matrix4f().translation(pos.x, pos.y, 0.0f)
                .rotateYXZ(0.0f, 0.0f, (float) Math.toRadians(this.rotation + r))
                .scale(sx, sy, 1.0f);
    }

    /**
     * Rotate the mesh to correct orientation
     */
    public void rotate() {
        if (this.vel.x < 0.0f) this.rotation = 90.0f;
        if (this.vel.x > 0.0f) this.rotation = 270.0f;
        if (this.vel.y < 0.0f) this.rotation = 180.0f;
        if (this.vel.y > 0.0f) this.rotation = 0.0f;
    }

    public void render() {
        this.mesh.render();
    }

    public Matrix4f matrix() {
        return this.transform;
    }
}
