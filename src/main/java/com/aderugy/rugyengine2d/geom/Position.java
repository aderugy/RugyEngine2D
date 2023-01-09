package com.aderugy.rugyengine2d.geom;

import org.joml.Vector3f;

public class Position {
    private float x, y, z;

    public Position() {
        x = 0;
        y = 0;
        z = 0;
    }

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

    public Position add(float value, Vector3f axis) {
        return new Position(value * axis.x, value * axis.y, value * axis.z);
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
