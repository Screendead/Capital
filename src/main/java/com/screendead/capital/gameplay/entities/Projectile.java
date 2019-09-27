package com.screendead.capital.gameplay.entities;

import org.joml.Vector2f;

public abstract class Projectile extends Entity {
    public Projectile(Vector2f pos, Vector2f vel, int direction) {
        this.pos = pos;
        this.vel = vel;
        this.acc = new Vector2f();

        this.mesh = direction;

        this.damping = 1.0f;
    }
}
