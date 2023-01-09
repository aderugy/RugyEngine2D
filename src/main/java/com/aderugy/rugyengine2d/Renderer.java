package com.aderugy.rugyengine2d;

import com.aderugy.rugyengine2d.gameobjects.shapes.Rectangle;
import com.aderugy.rugyengine2d.geom.Position;
import com.aderugy.rugyengine2d.gameobjects.images.Texture;
import com.aderugy.rugyengine2d.shaders.ShaderProgram;
import com.aderugy.rugyengine2d.utils.Log;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL15.*;

public class Renderer {
    //private MeshLoader loader;

    /**
     * Called in the loop that renders the window.
     * Responsible for the rendering of the next frame.
     */
    public void render() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        Scene.getInstance().drawComponents();

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

        Scene.getInstance().addComponent(new Texture(
                new ShaderProgram("texture"),
                "sprite.png",
                new Position(0.5f, 0.5f),
                new Position(0.5f, -0.5f),
                new Position(-0.5f, -0.5f),
                new Position(-0.5f, 0.5f)));

        Log.success(operation);
    }
}
