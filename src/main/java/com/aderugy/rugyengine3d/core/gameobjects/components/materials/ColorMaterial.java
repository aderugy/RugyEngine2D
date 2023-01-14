package com.aderugy.rugyengine3d.core.gameobjects.components.materials;

import com.aderugy.rugyengine3d.core.gameobjects.components.Material;

import java.awt.*;

public class ColorMaterial extends Material {
    public ColorMaterial(Color color) {

        this.data = new float[][] {
                {
                        color.getRed() / 255.0f,
                        color.getBlue() / 255.0f,
                        color.getGreen() / 255.0f
                }
        };
    }
}
