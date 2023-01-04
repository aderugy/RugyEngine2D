package com.aderugy.rugyengine2d.components;

import com.aderugy.rugyengine2d.utils.Utils;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public abstract class Component {
    protected int vaoID;
    protected int eboID;

    protected Component() {
        vaoID = glGenVertexArrays();
        eboID = glGenBuffers();
    }

    public abstract void draw();

    protected abstract float[] genData(Position[] pos);
    protected abstract void render(Position[] data, int[] indices);

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

    public int getVaoID() {
        return vaoID;
    }

    public int getEboID() {
        return eboID;
    }
}
