package com.screendead.capital.gameplay.entities;

import com.screendead.capital.graphics.Mesh;
import com.screendead.capital.levels.Bricks;
import org.joml.Vector2f;

public class PaperBall extends Projectile {
    public PaperBall(Vector2f pos, Vector2f vel, int direction) {
        super(pos, vel, direction);

        Vector2f v = vel;

        if (v.length() <= 0.001f) {
            switch (direction) {
                case 0: // Up
                    v = new Vector2f( 0.0f, -1.0f);
                    break;
                case 1: // Left
                    v = new Vector2f(-1.0f,  0.0f);
                    break;
                case 2: // Down
                    v = new Vector2f( 0.0f,  1.0f);
                    break;
                case 3: // Right
                    v = new Vector2f( 1.0f,  0.0f);
                    break;
            }
        }
        this.vel = v.normalize().mul(speed);

        this.meshes = Mesh.generate(new Bricks[] {
                Bricks.PAPER_BALL,
        });

        this.mesh = 0;

        update();
    }
}
