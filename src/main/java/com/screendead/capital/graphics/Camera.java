package com.screendead.capital.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Camera {
    Vector2f pos, vel, acc;
    private static float SPEED = 1.0f / 256.0f;
    private float width, height, aspect;
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

        update();
    }

    public void update() {
        update(0.0f, 1.0f);
    }

    public void update(float r, float s) {
        update(r, s, s);
    }

    /**
     * Set the transformation matrix for the shader
     * Rotation order is YXZ
     * @param r Degrees of rotation about the Z axis
     * @param sx X component of the scale
     * @param sy Y component of the scale
     */
    public void update(float r, float sx, float sy) {
        this.vel.add(acc);
        this.pos.add(vel);
        this.vel.mul(0.9f);
        this.acc.zero();

        this.transform = new Matrix4f().translation(-pos.x, -pos.y, 0.0f)
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

//    private float constrain(float f, float min, float max) {
//        return Math.min(Math.max(f, min), max);
//    }

    public Matrix4f matrix() {
        return this.transform;
    }
}
