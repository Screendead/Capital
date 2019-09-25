package com.screendead.capital.graphics;

import com.screendead.capital.levels.Brick;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private static ArrayList<Integer> vboList = new ArrayList<>();
    private final int vao, vertexCount;

    public Mesh(float[] vertices, float[] tex_coords, int[] indices) {
        FloatBuffer vertBuffer = null, texBuffer = null;
        IntBuffer indicesBuffer = null;

        try {
            vertexCount = indices.length;
            vboList = new ArrayList<>();

            vao = glGenVertexArrays();
            glBindVertexArray(vao);

            // Position VBO
            int vbo = glGenBuffers();
            vboList.add(vbo);
            vertBuffer = MemoryUtil.memAllocFloat(vertices.length);
            vertBuffer.put(vertices).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, vertBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

            // Texture coordinates VBO
            vbo = glGenBuffers();
            vboList.add(vbo);
            texBuffer = MemoryUtil.memAllocFloat(tex_coords.length);
            texBuffer.put(tex_coords).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, texBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, true, 0, 0);

            // Index VBO
            vbo = glGenBuffers();
            vboList.add(vbo);
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ARRAY_BUFFER, 0);

            glBindVertexArray(0);
        } finally {
            if (vertBuffer != null) MemoryUtil.memFree(vertBuffer);
            if (texBuffer != null) MemoryUtil.memFree(texBuffer);
            if (indicesBuffer != null) MemoryUtil.memFree(indicesBuffer);
        }
    }

    public static Mesh generate(Brick[] bricks) {
        float[] v = new float[bricks.length * Brick.VERTICES_LENGTH],
                t = new float[bricks.length * Brick.VERTICES_LENGTH];
        int[] i = new int[bricks.length * Brick.INDICES_LENGTH];

        for (int a = 0; a < bricks.length; a++) {
            for (int b = 0; b < Brick.VERTICES_LENGTH; b++) {
                v[a * Brick.VERTICES_LENGTH + b] = bricks[a].vertices[b];
                t[a * Brick.VERTICES_LENGTH + b] = bricks[a].texture[b];
            }

            for (int b = 0; b < Brick.INDICES_LENGTH; b++) {
                i[a * Brick.INDICES_LENGTH + b] = Brick.INDICES[b] + a * (Brick.INDICES_LENGTH + 2) / 2;
            }
        }

        return new Mesh(v, t, i);
    }

    /**
     * Render this mesh to the framebuffer
     */
    public void render() {
        // Draw the mesh
        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);

        // Restore state
        glBindVertexArray(0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }

    /**
     * Clean the memory after removal
     */
    public void cleanup() {
        glDisableVertexAttribArray(0);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for (int vboId : vboList) {
            glDeleteBuffers(vboId);
        }

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vao);
    }

//    public static void setGlobalTexture(Image t) {
//        texture = t;
//    }

    /**
     * @return vao The vertex array object
     */
    public int getVAO() {
        return vao;
    }

    /**
     * @return vertexCount The number of vertices in the mesh
     */
    public int getVertexCount() {
        return vertexCount;
    }
}
