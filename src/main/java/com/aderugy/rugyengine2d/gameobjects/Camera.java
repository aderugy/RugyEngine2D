package com.aderugy.rugyengine2d.gameobjects;

import com.aderugy.rugyengine2d.geom.Transform;
import org.joml.Matrix4f;
import org.joml.Math;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class Camera {
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;

    public Camera() {
        projectionMatrix = new Matrix4f().perspective(Math.toRadians(45), 800.0f / 600.0f, 0.1f, 100.0f);
        viewMatrix = new Matrix4f().translate(0, 0, -3f);
    }

    public void loadMatrices(int shaderProgramID) {
        int viewMatrixUniformLocation = glGetUniformLocation(shaderProgramID, "view");
        FloatBuffer viewMatrixBuffer = BufferUtils.createFloatBuffer(16);
        viewMatrix.get(viewMatrixBuffer);
        glUniformMatrix4fv(viewMatrixUniformLocation, false, viewMatrixBuffer);

        int projectionMatrixUniformLocation = glGetUniformLocation(shaderProgramID, "projection");
        FloatBuffer projectionMatrixBuffer = BufferUtils.createFloatBuffer(16);
        projectionMatrix.get(projectionMatrixBuffer);
        glUniformMatrix4fv(projectionMatrixUniformLocation, false, projectionMatrixBuffer);
    }
}
