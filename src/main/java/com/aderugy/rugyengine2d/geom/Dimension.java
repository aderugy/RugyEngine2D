package com.aderugy.rugyengine2d.geom;

public class Dimension {
    private float width, height, length;

    public Dimension(float width, float height, float length) {
        this.width = width;
        this.height = height;
        this.length = length;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getLength() {
        return length;
    }
}
