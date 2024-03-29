package com.screendead.capital;

import com.screendead.capital.graphics.Window;
import org.joml.Vector2i;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    public boolean[] keys = new boolean[68836];
    public boolean[] mods = new boolean[68836];

    public float x, y, dx, dy;
    public int last_move;

    private GLFWWindowSizeCallback windowSizeCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWScrollCallback scrollCallback;
    private GLFWWindowFocusCallback windowFocusCallback;

    private Window window;

    public Input(Window window) {
        this.window = window;

        Vector2i size = window.getSize();
        x = (float) size.x / 2;
        y = (float) size.y / 2;

        init();
    }

    private void onWindowSizeChanged(int width, int height) {
        window.setSize(width, height);
    }

    private void onFocusChanged(boolean focused) {

    }

    private void onKeyPress(int key, int scancode, int mod) {
        keys[key] = true;
        mods[mod] = true;

        if (key == GLFW_KEY_W) last_move = 0; // Up
        if (key == GLFW_KEY_A) last_move = 1; // Left
        if (key == GLFW_KEY_S) last_move = 2; // Down
        if (key == GLFW_KEY_D) last_move = 3; // Right
    }

    private void onKeyRelease(int key, int scancode, int mod) {
        keys[key] = false;
        mods[mod] = false;
    }

    private void onKeyRepeat(int key, int scancode, int mod) {

    }

    private void onMouseButtonPress(int button, int mod) {

    }

    private void onMouseButtonRelease(int button, int mod) {

    }

    private void onMouseButtonRepeat(int button, int mod) {

    }

    private void onMouseMove(float xpos, float ypos) {
        Vector2i size = window.getSize();
        x = (float) size.x / 2;
        y = (float) size.y / 2;

//        glfwSetCursorPos(window.getHandle(), x, y);

        dx = xpos - x;
        dy = ypos - y;
    }

    private void onMouseScroll(double xoffset, double yoffset) {

    }

    private void init() {
        glfwSetWindowSizeCallback(window.getHandle(),
                windowSizeCallback = new GLFWWindowSizeCallback() {

                    @Override
                    public void invoke(long window, int width, int height) {
                        /*
                         * window - the window that received the event
                         * width - the new width
                         * height - the new height
                         */
                        onWindowSizeChanged(width, height);
                    }
                });

        glfwSetCursorPosCallback(window.getHandle(),
                cursorPosCallback = new GLFWCursorPosCallback() {

                    @Override
                    public void invoke(long window, double xpos, double ypos) {
                        /*
                         * window - the window that received the event
                         * xpos - the new absolute x-value of the cursor
                         * ypos - the new absolute y-value of the cursor
                         */
                        onMouseMove((float) xpos, (float) ypos);
                    }
                });

        glfwSetKeyCallback(window.getHandle(),
                keyCallback = new GLFWKeyCallback() {

                    @Override
                    public void invoke(long window, int key, int scancode, int action, int mods) {
                        /*
                         * window - the window that received the event
                         * key - the keyboard key that was pressed or released
                         * scancode - the system-specific scancode of the key
                         * action - the key action [GLFW.GLFW_PRESS; GLFW.GLFW_RELEASE; GLFW.GLFW_REPEAT]
                         * mods - bitfield describing which modifier keys were held down
                         */
                        switch (action) {
                            case GLFW_PRESS:
                                onKeyPress(key, scancode, mods);
                                break;
                            case GLFW_RELEASE:
                                onKeyRelease(key, scancode, mods);
                                break;
                            case GLFW_REPEAT:
                                onKeyRepeat(key, scancode, mods);
                                break;
                        }
                    }
                });

        glfwSetMouseButtonCallback(window.getHandle(),
                mouseButtonCallback = new GLFWMouseButtonCallback() {

                    @Override
                    public void invoke(long window, int button, int action, int mods) {
                        /*
                         * window - the window that received the event
                         * button - the mouse button that was pressed or released
                         * action - the key action [GLFW.GLFW_PRESS; GLFW.GLFW_RELEASE; GLFW.GLFW_REPEAT]
                         * mods - bitfield describing which modifier keys were held down
                         */
                        switch (action) {
                            case GLFW_PRESS:
                                onMouseButtonPress(button, mods);
                                break;
                            case GLFW_RELEASE:
                                onMouseButtonRelease(button, mods);
                                break;
                            case GLFW_REPEAT:
                                onMouseButtonRepeat(button, mods);
                                break;
                        }
                    }
                });

        glfwSetScrollCallback(window.getHandle(),
                scrollCallback = new GLFWScrollCallback() {

                    @Override
                    public void invoke(long window, double xoffset, double yoffset) {
                        /*
                         * window - the window that received the event
                         * xoffset - the scroll offset along the x-axis
                         * yoffset - the scroll offset along the y-axis
                         */
                        onMouseScroll(xoffset, yoffset);
                    }
                });

        glfwSetWindowFocusCallback(window.getHandle(),
                windowFocusCallback = new GLFWWindowFocusCallback() {

                    @Override
                    public void invoke(long window, boolean focused) {
                        /*
                         * window - the window that received the event
                         * focused - [GL11.GL_TRUE; GL11.GL_FALSE]
                         */
                        onFocusChanged(focused);
                    }
                });
    }

    public void dispose() {
        windowSizeCallback.free();
        cursorPosCallback.free();
        keyCallback.free();
        mouseButtonCallback.free();
        scrollCallback.free();
        windowFocusCallback.free();
    }
}
