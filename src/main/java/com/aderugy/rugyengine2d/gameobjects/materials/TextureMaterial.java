package com.aderugy.rugyengine2d.gameobjects.materials;

import com.aderugy.rugyengine2d.ResourceManager;
import com.aderugy.rugyengine2d.geom.Transform;
import com.aderugy.rugyengine2d.shaders.ShaderProgram;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class TextureMaterial extends Material {
    private final String filename;
    private final int textureID;

    public TextureMaterial(String filename, ShaderProgram shaderProgram, int materialVertexSize, int vertexCount) {
        super(shaderProgram, materialVertexSize, vertexCount);

        this.filename = filename;
        this.textureID = glGenTextures();
    }

    @Override
    protected void loadMaterialData() {
        float[] texCoords = new float[] {
                0, 0,
                1, 0,
                1, 1,
                1, 1,
                0, 1,
                0, 0
        };

        for (int i = 0; i < materialData.length; i++) {
            materialData[i] = texCoords[i % texCoords.length];
        }
    }

    @Override
    public void attribPointers(int strideOffset) {
        // Loading the texture data
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer textureData = stbi_load(
                ResourceManager.getImage(this.filename).getAbsolutePath(),
                width, height, channels, 0);

        // Binding the texture, storing it in the GL_TEXTURE_2D buffer
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, textureData);
        glGenerateMipmap(GL_TEXTURE_2D);

        // Passing the texture data to the shader
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 5 * Float.BYTES, (long) strideOffset * Float.BYTES);
        glEnableVertexAttribArray(1);

        // Free memory from input stream
        assert textureData != null;
        stbi_image_free(textureData);
    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
