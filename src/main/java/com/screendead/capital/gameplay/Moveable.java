package com.screendead.capital.gameplay;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public interface Moveable {
    int TICKS = 0;
    float speed = 0;


    /**
     * Update physics-related values
     */
    default void update() {
        update(0.0f, 1.0f);
    }


    /**
     * Update physics-related values
     */
    default void update(float r, float s) {
        update(r, s, s);
    }

    /**
     * Update physics-related values
     */
    default void update(float r, float sx, float sy) {
        updatePosition();
        updateTransform(r, sx, sy);
    }

    /**
     * Update `pos`, `vel`, `acc` variables
     */
    void updatePosition();

    /**
     * @param h The horizontal aspect of movement
     * @param v The vertical aspect of movement
     */
    void move(float h, float v, int direction);

    void setSpeed(float s);

    void setScale(float s);

    Vector2f getPos();

    Vector2f getVel();

    void updateTransform(float r, float sx, float sy);

    Matrix4f matrix();
}
