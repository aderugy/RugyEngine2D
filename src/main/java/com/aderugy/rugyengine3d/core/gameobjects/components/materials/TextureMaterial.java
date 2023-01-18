package com.aderugy.rugyengine3d.core.gameobjects.components.materials;

import com.aderugy.rugyengine3d.core.gameobjects.components.Material;
import com.aderugy.rugyengine3d.core.utils.ResourceManager;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class TextureMaterial extends Material {
    private final int textureID;
    private final String filename;

    public static final float[][] RECTANGLE = new float[][] {
            { 0, 0 },
            { 0, 1 },
            { 1, 1 },
            { 1, 0 }
    };
    public static final float[][] UNIFORMLY_TEXTURED_CUBE = new float[][] {
            { 0, 0 },
            { 1, 0 },
            { 1, 1 },
            { 1, 1 },
            { 0, 1 },
            { 0, 0 },

            { 0, 0 },
            { 1, 0 },
            { 1, 1 },
            { 1, 1 },
            { 0, 1 },
            { 0, 0 },

            { 1, 0 },
            { 1, 1 },
            { 0, 1 },
            { 0, 1 },
            { 0, 0 },
            { 1, 0 },

            { 1, 0 },
            { 1, 1 },
            { 0, 1 },
            { 0, 1 },
            { 0, 0 },
            { 1, 0 },

            { 0, 1 },
            { 1, 1 },
            { 1, 0 },
            { 1, 0 },
            { 0, 0 },
            { 0, 1 },

            { 0, 1 },
            { 1, 1 },
            { 1, 0 },
            { 1, 0 },
            { 0, 0 },
            { 0, 1 },
    };

    public TextureMaterial(String filename) {
        data = new float[][] {
                { 0, 0 },
                { 1, 0 },
                { 1, 1 },
                { 1, 1 },
                { 0, 1 },
                { 0, 0 }
        };

        this.textureID = glGenTextures();
        this.filename = filename;
    }

    public TextureMaterial(String filename, float[][] data) {
        this.data = data;

        this.textureID = glGenTextures();
        this.filename = filename;
    }

    @Override
    public void load() {
        bind();

        // Loading the texture data
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer textureData = stbi_load(
                ResourceManager.getImage(this.filename).getAbsolutePath(),
                width, height, channels, 0);

        // Storing texture in the GL_TEXTURE_2D buffer
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, textureData);
        glGenerateMipmap(GL_TEXTURE_2D);

        assert textureData != null;
        stbi_image_free(textureData);
    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }
}
