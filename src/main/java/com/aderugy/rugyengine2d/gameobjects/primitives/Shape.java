package com.aderugy.rugyengine2d.gameobjects.primitives;

import com.aderugy.rugyengine2d.gameobjects.Color;
import com.aderugy.rugyengine2d.gameobjects.GameObject;
import com.aderugy.rugyengine2d.gameobjects.materials.Material;
import com.aderugy.rugyengine2d.gameobjects.vertices.Vertex;
import com.aderugy.rugyengine2d.gameobjects.vertices.VertexData;
import com.aderugy.rugyengine2d.geom.Position;
import com.aderugy.rugyengine2d.shaders.ShaderProgram;
import com.aderugy.rugyengine2d.utils.Utils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;

public abstract class Shape extends GameObject {
    protected Color color;
    protected Shape(Material material) {
        super(material);
    }

    @Override
    protected float[] genData(Position[] pos) {
        return null;
    }

    @Override
    protected void render(Position[] pos, int[] indices) {
        float[] data = genData(pos);
        glBindVertexArray(vaoID);
        genVboID();

        FloatBuffer bufferedData = Utils.createFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, bufferedData, GL_STATIC_DRAW);

        bindIndices(indices);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 7 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 4, GL_FLOAT, false, 7 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);
    }
}
