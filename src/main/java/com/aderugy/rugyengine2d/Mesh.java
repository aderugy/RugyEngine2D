package com.aderugy.rugyengine2d;

public class Mesh {
    private final int vaoID;
    private final int verticesCount;

    public Mesh(int vao, int vertexSize) {
        this.vaoID = vao;
        this.verticesCount = vertexSize;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getVerticesCount() {
        return verticesCount;
    }
}
