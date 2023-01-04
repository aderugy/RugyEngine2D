package com.aderugy.rugyengine2d.components;

public class Position {
    private float x, y, z;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Position(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
