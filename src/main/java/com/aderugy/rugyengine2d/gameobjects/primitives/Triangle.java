package com.aderugy.rugyengine2d.gameobjects.primitives;

import com.aderugy.rugyengine2d.gameobjects.Color;
import com.aderugy.rugyengine2d.gameobjects.materials.Material;
import com.aderugy.rugyengine2d.geom.Position;
import com.aderugy.rugyengine2d.shaders.ShaderProgram;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;

public class Triangle extends Shape {

    public Triangle(Material material, Position a, Position b, Position c) {
        super(material);
        render(new Position[]{a, b, c}, new int[]{0, 1, 2});
    }

    @Override
    public void draw() {
        glUseProgram(material.getShaderProgram().getShaderProgramID());
        glBindVertexArray(vaoID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0);
    }
}
