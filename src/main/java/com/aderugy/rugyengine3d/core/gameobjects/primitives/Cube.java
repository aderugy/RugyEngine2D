package com.aderugy.rugyengine3d.core.gameobjects.primitives;

import com.aderugy.rugyengine3d.core.gameobjects.GameObject;
import com.aderugy.rugyengine3d.core.gameobjects.components.Material;
import com.aderugy.rugyengine3d.core.gameobjects.fragmentdata.Vertex;
import com.aderugy.rugyengine3d.core.gameobjects.fragmentdata.VertexData;
import com.aderugy.rugyengine3d.core.gameobjects.shaders.Shader;

public class Cube extends GameObject {
    @Override
    protected void initIndices(){
        indices = new int[] {
                //Front
                0, 1, 3,
                1, 2, 3,

                //Back
                4, 5, 7,
                5, 6, 7,

                //Left
                0, 1, 5,
                0, 4, 5,

                //Right
                3, 2, 6,
                2, 3, 7,

                //Front
                0, 2, 3,
                0, 1, 3,

                //Back
                4, 6, 7,
                4, 5, 7
        };
    }
    public Cube(Shader shader, Vertex position, Material material) {
        super(shader, position, material);
    }

    protected void initVertexData(int size) {
         position = new Vertex(new VertexData(new float[][]{
                {0 , 0 , 0},
                {0 , size , 0},
                {size , size , 0},
                {size , 0 , 0},
                {0 , 0 , -size},
                {0 , size , -size},
                {size , size , -size},
                {size , 0 , -size}
        }));
    }

    @Override
    protected int getVertexCountUsingIndices() {
        return 36;
    }
}
