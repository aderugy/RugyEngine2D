package com.aderugy.rugyengine2d.gameobjects;

public class Color {
    float r, g, b, a;

    public Color() {
        r = 0;
        g = 0;
        b = 0;
        a = 1;
    }
    public Color(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0f;
    }

    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    public float getA() {
        return a;
    }
}
