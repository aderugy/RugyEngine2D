package com.aderugy.rugyengine2d.gameobjects.primitives;

import com.aderugy.rugyengine2d.gameobjects.Color;
import com.aderugy.rugyengine2d.gameobjects.materials.Material;
import com.aderugy.rugyengine2d.geom.Dimension;
import com.aderugy.rugyengine2d.geom.Position;
import com.aderugy.rugyengine2d.geom.Transform;
import com.aderugy.rugyengine2d.shaders.ShaderProgram;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;

public class Rectangle extends Shape {
    public Rectangle(Material material, Position a, Position b, Position c, Position d) {
        super(material);
        this.transform = new Transform(a, new Dimension(0.5f, 0.5f, 0));
        render(new Position[]{a, b, c, d}, new int[]{0, 1, 3, 1, 2, 3});
    }

    @Override
    public void draw() {
        glUseProgram(material.getShaderProgram().getShaderProgramID());
        glBindVertexArray(vaoID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);

        loadProjection();

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
    }
}
