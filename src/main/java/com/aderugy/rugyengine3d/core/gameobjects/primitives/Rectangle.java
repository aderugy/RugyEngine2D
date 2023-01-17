package com.aderugy.rugyengine3d.core.gameobjects.primitives;

import com.aderugy.rugyengine3d.core.gameobjects.GameObject;
import com.aderugy.rugyengine3d.core.gameobjects.components.Material;
import com.aderugy.rugyengine3d.core.gameobjects.fragmentdata.Vertex;
import com.aderugy.rugyengine3d.core.gameobjects.shaders.Shader;

public class Rectangle extends GameObject {
    public Rectangle(Shader shader, Vertex position, Material material) {
        super(shader, position, material);
    }

    @Override
    protected void initIndices(){
        indices = new int[] {0, 1, 2, 1, 2, 3};
    }

    @Override
    protected int getVertexCountUsingIndices() {
        return 6;
    }
}
