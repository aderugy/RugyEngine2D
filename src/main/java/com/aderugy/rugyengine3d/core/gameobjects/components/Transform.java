package com.aderugy.rugyengine3d.core.gameobjects.components;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class Transform {
    public static final Vector3f X_AXIS = new Vector3f(1, 0, 0);
    public static final Vector3f Y_AXIS = new Vector3f(0, 1, 0);
    public static final Vector3f Z_AXIS = new Vector3f(0, 0, 1);

    private final Matrix4f modelMatrix;

    public Transform() {
        this.modelMatrix = new Matrix4f();
    }

    public void scale(float factor) {
        modelMatrix.scale(factor);
    }

    public void scale(float factor, Vector3f axis) {
        float xFactor = axis == com.aderugy.rugyengine2d.geom.Transform.X_AXIS ? factor : 1;
        float yFactor = axis == com.aderugy.rugyengine2d.geom.Transform.Y_AXIS ? factor : 1;
        float zFactor = axis == com.aderugy.rugyengine2d.geom.Transform.Z_AXIS ? factor : 1;
        modelMatrix.scale(xFactor, yFactor, zFactor);
    }

    public void translate(Vector3f offset) {
        modelMatrix.translate(offset);
    }

    public void rotateDeg(float degrees, Vector3f axis) {
        modelMatrix.rotate(Math.toRadians(degrees), axis);
    }

    public void rotateRad(float radians, Vector3f axis) {
        modelMatrix.rotate(radians, axis);
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

    public FloatBuffer getProjection() {
        FloatBuffer projectionBuffer = BufferUtils.createFloatBuffer(16);
        modelMatrix.get(projectionBuffer);
        return projectionBuffer;
    }
}
