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

    public void drawComponents() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for (GameObject component : gameObjects) {
            camera.loadMatrices(component.getShader().getShaderProgram());
            component.draw();
        }
    }


    public void addComponent(GameObject c) {
        gameObjects.add(c);
    }

    public Camera getCamera() {
        return this.camera;
    }
}
