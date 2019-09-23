//package com.screendead.capital;
//
//import com.screendead.capital.graphics.Mesh;
//import com.screendead.capital.graphics.Shader;
//import org.joml.Matrix4f;
//import org.lwjgl.*;
//import org.lwjgl.glfw.*;
//import org.lwjgl.opengl.*;
//import org.lwjgl.system.*;
//
//import java.nio.*;
//import java.util.Objects;
//
//import static org.lwjgl.glfw.Callbacks.*;
//import static org.lwjgl.glfw.GLFW.*;
//import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.system.MemoryStack.*;
//import static org.lwjgl.system.MemoryUtil.*;
//
//public class HelloWorld {
//
//    // The window handle
//    private long window;
//
//    public void run() {
//        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
//
//        init();
//        loop();
//
//        // Free the window callbacks and destroy the window
//        glfwFreeCallbacks(window);
//        glfwDestroyWindow(window);
//
//        // Terminate GLFW and free the error callback
//        glfwTerminate();
//        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
//    }
//
//    private void init() {
//        // Setup an error callback. The default implementation
//        // will print the error message in System.err.
//        GLFWErrorCallback.createPrint(System.err).set();
//
//        // Initialize GLFW. Most GLFW functions will not work before doing this.
//        if ( !glfwInit() )
//            throw new IllegalStateException("Unable to initialize GLFW");
//
//        // Configure GLFW
//        glfwDefaultWindowHints(); // optional, the current window hints are already the default
//        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
//        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
//
//        // Create the window
//        window = glfwCreateWindow(720, 720, "Hello World!", NULL, NULL);
//        if ( window == NULL )
//            throw new RuntimeException("Failed to create the GLFW window");
//
//        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
//        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
//            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
//                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
//        });
//
//        // Get the thread stack and push a new frame
//        try ( MemoryStack stack = stackPush() ) {
//            IntBuffer pWidth = stack.mallocInt(1); // int*
//            IntBuffer pHeight = stack.mallocInt(1); // int*
//
//            // Get the window size passed to glfwCreateWindow
//            glfwGetWindowSize(window, pWidth, pHeight);
//
//            // Get the resolution of the primary monitor
//            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//
//            // Center the window
//            glfwSetWindowPos(
//                    window,
//                    (vidmode.width() - pWidth.get(0)) / 2,
//                    (vidmode.height() - pHeight.get(0)) / 2
//            );
//        } // the stack frame is popped automatically
//
//        // Make the OpenGL context current
//        glfwMakeContextCurrent(window);
//        // Enable v-sync
//        glfwSwapInterval(1);
//
//        // Make the window visible
//        glfwShowWindow(window);
//    }
//
//    private void loop() {
//        // This line is critical for LWJGL's interoperation with GLFW's
//        // OpenGL context, or any context that is managed externally.
//        // LWJGL detects the context that is current in the current thread,
//        // creates the GLCapabilities instance and makes the OpenGL
//        // bindings available for use.
//        GL.createCapabilities();
//
////        glMatrixMode(GL_PROJECTION);
////        glOrtho(-1, 1, 1, -1, -1, 1); // 2D projection matrix
////        glMatrixMode(GL_MODELVIEW);
//
//        //Enable blending so the red background can be seen through the texture
//        glEnable(GL_BLEND);
//        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//
//        glEnable(GL_TEXTURE_2D); // Enable texturing
//
//        Shader simple = new Shader("shaders/simple");
//        Texture texture = new Texture("images/grass.png");
//
//        simple.addUniform("projection");
//        simple.setUniform("projection", new Matrix4f().ortho(-1, 1, 1, -1, -1, 1));
//
//        simple.addUniform("tex");
//        simple.setUniform("tex", 0);
//
//        // Set the clear color
//        glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
//
//        Mesh m = new Mesh();
//
//        // Run the rendering loop until the user has attempted to close
//        // the window or has pressed the ESCAPE key.
//        while ( !glfwWindowShouldClose(window) ) {
//            // Poll for window events. The key callback above will only be
//            // invoked during this call.
//            glfwPollEvents();
//
//            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
//
//            glClear(GL_COLOR_BUFFER_BIT);
//
//            simple.bind();
//            texture.bind();
//
//            m.render();
//
//            Shader.unbind();
//            Texture.unbind();
//
//            glfwSwapBuffers(window); // swap the color buffers
//        }
//    }
//}