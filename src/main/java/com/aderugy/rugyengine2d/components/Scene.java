package com.aderugy.rugyengine2d.components;

import java.util.ArrayList;

public class Scene {
    private ArrayList<Component> visibleComponents;
    private ArrayList<Component> hiddenComponents;

    private static Scene instance = null;

    public void drawComponents() {
        for (Component component : visibleComponents) {
            component.draw();
        }
    }

    public void addComponent(Component c) {
        visibleComponents.add(c);
    }

    private void setVisible(int vaoID, boolean visibility) {
        Component removed = removeByVao(vaoID, !visibility);
        if (removed == null) return;

        (visibility ? visibleComponents : hiddenComponents).add(removed);
    }

    private Component removeByVao(int vaoID, boolean visibility) {
        ArrayList<Component> searchArea = visibility ? visibleComponents : hiddenComponents;

        for (Component c : searchArea) {
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
