package com.aderugy.rugyengine3d.core;

import com.aderugy.rugyengine3d.core.gameobjects.Camera;
import com.aderugy.rugyengine3d.core.gameobjects.GameObject;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Scene {
    private Camera camera;
    private ArrayList<GameObject> gameObjects;

    public Scene() {
        this.gameObjects = new ArrayList<>();
        this.camera = new Camera();
    }

    public void drawComponents() {
        for (GameObject component : gameObjects) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            camera.loadMatrices(component.getShader().getShaderProgram());
            component.draw();
        }
    }


    public void addComponent(GameObject c) {
        gameObjects.add(c);
    }
}
