package com.aderugy.rugyengine2d.components.images;

import com.aderugy.rugyengine2d.ResourceManager;
import com.aderugy.rugyengine2d.components.Component;
import com.aderugy.rugyengine2d.components.Position;
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

public class Image extends Component {
    private int textureID;
    private String filename;

    public Image(String filename, Position a, Position b, Position c, Position d, Position texA, Position texB, Position texC, Position texD) {
        super();
        this.filename = filename;
        this.textureID = glGenTextures();

        Position[] positions = new Position[] { a, b, c, d, texA, texB, texC, texD };
        render(positions, new int[] { 0, 1, 3, 1, 2, 3 });
    }

    @Override
    public void draw() {
        glUseProgram(ShaderManager.getInstance().TEXTURE_SHADER_PROGRAM);
        glBindTexture(GL_TEXTURE_2D, textureID);
        glBindVertexArray(vaoID);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
    }

    @Override
    protected float[] genData(Position[] pos) {
        float[] data = new float[4 * 8];

        for (int i = 0; i < 4; i++) {
            int index = i * 8;
            Position cur = pos[i];
            Position tex = pos[i + 4];

            data[index] = cur.getX();
            data[index + 1] = cur.getY();
            data[index + 2] = 0;
            data[index + 3] = 0;
            data[index + 4] = 0;
            data[index + 5] = 0;
            data[index + 6] = tex.getX();
            data[index + 7] = tex.getY();
        }
        return data;
    }

    @Override
    protected void render(Position[] pos, int[] indices) {
        glBindVertexArray(vaoID);
        genVboID();

        FloatBuffer bufferedData = Utils.createFloatBuffer(genData(pos));
        glBufferData(GL_ARRAY_BUFFER, bufferedData, GL_STATIC_DRAW);
        bindIndices(indices);

        glVertexAttribPointer(0, 3,  GL_FLOAT, false, 8 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3,  GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer textureData = stbi_load(
                ResourceManager.getImage(this.filename).getAbsolutePath(),
                width, height, channels, 0);

        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, textureData);
        glGenerateMipmap(GL_TEXTURE_2D);

        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * Float.BYTES, 6 * Float.BYTES);
        glEnableVertexAttribArray(2);

        stbi_image_free(textureData);

        // Unbind the buffer
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
