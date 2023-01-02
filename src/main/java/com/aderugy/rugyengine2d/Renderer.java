package com.aderugy.rugyengine2d;

import com.aderugy.rugyengine2d.shaders.ShaderManager;
import com.aderugy.rugyengine2d.utils.Log;
import com.aderugy.rugyengine2d.utils.Utils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static com.aderugy.rugyengine2d.RugyEngine2D.FRAGMENT_SHADER_PATH;
import static com.aderugy.rugyengine2d.RugyEngine2D.VERTEX_SHADER_PATH;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Renderer {
    private long window;
    private int program;

    private MeshLoader loader;

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        glUseProgram(program);
        for (Mesh mesh :
                loader.getMeshes()) {
            glBindVertexArray(mesh.getVaoID());
            glDrawArrays(GL_TRIANGLES, 0, 3);
        }

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
        this.program = shaderManager.link(new int[]{vert, frag});

        // Using the program
        glUseProgram(program);
        glDeleteShader(vert);
        glDeleteShader(frag);

        loader = new MeshLoader();

        Log.success(operation);
    }

    public void setWindow(long window) {
        this.window = window;
    }

    public MeshLoader getLoader() {
        return loader;
    }
}
