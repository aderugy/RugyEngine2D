package com.aderugy.rugyengine2d;

import com.aderugy.rugyengine2d.utils.Utils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class MeshLoader {
    ArrayList<Mesh> meshes;

    public MeshLoader() {
        meshes = new ArrayList<>();
    }

    public Mesh createMesh(float[] vertices, int size) {
        int vao = genVAO();
        storeData(size, vertices);
        glBindVertexArray(0);

        Mesh mesh = new Mesh(vao, size);
        meshes.add(mesh);
        return mesh;
    }

    private void storeData(int size, float[] data) {
        // Generate the VBO
        genVBO();

        // Store the data in the buffer
        FloatBuffer buffer = Utils.createFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, size, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, size, GL_FLOAT, false, 6 * Float.BYTES, 12);
        glEnableVertexAttribArray(1);

        // Unbind the buffer
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    /**
     * Creates and binds a Vertex Buffer Object.
     * @return ID of the generated VBO
     */
    private int genVBO() {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        return vbo;
    }

    /**
     * Creates and binds a Vertex Array Object.
     * @return ID of the generated VAO
     */
    private int genVAO() {
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
        return vao;
    }

    public ArrayList<Mesh> getMeshes() {
        return meshes;
    }
}
