package com.aderugy.rugyengine2d.geom;

import org.joml.*;
import org.joml.Math;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;


public class Transform {
    public static final Vector3f X_AXIS = new Vector3f(1, 0, 0);
    public static final Vector3f Y_AXIS = new Vector3f(0, 1, 0);
    public static final Vector3f Z_AXIS = new Vector3f(0, 0, 1);

    private Matrix4f modelMatrix;
    private Position coordinates;
    private Dimension dimension;

    public Transform(float width, float height) {
        this.coordinates = new Position();
        this.dimension = new Dimension(width, height, 0);
        this.modelMatrix = new Matrix4f();
    }

    public Transform(float width, float height, float length) {
        this.coordinates = new Position();
        this.dimension = new Dimension(width, height, length);
        this.modelMatrix = new Matrix4f();
    }

    public Transform(Dimension dimension) {
        this.coordinates = new Position();
        this.dimension = dimension;
        this.modelMatrix = new Matrix4f();
    }

    public Transform(Position coordinates, Dimension dimension) {
        this.coordinates = coordinates;
        this.dimension = dimension;
        modelMatrix = new Matrix4f();
    }

    public void scale(float factor) {
        modelMatrix.scale(factor);
    }

    public void scale(float factor, Vector3f axis) {
        float xFactor = axis == Transform.X_AXIS ? factor : 1;
        float yFactor = axis == Transform.Y_AXIS ? factor : 1;
        float zFactor = axis == Transform.Z_AXIS ? factor : 1;
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

    public Position getCoordinates() {
        return coordinates;
    }

    public Dimension getDimension() {
        return dimension;
    }
}
