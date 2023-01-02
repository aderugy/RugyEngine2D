package com.aderugy.rugyengine2d;

import com.aderugy.rugyengine2d.shaders.ShaderManager;
import com.aderugy.rugyengine2d.utils.Log;
import com.aderugy.rugyengine2d.utils.Utils;
import org.lwjgl.opengl.GL;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static com.aderugy.rugyengine2d.RugyEngine2D.FRAGMENT_SHADER_PATH;
import static com.aderugy.rugyengine2d.RugyEngine2D.VERTEX_SHADER_PATH;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Renderer {
    private long window;
    private ArrayList<Integer> vaos = new ArrayList<>();
    private ArrayList<Integer> vbos = new ArrayList<>();

    public void render() {
        float[] triangle = {
                 0,  1, 0,
                -1, -1, 0,
                 1, -1, 0
        };

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        glfwSwapBuffers(window); // swap the color buffers

        // Preparing next images rendering
    }

    public void initRenderer() {
        String operation = "Initializing the renderer";
        Log.log(operation);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        // Compiling the shaders
        ShaderManager shaderManager = new ShaderManager();
        int vert = shaderManager.compile(VERTEX_SHADER_PATH, GL_VERTEX_SHADER);
        int frag = shaderManager.compile(FRAGMENT_SHADER_PATH, GL_FRAGMENT_SHADER);

        // Linking the shaders
        int program = shaderManager.link(new int[]{vert, frag});

        // Using the program
        glUseProgram(program);
        glDeleteShader(vert);
        glDeleteShader(frag);

        Log.success(operation);
    }

    public void render(float[] data) {
        FloatBuffer vertices = Utils.createFloatBuffer(data);
        int vbo = glGenBuffers();
        vbos.add(vbo);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
    }

    public void setWindow(long window) {
        this.window = window;
    }
}
