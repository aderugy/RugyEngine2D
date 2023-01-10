package com.aderugy.rugyengine2d.gameobjects.primitives;

import com.aderugy.rugyengine2d.gameobjects.GameObject;
import com.aderugy.rugyengine2d.gameobjects.materials.Material;
import com.aderugy.rugyengine2d.geom.Position;
import com.aderugy.rugyengine2d.geom.Transform;
import com.aderugy.rugyengine2d.utils.Utils;
import org.joml.Vector3f;

import java.nio.FloatBuffer;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;

public class Cube extends GameObject {

    public Cube(Material material) {
        super(material);

        transform = new Transform(1, 1);
        render(null, null);
    }

    @Override
    public void draw() {
        glUseProgram(material.getShaderProgram().getShaderProgramID());
        material.bind();
        glBindVertexArray(vaoID);
        glEnable(GL_DEPTH_TEST);

        transform.rotateRad((float) (0.001 * glfwGetTime() * Math.toRadians(50.0f)), new Vector3f(0.5f, 0.8f, 0));
        loadProjection();

        glDrawArrays(GL_TRIANGLES, 0, 36);
    }

    @Override
    protected float[] genData(Position[] pos) {
        float[] vertices = new float[]{
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,

                0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,

                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,

                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,

                -0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,

                -0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,

                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,

                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,

                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,

                0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,

                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, 0.5f,

                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f
        };

        float[] materialData = material.getMaterialData();

        float[] data = new float[materialData.length + vertices.length];
        for (int i = 0; i < vertices.length / 3; i++) {
            data[5 * i] = vertices[3 * i];
            data[5 * i + 1] = vertices[3 * i + 1];
            data[5 * i + 2] = vertices[3 * i + 2];
            data[5 * i + 3] = materialData[2 * i];
            data[5 * i + 4] = materialData[2 * i + 1];
        }

        return data;
    }

    @Override
    protected void render(Position[] data, int[] indices) {
        glUseProgram(material.getShaderProgram().getShaderProgramID());

        // Binding VAO and VBO
        glBindVertexArray(vaoID);
        genVboID();

        // Parsing vertices to a FloatBuffer
        float[] vertices = genData(null);
        FloatBuffer bufferedData = Utils.createFloatBuffer(vertices);

        // Storing the vertex data in the VBO
        glBufferData(GL_ARRAY_BUFFER, bufferedData, GL_STATIC_DRAW);

        // Passing the vertex coordinates to the shader
        glVertexAttribPointer(0, 3,  GL_FLOAT, false, 5 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        // Passing the material data to the shader
        material.attribPointers(3);

        // Unbinding VAO, VBO and EBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
}
