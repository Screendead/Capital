package com.screendead.capital;

import com.screendead.capital.graphics.Window;

import javax.swing.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Capital {
    private Window window;
    private float UPS = 60.0f, FPS = 60.0f;

    /**
     * Begin the game
     */
    protected void run() {
        // Create the window
        window = new Window("Capital", 800, 600, true, true);

        // Start the game loop
        loop();

        // Destroy the window after exit
        window.destroy();
    }

    /**
     * The main game loop
     */
    private void loop() {
        long initialTime = System.nanoTime();
        final float timeU = 1000000000.0f / UPS;
        final float timeF = 1000000000.0f / FPS;
        float deltaU = 0, deltaF = 0;
        int frames = 0, ticks = 0, totalTicks = 0;
        long timer = System.currentTimeMillis();

        while (!glfwWindowShouldClose(window.getHandle())) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if (deltaU >= 1) {
                glfwPollEvents();
                window.update(totalTicks);
                ticks++;
                totalTicks++;
                deltaU--;
            }

            if (deltaF >= 1) {
                window.render();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
//                System.out.println(String.format("UPS: %s, FPS: %s", ticks, frames));
                frames = 0;
                ticks = 0;
                timer += 1000;
            }
        }
    }
}
