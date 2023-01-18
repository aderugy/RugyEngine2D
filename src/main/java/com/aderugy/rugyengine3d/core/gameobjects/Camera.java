package com.aderugy.rugyengine3d.core.gameobjects;

import com.aderugy.rugyengine3d.core.gameobjects.components.Transform;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Camera {
    private static final String VIEW_MATRIX_UNIFORM_LOCATION = "view";
    private static final String PROJECTION_MATRIX_UNIFORM_LOCATION = "projection";

    private Transform projection;
    private Transform view;

    public Camera() {
        projection = new Transform(new Matrix4f().perspective(Math.toRadians(45), 800.0f / 600.0f, 0.1f, 100.0f));
        view = new Transform(new Matrix4f().translate(0, 0, -3f));
    }

    public void loadMatrices(int shaderProgramID) {
        glUseProgram(shaderProgramID);

        int viewMatrixUniformLocation = glGetUniformLocation(shaderProgramID, VIEW_MATRIX_UNIFORM_LOCATION);
        FloatBuffer viewMatrixBuffer = BufferUtils.createFloatBuffer(16);
        view.getTransformMatrix().get(viewMatrixBuffer);
        glUniformMatrix4fv(viewMatrixUniformLocation, false, viewMatrixBuffer);

        int projectionMatrixUniformLocation = glGetUniformLocation(shaderProgramID, PROJECTION_MATRIX_UNIFORM_LOCATION);
        FloatBuffer projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
        projection.getTransformMatrix().get(projectionMatrixBuffer);
        glUniformMatrix4fv(projectionMatrixUniformLocation, false, projectionMatrixBuffer);

        glUseProgram(0);
    }

    public void translate(float distance, Vector3f axis) {
        view.translate(axis.mul(distance));
    }

    public void rotateDeg(float deg, Vector3f axis) {
        projection.rotateDeg(deg, axis);
    }
}
