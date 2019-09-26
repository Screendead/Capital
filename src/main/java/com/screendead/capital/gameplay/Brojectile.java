package com.screendead.capital.gameplay;

import com.screendead.capital.graphics.Mesh;
import com.screendead.capital.levels.Bricks;
import org.joml.Vector2f;

public class Brojectile extends Entity {
    public Brojectile(Vector2f pos, Vector2f vel, int direction) {
        super.setSpeed(1.0f / 32.0f);
        super.setScale(1.0f / 12.0f);

        this.pos = pos;
        if (vel.length() == 0.0f) {
            switch (direction) {
                case 0: // Up
                    vel.add( 0.0f, -1.0f);
                    break;
                case 1: // Left
                    vel.add(-1.0f,  0.0f);
                    break;
                case 2: // Down
                    vel.add( 0.0f,  1.0f);
                    break;
                case 3: // Right
                    vel.add( 1.0f,  0.0f);
                    break;
            }
        }
        this.vel = vel.normalize().mul(speed);
        this.acc = new Vector2f();

        this.meshes = Mesh.generate(new Bricks[] {
                Bricks.BROJECTILE_UP,
                Bricks.BROJECTILE_LEFT,
                Bricks.BROJECTILE_DOWN,
                Bricks.BROJECTILE_RIGHT,
        });

        this.mesh = direction;

        this.damping = 1.0f;

        update();
    }

    public void move(float h, float v, int direction) { }
}
