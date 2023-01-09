package com.aderugy.rugyengine2d;

import com.aderugy.rugyengine2d.gameobjects.GameObject;
import com.aderugy.rugyengine2d.geom.Transform;
import org.joml.Vector3f;

import java.util.ArrayList;

public class Scene {
    private ArrayList<GameObject> visibleComponents;
    private ArrayList<GameObject> hiddenComponents;

    private static Scene instance = null;

    public void drawComponents() {
        for (GameObject component : visibleComponents) {
            component.draw();
        }
    }

    public void addComponent(GameObject c) {
        visibleComponents.add(c);
    }

    private void setVisible(int vaoID, boolean visibility) {
        GameObject removed = removeByVao(vaoID, !visibility);
        if (removed == null) return;

        (visibility ? visibleComponents : hiddenComponents).add(removed);
    }

    private GameObject removeByVao(int vaoID, boolean visibility) {
        ArrayList<GameObject> searchArea = visibility ? visibleComponents : hiddenComponents;

        for (GameObject c : searchArea) {
            if (c.getVaoID() == vaoID) searchArea.remove(c);
            return c;
        }

        return null;
    }

    private Scene() {
        visibleComponents = new ArrayList<>();
        hiddenComponents = new ArrayList<>();
    }

    public static Scene getInstance() {
        if (instance == null) instance = new Scene();

        return instance;
    }
}
