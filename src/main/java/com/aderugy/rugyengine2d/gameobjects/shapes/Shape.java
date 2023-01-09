package com.aderugy.rugyengine2d.gameobjects.shapes;

import com.aderugy.rugyengine2d.gameobjects.Color;
import com.aderugy.rugyengine2d.gameobjects.GameObject;
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
    protected Shape(ShaderProgram shaderProgram) {
        super(shaderProgram);
        this.color = new Color();
    }

    @Override
    protected float[] genData(Position[] pos) {
        float[] data = new float[pos.length * 7];

        for (int j = 0; j < pos.length; j++) {
            int i = 7 * j;

            data[i]     = pos[j].getX();
            data[i + 1] = pos[j].getY();
            data[i + 2] = 0;
            data[i + 3] = color.getR();
            data[i + 4] = color.getG();
            data[i + 5] = color.getB();
            data[i + 6] = color.getA();
        }

        return data;
    }

    @Override
    protected void render(Position[] pos, int[] indices) {
        float[] data = genData(pos);
        glBindVertexArray(vaoID);
        genVboID();

        FloatBuffer bufferedData = Utils.createFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, bufferedData, GL_STATIC_DRAW);

        bindIndices(indices);

        glVertexAttribPointer(0, 3,  GL_FLOAT, false, 7 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 4,  GL_FLOAT, false, 7 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);
    }

    protected Shape(ShaderProgram shaderProgram, Color color) {
        super(shaderProgram);
        this.color = color;
    }
}
