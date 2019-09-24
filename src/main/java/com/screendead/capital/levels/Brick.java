package com.screendead.capital.levels;

import com.screendead.capital.graphics.Mesh;

public class Brick {
    public static final int VERTICES_LENGTH = 8;
    public static final int INDICES_LENGTH = 6;

    private static final float[] VERTICES = new float[] {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
    };
    public static final int[] INDICES = new int[] {
            0, 1, 2,
            1, 3, 2,
    };

    public float[] texture, vertices;

    private Mesh mesh;

    private Brick(float[] v, float[] t) {
        this.vertices = v;
        this.texture = t;

        this.mesh = new Mesh(vertices, texture, INDICES);
    }

    public static Brick generate(Bricks b, float x, float y) {
        float[] v = VERTICES.clone();

        for (int i = 0; i < v.length; i++) {
            if (i % 2 == 0) v[i] += x;
            else v[i] += y;
        }

        return new Brick(v, b.getTexture());
    }
}
