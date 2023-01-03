package com.aderugy.rugyengine2d;

import com.aderugy.rugyengine2d.shaders.ShaderManager;
import com.aderugy.rugyengine2d.utils.Log;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Renderer {
    private MeshLoader loader;
    private int program;

    /**
     * Called in the loop that renders the window.
     * Responsible for the rendering of the next frame.
     */
    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        // Rendering all the meshes
        for (Mesh mesh : loader.getMeshes()) {
            glBindVertexArray(mesh.getVaoID());
            glDrawArrays(GL_TRIANGLES, 0, mesh.getVerticesCount());
        }

        // Preparing next images rendering
    }

    /**
     * Called before starting rendering the window.
     * Shader compilation and linking, using program and deleting the shaders in the memory.
     */
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
        int vert = shaderManager.compile(ResourceManager.getShader("shader.vert"), GL_VERTEX_SHADER);
        int frag = shaderManager.compile(ResourceManager.getShader("shader.frag"), GL_FRAGMENT_SHADER);

        // Linking the shaders
        program = shaderManager.link(new int[]{vert, frag});

        // Using the program
        glUseProgram(program);

        // Deleting shaders (not useful anymore)
        glDeleteShader(vert);
        glDeleteShader(frag);

        loader = new MeshLoader();

        Log.success(operation);
    }

    public MeshLoader getLoader() {
        return loader;
    }
}
