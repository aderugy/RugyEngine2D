package com.aderugy.rugyengine2d.gameobjects.vertices;

public class VertexData {
    private int length;
    private float[] data;

    public VertexData(float... elements) {
        data = elements;
        length = data.length;
    }

    public int getLength() {
        return length;
    }

    public float[] getData() {
        return data;
    }
}
