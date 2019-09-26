package com.screendead.capital.graphics;

import com.screendead.capital.gameplay.Moveable;
import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Camera implements Moveable {
    Vector2f pos, vel, acc;
    private static float SPEED = 1.0f / 256.0f;
    private float width, height, aspect, zoom;
    private float damping;
    private Matrix4f transform = new Matrix4f();

    public Camera(float width, float height) {
        this(new Vector2f(0.0f, 0.0f), width, height);
    }

    public Camera(Vector2f pos, float width, float height) {
        this.width = width;
        this.height = height;
        this.aspect = height / width;

        this.pos = pos;
        this.vel = new Vector2f();
        this.acc = new Vector2f();

        this.damping = 0.9f;

        update();
    }

    public void update() {
        update(0.0f, 1.0f);
    }

    public void update(float r, float s) {
        update(r, s, s);
    }

    @Override
    public void updatePosition() {
        this.vel.add(acc);
        this.pos.add(vel);
        this.acc.zero();
        this.vel.mul(this.damping);
    }

    @Override
    public void move(float h, float v, int direction) { }

    @Override
    public void setSpeed(float s) { }

    @Override
    public void setScale(float s) {
        this.zoom = s;
    }

    @Override
    public Vector2f getPos() {
        return pos;
    }

    @Override
    public Vector2f getVel() {
        return vel;
    }

    @Override
    public void updateTransform(float r, float sx, float sy) {
        this.transform = new Matrix4f().translation(zoom / 2.0f - pos.x, zoom / 2.0f - pos.y, 0.0f)
            .rotateYXZ(0.0f, 0.0f, (float) Math.toRadians(r))
            .scale(sx * zoom, sy * zoom, 1.0f);
    }

    public void move(float h, float v) {
        this.acc.add(h * SPEED, v * SPEED);
    }

    public Matrix4f matrix() {
        return this.transform;
    }
}
