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

    private Vector3f cameraPosition;
    private Vector3f cameraFront;
    private Vector3f cameraUp;

    public Camera() {
        projection = new Transform(new Matrix4f().perspective(Math.toRadians(45), 800.0f / 600.0f, 0.1f, 100.0f));

        cameraPosition = new Vector3f(0, 0, 3);
        cameraFront= new Vector3f(0, 0, -1);
        cameraUp = new Vector3f(0, 1, 0);
    }

    public void loadMatrices(int shaderProgramID) {
        glUseProgram(shaderProgramID);

        Vector3f target = new Vector3f();
        cameraPosition.add(cameraFront, target);
        Matrix4f view = new Matrix4f().lookAt(
                cameraPosition,
                target,
                cameraUp
        );

        int viewMatrixUniformLocation = glGetUniformLocation(shaderProgramID, VIEW_MATRIX_UNIFORM_LOCATION);
        FloatBuffer viewMatrixBuffer = BufferUtils.createFloatBuffer(16);
        view.get(viewMatrixBuffer);
        glUniformMatrix4fv(viewMatrixUniformLocation, false, viewMatrixBuffer);

        int projectionMatrixUniformLocation = glGetUniformLocation(shaderProgramID, PROJECTION_MATRIX_UNIFORM_LOCATION);
        FloatBuffer projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
        projection.getTransformMatrix().get(projectionMatrixBuffer);
        glUniformMatrix4fv(projectionMatrixUniformLocation, false, projectionMatrixBuffer);

        glUseProgram(0);
    }

    public Vector3f getCameraPosition() {
        return cameraPosition;
    }

    public Vector3f getCameraFront() {
        return cameraFront;
    }

    public Vector3f getCameraUp() {
        return cameraUp;
    }

    public void setCameraFront(Vector3f cameraFront) {
        this.cameraFront = cameraFront;
    }
}
