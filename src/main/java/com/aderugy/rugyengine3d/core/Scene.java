package com.aderugy.rugyengine3d.core;

import com.aderugy.rugyengine3d.core.gameobjects.Camera;
import com.aderugy.rugyengine3d.core.gameobjects.GameObject;
import com.aderugy.rugyengine3d.core.gameobjects.components.Transform;
import com.aderugy.rugyengine3d.core.log.Log;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Scene {
    private Camera camera;
    private ArrayList<GameObject> gameObjects;

    public Scene() {
        this.gameObjects = new ArrayList<>();
        this.camera = new Camera();
    }

    public void drawComponents(long window) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            camera.translate(0.1f, Transform.X_AXIS);
            System.out.println("EXEC");
        }
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
            camera.translate(-0.1f, Transform.X_AXIS);

        for (GameObject component : gameObjects) {
            if (glfwGetKey(window, GLFW_KEY_J) == GLFW_PRESS)
                component.getTransform().rotateDeg(1f, Transform.X_AXIS);
            if (glfwGetKey(window, GLFW_KEY_K) == GLFW_PRESS)
                component.getTransform().rotateDeg(1f, Transform.Y_AXIS);
            if (glfwGetKey(window, GLFW_KEY_L) == GLFW_PRESS)
                component.getTransform().rotateDeg(1f, Transform.Z_AXIS);

            camera.loadMatrices(component.getShader().getShaderProgram());
            component.draw();
        }
    }


    public void addComponent(GameObject c) {
        gameObjects.add(c);
    }
}
