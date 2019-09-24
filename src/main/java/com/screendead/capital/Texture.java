package com.screendead.capital;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;

import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

import static com.screendead.capital.Main.load;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_BASE_LEVEL;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_MAX_LEVEL;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.GL_TEXTURE_LOD_BIAS;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {
    public static int SIZE = 32;
    private int id, width, height;
    private ByteBuffer raw_image;

    public Texture(String source) {
        // Get an image for texturing
        IntBuffer w = null, h = null, channels = null;
        try {
            w = MemoryUtil.memAllocInt(1);
            h = MemoryUtil.memAllocInt(1);
            channels = MemoryUtil.memAllocInt(1);

            STBImage.stbi_set_flip_vertically_on_load(false);

            String path = URLDecoder.decode(load(source).getPath().replace("file:", "").substring(1), StandardCharsets.US_ASCII);

            // Load the texture
            this.raw_image = STBImage.stbi_load(path, w, h, channels, 4);
            if (this.raw_image == null) throw new RuntimeException("Texture " + source + " failed to load.");

            // Store width and height values
            width = w.get();
            height = h.get();

            // Create texture ID
            id = glGenTextures();
            if (id == 0) throw new RuntimeException("Failed to allocate texture ID.");

            glActiveTexture(GL_TEXTURE0);

            // Bind the texture
            this.bind();

            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

            // Flip the buffer for reading
            this.raw_image.flip();

            // Pass texture data to the graphics card
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, this.raw_image);

            // Mipmap the texture
            glGenerateMipmap(GL_TEXTURE_2D);

            // Set texture parameters
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_BASE_LEVEL, 0);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAX_LEVEL, 4);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, 0.0f);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            // Unbind the texture
            Texture.unbind();
        } catch (Exception e) {
            System.err.println("FAILURE REASON: " + STBImage.stbi_failure_reason());
            e.printStackTrace();
            throw new RuntimeException(e.getClass() + ": " + e.getMessage());
        } finally {
            // Free memory locations of buffers
            if (w != null) MemoryUtil.memFree(w);
            if (h != null) MemoryUtil.memFree(h);
            if (channels != null) MemoryUtil.memFree(channels);
        }
    }

    /**
     * @return The texture ID
     */
    int getID() {
        return id;
    }

    /**
     * @return The result of {@code STBImage.stbi_load} for this resource
     */
    public ByteBuffer getRawImage() {
        return raw_image;
    }

    /**
     * @return A Vector2i storing the width and height of the image in pixels
     */
    public Vector2i getSize() {
        return new Vector2i(width, height);
    }

    /**
     * @return The width of the image in pixels
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return The height of the image in pixels
     */
    public int getHeight() { return height; }

    /**
     * @param x The location of the desired sub-texture along the x-axis, measured in sub-texture-count
     * @param y The location of the desired sub-texture along the y-axis, measured in sub-texture-count
     * @return The actual location, in pixels, of the top-left coordinate of the xth and yth sub-texture along those respective axes
     */
    public Vector2f getTexturePosition(int x, int y) {
        x -= 1;
        y -= 1;

        return new Vector2f((float) x / SIZE, (float) y / SIZE);
    }

    /**
     * Bind this image to GL_TEXTURE_2D
     */
    public void bind() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    /**
     * Unbind GL_TEXTURE_2D
     */
    public static void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    /**
     * Clean the memory after removal
     */
    public void cleanup() {
        glDeleteTextures(id);
    }
}
