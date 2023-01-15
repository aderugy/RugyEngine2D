package com.aderugy.rugyengine3d.core.gameobjects.primitives;

import com.aderugy.rugyengine3d.core.gameobjects.GameObject;
import com.aderugy.rugyengine3d.core.gameobjects.components.Material;
import com.aderugy.rugyengine3d.core.gameobjects.fragmentdata.Vertex;
import com.aderugy.rugyengine3d.core.gameobjects.shaders.Shader;

public class Triangle extends GameObject {
    {
        indices = new int[] {0, 1, 2};
    }
    public Triangle(Shader shader, Vertex position, Material material) {
        super(shader, position, material, 3);

        render();
    }
}
