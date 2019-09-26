package com.screendead.capital.gameplay;

import com.screendead.capital.graphics.Mesh;
import org.joml.Matrix4f;
import org.joml.Vector2f;

// TODO: Add collision detection
public abstract class Entity implements Moveable {
    public static int TICKS = 0;
    float speed, scale;

    Vector2f pos, vel, acc;
    Mesh[] meshes;
    int mesh;
    boolean solid = true;
    float damping, rotation;
    private Matrix4f transform;

    public void updatePosition() {
        this.vel.add(acc);
        this.pos.add(vel);
        this.acc.zero();
        this.vel.mul(this.damping);

        if (this.vel.length() < 0.001f) this.vel.zero();
    }

    public void updateTransform(float r, float sx, float sy) {
        this.transform = new Matrix4f().translation(pos.x, pos.y, 0.0f)
                .rotateYXZ(0.0f, 0.0f, (float) Math.toRadians(r + this.rotation))
                .scale(sx * scale, sy * scale, 1.0f);
    }

    public void setSpeed(float s) {
        this.speed = s;
    }

    public void setScale(float s) {
        this.scale = s;
    }

    public void render() {
        this.meshes[mesh].render();
    }

    public Vector2f getPos() {
        return new Vector2f(pos.x, pos.y);
    }

    public Vector2f getVel() {
        return new Vector2f(vel.x, vel.y);
    }

    public Matrix4f matrix() {
        return this.transform;
    }
}
