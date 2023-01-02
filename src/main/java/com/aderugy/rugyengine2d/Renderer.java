package com.aderugy.rugyengine2d;

import com.aderugy.rugyengine2d.shaders.ShaderManager;
import com.aderugy.rugyengine2d.utils.Log;
import com.aderugy.rugyengine2d.utils.Utils;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Renderer {
    public static final String VERTEX_SHADER_PATH = "C:\\Users\\Arthur\\Desktop\\Java\\Projects\\RugyEngine2D\\src\\main\\java\\com\\aderugy\\rugyengine2d\\shaders\\shader.vert";
    public static final String FRAGMENT_SHADER_PATH = "C:\\Users\\Arthur\\Desktop\\Java\\Projects\\RugyEngine2D\\src\\main\\java\\com\\aderugy\\rugyengine2d\\shaders\\shader.frag";

    private MeshLoader loader;

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        for (Mesh mesh :
                loader.getMeshes()) {
            glBindVertexArray(mesh.getVaoID());
            glDrawArrays(GL_TRIANGLES, 0, 3);
        }

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

        loader = new MeshLoader();

        Log.success(operation);
    }

    public MeshLoader getLoader() {
        return loader;
    }
}
