package com.aderugy.rugyengine3d.core.gameobjects;

import com.aderugy.rugyengine3d.core.gameobjects.components.Material;
import com.aderugy.rugyengine3d.core.gameobjects.components.Transform;
import com.aderugy.rugyengine3d.core.gameobjects.fragmentdata.Vertex;
import com.aderugy.rugyengine3d.core.gameobjects.shaders.Shader;
import com.aderugy.rugyengine3d.core.utils.Utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;

public abstract class GameObject {
    private static final String MODEL_UNIFORM_LOCATION = "model";

    private Vertex position;
    private Transform transform;
    private Material material;
    private Shader shader;

    protected int[] indices;
    protected int vaoID;
    protected int eboID;

    public GameObject(Shader shader, Vertex position, Material material) {
        this.shader = shader;
        this.position = position;
        this.transform = new Transform();
        this.material = material;

        this.vaoID = glGenVertexArrays();
        this.eboID = glGenBuffers();
    }

    public void draw() {
        glUseProgram(shader.getShaderProgram());
        glBindVertexArray(vaoID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);

        loadProjection();

        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);

        // Unbind everything
        glUseProgram(0);
        glBindVertexArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private int getVertexCount() {
        return position.getVertexCount();
    }

    public void render() {
        glUseProgram(shader.getShaderProgram());
        glBindVertexArray(vaoID);
        genVboID();

        bindIndices(indices);

        Vertex vertexData = Vertex.merge(position, material.toVertex(position.getVertexCount()));
        FloatBuffer bufferedData = vertexData.get();
        glBufferData(GL_ARRAY_BUFFER, bufferedData, GL_STATIC_DRAW);

        vertexData.attribPointers();

        // Unbinding VAO, VBO and EBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void loadProjection() {
        FloatBuffer transformBuffer = transform.getProjection();

        // Passing the transform matrix
        int transformLocation = glGetUniformLocation(shader.getShaderProgram(), MODEL_UNIFORM_LOCATION);
        glUniformMatrix4fv(transformLocation, false, transformBuffer);
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getEboID() {
        return eboID;
    }

    public Transform getTransform() {
        return transform;
    }

    public Material getMaterial() {
        return material;
    }

    protected void genVboID() {
        int vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
    }

    protected void bindIndices(int[] indices) {
        IntBuffer bufferedIndices = Utils.createIntBuffer(indices);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,  bufferedIndices, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public Shader getShader() {
        return shader;
    }
}
