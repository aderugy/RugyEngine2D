package com.aderugy.rugyengine2d;

import com.aderugy.rugyengine2d.gameobjects.Camera;
import com.aderugy.rugyengine2d.gameobjects.GameObject;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Scene {
    private ArrayList<GameObject> gameObjects;
    private Camera camera;

    private static Scene instance = null;

    public void drawComponents() {
        for (GameObject component : gameObjects) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            camera.loadMatrices(component.getMaterial().getShaderProgram().getShaderProgramID());
            component.draw();
        }
    }

    public void addComponent(GameObject c) {
        gameObjects.add(c);
    }

    private GameObject removeByVao(int vaoID, boolean visibility) {
        for (GameObject c : gameObjects) {
            if (c.getVaoID() == vaoID) gameObjects.remove(c);
            return c;
        }

        return null;
    }

    private Scene() {
        gameObjects = new ArrayList<>();
        camera = new Camera();
    }

    public static Scene getInstance() {
        if (instance == null) instance = new Scene();

        return instance;
    }
}
