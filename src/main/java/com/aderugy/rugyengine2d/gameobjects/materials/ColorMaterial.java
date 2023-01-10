package com.aderugy.rugyengine2d.gameobjects.materials;

import com.aderugy.rugyengine2d.shaders.ShaderProgram;

public class ColorMaterial extends Material {
    private ColorMaterial color;

    public ColorMaterial(ShaderProgram shaderProgram, int materialVertexSize, int vertexCount) {
        super(shaderProgram, materialVertexSize, vertexCount);
    }

    @Override
    protected void loadMaterialData() {

    }

    @Override
    public void attribPointers(int strideOffset) {

    }

    @Override
    public void bind() {

    }

    @Override
    public void unbind() {

    }
}
