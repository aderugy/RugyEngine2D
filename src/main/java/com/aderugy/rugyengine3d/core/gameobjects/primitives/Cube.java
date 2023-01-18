package com.aderugy.rugyengine3d.core.gameobjects.primitives;

import com.aderugy.rugyengine3d.core.gameobjects.GameObject;
import com.aderugy.rugyengine3d.core.gameobjects.components.Material;
import com.aderugy.rugyengine3d.core.gameobjects.fragmentdata.Vertex;
import com.aderugy.rugyengine3d.core.gameobjects.fragmentdata.VertexData;
import com.aderugy.rugyengine3d.core.gameobjects.shaders.Shader;

public class Cube extends GameObject {
    @Override
    protected void initIndices(){
        indices = new int[36];

        for (int i = 0; i < 36; i++) {
            indices[i] = i;
        }
    }
    protected Cube(Shader shader, Vertex position, Material material) {
        super(shader, position, material);
    }

    @Override
    protected int getVertexCountUsingIndices() {
        return 36;
    }
}
