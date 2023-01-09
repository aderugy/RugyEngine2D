package com.aderugy.rugyengine2d.components.images;

import com.aderugy.rugyengine2d.ResourceManager;
import com.aderugy.rugyengine2d.components.Component;
import com.aderugy.rugyengine2d.geom.Position;
import com.aderugy.rugyengine2d.geom.Transform;
import com.aderugy.rugyengine2d.shaders.ShaderManager;
import com.aderugy.rugyengine2d.utils.Utils;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture extends Component {
    private static final int VERTEX_SIZE = 5;

    private int textureID;
    private String filename;

    private Position[] positions;
    private static final int[] indices = new int[] { 0, 1, 3, 1, 2, 3 };

    public Texture(String filename, Position a, Position b, Position c, Position d, Position texA, Position texB, Position texC, Position texD) {
        super();

        transform = new Transform(1, 1);

        this.filename = filename;
        this.textureID = glGenTextures();
        this.positions = new Position[] { a, b, c, d, texA, texB, texC, texD };

        render(positions, indices);
    }

    @Override
    public void draw() {
        glUseProgram(ShaderManager.getInstance().TEXTURE_SHADER_PROGRAM);
        glBindTexture(GL_TEXTURE_2D, textureID);
        glBindVertexArray(vaoID);

        loadProjection();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
    }

    @Override
    protected float[] genData(Position[] pos) {
        float[] data = new float[4 * VERTEX_SIZE];

        for (int i = 0; i < 4; i++) {
            int index = i * VERTEX_SIZE;
            Position cur = pos[i];
            Position tex = pos[i + 4];

            data[index] = cur.getX();
            data[index + 1] = cur.getY();
            data[index + 2] = 0;
            data[index + 3] = tex.getX();
            data[index + 4] = tex.getY();
        }
        return data;
    }

    @Override
    protected void render(Position[] vertexPositions, int[] indices) {
        glUseProgram(ShaderManager.getInstance().TEXTURE_SHADER_PROGRAM);

        // Binding VAO and VBO
        glBindVertexArray(vaoID);
        genVboID();

        // Parsing vertices to a FloatBuffer
        float[] vertices = genData(vertexPositions);
        FloatBuffer bufferedData = Utils.createFloatBuffer(vertices);

        // Storing the vertex data in the VBO
        glBufferData(GL_ARRAY_BUFFER, bufferedData, GL_STATIC_DRAW);

        // Binding the indices in the EBO
        bindIndices(indices);

        // Passing the vertex coordinates to the shader
        glVertexAttribPointer(0, 3,  GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

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
        glVertexAttribPointer(1, 2, GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        // Free memory from input stream
        assert textureData != null;
        stbi_image_free(textureData);

        // Unbinding VAO, VBO and EBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
}
