package com.aderugy.rugyengine3d.core;

import com.aderugy.rugyengine3d.core.log.Log;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

public class InputHandler {
    private long window;
    private HashMap<Integer, Runnable> onKeyPressedEventListeners = new HashMap<>();

    private static InputHandler instance = null;

    public static InputHandler getInstance() {
        if (instance == null) {
            Log.error("Access violation: InputHandler has not been initialized.");
            throw new IllegalStateException("InputHandler has not been initialized.");
        }

        return instance;
    }

    public static void init(long window) {
        if (instance == null) {
            instance = new InputHandler(window);
        }
    }

    private InputHandler(long window) {
        this.window = window;
    }

    public void addOnKeyPressedEventListener(int key, Runnable event) {
        onKeyPressedEventListeners.put(key, event);
    }

    public void processOnKeyPressedEvents() {
        for (Map.Entry<Integer, Runnable> entry : onKeyPressedEventListeners.entrySet()) {
            if (isKeyPressed(entry.getKey())) {
                entry.getValue().run();
            }
        }
    }

    public boolean isKeyPressed(int key) {
        return glfwGetKey(window, key) == GLFW_PRESS;
    }
}
