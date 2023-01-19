package com.aderugy.rugyengine3d.core;

import com.aderugy.rugyengine3d.core.gameobjects.Camera;
import com.aderugy.rugyengine3d.core.log.Log;
import org.joml.Math;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class InputHandler {
    private static InputHandler instance = null;
    private final static float SENSITIVITY = 0.05f;

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


    private long window;
    private HashMap<Integer, Runnable> onKeyPressedEventListeners = new HashMap<>();

    private double mousePosX;
    private double mousePosY;
    private double yaw;
    private double pitch;

    private InputHandler(long window) {
        this.window = window;

        mousePosX = 400;
        mousePosY = 300;

        yaw = 0;
        pitch = 0;
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

    public void setMouseInput(Camera camera, float sensitivity) {
        glfwSetCursorPosCallback(window, (long window, double posX, double posY) -> {
            System.out.println(posX + " " + posY);
            double dx = posX - mousePosX;
            double dy = posY - mousePosY;
            mousePosX = posX;
            mousePosY = posY;
            dx *= sensitivity;
            dy *= sensitivity;

            yaw += dx;
            pitch += dy;

            if(pitch > 89.0f)
                pitch = 89.0f;
            if(pitch < -89.0f)
                pitch = -89.0f;

            double yawRad = Math.toRadians(yaw);
            double pitchRad = Math.toRadians(pitch);

            double yawCos = Math.cos(yawRad);
            double yawSin = Math.sin(yawRad);
            double pitchCos = Math.cos(pitchRad);
            double pitchSin = Math.sin(pitchRad);

            camera.setCameraFront(new Vector3f(
                    (float) (yawCos * pitchCos),
                    (float) -pitchSin,
                    (float) (yawSin * pitchCos)
            ).normalize());
        });
    }

    public void setCursorVisibility(int visibility) {
        glfwSetInputMode(window, GLFW_CURSOR, visibility);
    }
}
