package com.screendead.capital.levels;

import com.screendead.capital.Texture;
import com.screendead.capital.graphics.Renderer;
import org.joml.Vector2f;

public enum Bricks {
    FLOOR(false, 1, 1),               // 0
    WALL_LEFT(true, 2, 1),            // 1
    WALL_TOP(true, 3, 1),             // 2
    WALL_RIGHT(true, 4, 1),           // 3
    WALL_BOTTOM(true, 5, 1),          // 4
    WALL_TOP_LEFT(true, 2, 2),        // 5
    WALL_TOP_RIGHT(true, 3, 2),       // 6
    WALL_BOTTOM_RIGHT(true, 4, 2),    // 7
    WALL_BOTTOM_LEFT(true, 5, 2),     // 8
    ROCK(true, 1, 2),                 // 9

    PLAYER(true, 7, 1);

    private boolean solid;
    private int x, y;

    Bricks(boolean solid, int x, int y) {
        this.solid = solid;
        this.x = x;
        this.y = y;
    }

    public float[] getTexture() {
        Vector2f pos = Renderer.TEXTURE.getTexturePosition(x, y);
        float sizeX = 1.0f / Texture.SIZE;
        float sizeY = 1.0f / Texture.SIZE;

        return new float[] {
                pos.x,          pos.y,
                pos.x,          pos.y + sizeY,
                pos.x + sizeX,  pos.y,
                pos.x + sizeX,  pos.y + sizeY,
        };
    }
}
