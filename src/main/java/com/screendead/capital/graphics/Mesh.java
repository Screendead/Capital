package com.screendead.capital.graphics;

import com.screendead.capital.Texture;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_TEXTURE0;
import static org.lwjgl.opengl.GL15.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL15.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.glActiveTexture;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBindTexture;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glDrawElements;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
//    private static Image texture;
    private static ArrayList<Integer> vboList = new ArrayList<>();
    private final int vao, vertexCount;
    private Texture texture;

    public Mesh(String source, float x, float y, float w, float h) {
        this(source,
                new float[] {
                        x - w / 2, y - h / 2,  0.0f,
                        x + w / 2, y - h / 2,  0.0f,
                        x - w / 2, y + h / 2,  0.0f,
                        x + w / 2, y + h / 2,  0.0f,
                }, new float[] {
                        0.0f,  0.0f,  0.0f,
                        0.0f,  0.0f,  0.0f,
                        0.0f,  0.0f,  0.0f,
                        0.0f,  0.0f,  0.0f,
                }, new float[] {
                        0.0f, 0.0f,
                        w,    0.0f,
                        0.0f, h,
                        w,    h,
                }, new int[] {
                        0, 2, 1,
                        2, 3, 1,
                });
    }

    public Mesh(String source, float[] vertices, float[] normals, float[] tex_coords, int[] indices) {
        this.texture = new Texture(source);

        FloatBuffer vertBuffer = null, normsBuffer = null, texBuffer = null;
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
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Normals VBO
            vbo = glGenBuffers();
            vboList.add(vbo);
            normsBuffer = MemoryUtil.memAllocFloat(normals.length);
            normsBuffer.put(normals).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, normsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

//            for (int i = 0; i < tex_coords.length; i++) tex_coords[i] /= ((float) ((i % 2 == 0) ? texture.getSize().x : texture.getSize().y) / 16.0f);

            // Texture coordinates VBO
            vbo = glGenBuffers();
            vboList.add(vbo);
            texBuffer = MemoryUtil.memAllocFloat(tex_coords.length);
            texBuffer.put(tex_coords).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, texBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(2, 2, GL_FLOAT, true, 0, 0);

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
            if (normsBuffer != null) MemoryUtil.memFree(normsBuffer);
            if (texBuffer != null) MemoryUtil.memFree(texBuffer);
            if (indicesBuffer != null) MemoryUtil.memFree(indicesBuffer);
        }
    }

    /**
     * Render this mesh to the framebuffer
     */
    public void render() {
        if (texture != null) {
            // Bind the texture
            this.texture.bind();

            // Draw the mesh
            glBindVertexArray(vao);
            glEnableVertexAttribArray(0);
            glEnableVertexAttribArray(1);
            glEnableVertexAttribArray(2);

            glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);

            // Restore state
            glBindVertexArray(0);
            glDisableVertexAttribArray(0);
            glDisableVertexAttribArray(1);
            glDisableVertexAttribArray(2);

            Texture.unbind();
        }
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
