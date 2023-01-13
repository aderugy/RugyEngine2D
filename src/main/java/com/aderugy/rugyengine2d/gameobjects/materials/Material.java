package com.aderugy.rugyengine2d.gameobjects.materials;

import com.aderugy.rugyengine2d.shaders.ShaderProgram;

public abstract class Material {
    protected ShaderProgram shaderProgram;
    protected int materialVertexSize;
    protected float[] materialData;

    public Material(ShaderProgram shaderProgram, int materialVertexSize, int vertexCount) {
        this.shaderProgram = shaderProgram;
        this.materialVertexSize = materialVertexSize;
        this.materialData = new float[materialVertexSize * vertexCount];

        this.loadMaterialData();
    }

    protected abstract void loadMaterialData();

    public abstract void attribPointers(int strideOffset);

    public abstract void bind();

    public abstract void unbind();

    public ShaderProgram getShaderProgram() {
        return shaderProgram;
    }

    public int getMaterialVertexSize() {
        return materialVertexSize;
    }

    public float[] getMaterialData() {
        return materialData;
    }
}
