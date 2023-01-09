package com.aderugy.rugyengine2d.geom;

import org.joml.*;
import org.joml.Math;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;


public class Transform {
    public static final Vector3f X_AXIS = new Vector3f(1, 0, 0);
    public static final Vector3f Y_AXIS = new Vector3f(0, 1, 0);
    public static final Vector3f Z_AXIS = new Vector3f(0, 0, 1);

    private Matrix4f transformMatrix;
    private Position coordinates;
    private Dimension dimension;

    public Transform(float width, float height) {
        this.coordinates = new Position();
        this.dimension = new Dimension(width, height, 0);
        this.transformMatrix = new Matrix4f();
    }

    public Transform(float width, float height, float length) {
        this.coordinates = new Position();
        this.dimension = new Dimension(width, height, length);
        this.transformMatrix = new Matrix4f();
    }

    public Transform(Dimension dimension) {
        this.coordinates = new Position();
        this.dimension = dimension;
        this.transformMatrix = new Matrix4f();
    }

    public Transform(Position coordinates, Dimension dimension) {
        this.coordinates = coordinates;
        this.dimension = dimension;
        transformMatrix = new Matrix4f();
    }

    public void scale(float factor) {
        transformMatrix.scale(factor);
    }

    public void scale(float factor, Vector3f axis) {
        transformMatrix.scale(axis.x * factor, axis.y * factor, axis.z * factor);
    }

    public void rotateDeg(float degrees, Vector3f axis) {
        transformMatrix.rotate(Math.toRadians(degrees), axis);
    }

    public void rotateRad(float radians, Vector3f axis) {
        transformMatrix.rotate(radians, axis);
    }

    public Matrix4f getTransformMatrix() {
        return transformMatrix;
    }

    public FloatBuffer getProjection() {
        FloatBuffer projectionBuffer = BufferUtils.createFloatBuffer(16);
        transformMatrix.get(projectionBuffer);
        return projectionBuffer;
    }

    public Position getCoordinates() {
        return coordinates;
    }

    public Dimension getDimension() {
        return dimension;
    }
}
