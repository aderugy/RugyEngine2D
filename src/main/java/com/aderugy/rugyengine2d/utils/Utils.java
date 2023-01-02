package com.aderugy.rugyengine2d.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Utils {
    public static IntBuffer createIntBuffer(int[] data) {
        return IntBuffer.wrap(data).flip();
    }

    public static FloatBuffer createFloatBuffer(float[] data) {
        return FloatBuffer.wrap(data).flip();
    }
}
