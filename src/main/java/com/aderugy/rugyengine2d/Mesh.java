package com.aderugy.rugyengine2d;

public class Mesh {
    private final int vaoID;
    private final int vertexSize;

    public Mesh(int vao, int vertexSize) {
        this.vaoID = vao;
        this.vertexSize = vertexSize;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVertexSize() {
        return vertexSize;
    }
}
