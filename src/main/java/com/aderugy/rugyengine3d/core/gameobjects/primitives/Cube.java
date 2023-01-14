package com.aderugy.rugyengine3d.core.gameobjects.primitives;

import com.aderugy.rugyengine3d.core.gameobjects.GameObject;
import com.aderugy.rugyengine3d.core.gameobjects.components.Material;
import com.aderugy.rugyengine3d.core.gameobjects.fragmentdata.Vertex;
import com.aderugy.rugyengine3d.core.gameobjects.shaders.Shader;

public class Cube extends GameObject {
    public Cube(Shader shader, Vertex position, Material material) {
        super(shader, position, material);
    }
}
