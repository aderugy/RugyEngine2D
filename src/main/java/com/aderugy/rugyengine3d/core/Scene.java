package com.aderugy.rugyengine3d.core;

import com.aderugy.rugyengine3d.core.gameobjects.Camera;
import com.aderugy.rugyengine3d.core.gameobjects.GameObject;
import com.aderugy.rugyengine3d.core.gameobjects.components.Transform;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Scene {
    private Camera camera;
    private ArrayList<GameObject> gameObjects;
    private static final float SPEED = 0.1f;

    public Scene() {
        this.gameObjects = new ArrayList<>();
        this.camera = new Camera();
    }

    public void drawComponents(long window) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Vector3f result = new Vector3f();
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            camera.getCameraFront().mul(SPEED, result);
            camera.getCameraPosition().add(result);
        }
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            camera.getCameraFront().mul(SPEED, result);
            camera.getCameraPosition().sub(result);
        }
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            camera.getCameraFront().cross(camera.getCameraUp(), result);
            result.normalize();
            result.mul(SPEED);
            camera.getCameraPosition().sub(result);
        }
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            camera.getCameraFront().cross(camera.getCameraUp(), result);
            result.normalize();
            result.mul(SPEED);
            camera.getCameraPosition().add(result);
        }
        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            camera.getCameraUp().mul(SPEED, result);
            camera.getCameraPosition().add(result);
        }
        if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            camera.getCameraUp().mul(SPEED, result);
            camera.getCameraPosition().sub(result);
        }

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
