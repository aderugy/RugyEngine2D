package com.aderugy.rugyengine2d;

import com.aderugy.rugyengine2d.shaders.ShaderManager;
import com.aderugy.rugyengine2d.utils.Utils;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class MeshLoader {
    ArrayList<Mesh> meshes;

    public MeshLoader() {
        meshes = new ArrayList<>();
    }

    public Mesh createMesh(float[] vertices, int size) {
        int vao = genVAO();
        storeData(size, vertices);
        glBindVertexArray(0);

        Mesh mesh = new Mesh(vao, size);
        meshes.add(mesh);
        return mesh;
    }

    private void storeData(int size, float[] data) {
        // Generate the VBO
        genVBO();

        // Store the data in the buffer
        FloatBuffer buffer = Utils.createFloatBuffer(data);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);


        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * Float.BYTES, 12);
        glEnableVertexAttribArray(1);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer textureData = stbi_load(ResourceManager.getImage("sprite.png").getAbsolutePath(), width, height, channels, 0);

        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, textureData);
        glGenerateMipmap(GL_TEXTURE_2D);

        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * Float.BYTES, 24);
        glEnableVertexAttribArray(2);

        stbi_image_free(textureData);

        // Unbind the buffer
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    /**
     * Creates and binds a Vertex Buffer Object.
     * @return ID of the generated VBO
     */
    private int genVBO() {
        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        return vbo;
    }

    /**
     * Creates and binds a Vertex Array Object.
     * @return ID of the generated VAO
     */
    private int genVAO() {
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);
        return vao;
    }

    public ArrayList<Mesh> getMeshes() {
        return meshes;
    }
}
