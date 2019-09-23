package com.screendead.capital.graphics;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

public class Renderer {
    private Shader shader;
    private Matrix4f view = new Matrix4f();
    private Mesh testMesh;

    /**
     * Render to the framebuffer
     */
    public void render(Camera camera) {
        // Clear the framebuffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // Update the camera in the shader
        shader.bind();
        shader.setUniform("transform", camera.matrix());
        Shader.unbind();

        // Render the chunk mesh
        shader.bind();
        {
                testMesh.render();
        }
        Shader.unbind();
    }

    /**
     * Initialise OpenGL context for use with this window
     */
    public void init() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Enable 2D texturing
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glEnable(GL_BLEND);
        glEnable(GL_MULTISAMPLE);

        // OpenGL settings
        glCullFace(GL_BACK);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // Create texture and shader
        shader = new Shader("shaders/simple");
        shader.addUniform("transform");
        shader.addUniform("view");
        shader.addUniform("tex");

        // Set the sampler2D to 0
        shader.bind();
        shader.setUniform("tex", 0);
        Shader.unbind();

        testMesh = new Mesh("images/atlas.png", 0.0f, 0.0f, 16.0f, 9.0f);

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    /**
     * Set the OpenGL viewport transformation and update the viewMatrix
     * @param width The window width
     * @param height The window height
     */
    public void setViewport(float width, float height) {
        // Set the viewport
        glViewport(0, 0, (int) width, (int) height);

        float aspect = height / width;

        // Set the viewMatrix
        view = new Matrix4f().ortho2D(-1.0f, 1.0f, aspect, -aspect);

        // Update the viewMatrix in the shader
        shader.bind();
        shader.setUniform("view", view);
        Shader.unbind();
    }
}
