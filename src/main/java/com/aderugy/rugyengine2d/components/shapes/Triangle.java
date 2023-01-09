package com.aderugy.rugyengine2d.components.shapes;

import com.aderugy.rugyengine2d.components.Color;
import com.aderugy.rugyengine2d.geom.Position;
import com.aderugy.rugyengine2d.shaders.ShaderManager;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;

public class Triangle extends Shape {

    public Triangle(Position a, Position b, Position c) {
        super();
        render(new Position[]{a, b, c}, new int[]{0, 1, 2});
    }

    public Triangle(Position a, Position b, Position c, Color color) {
        super(color);
        render(new Position[]{a, b, c}, new int[]{0, 1, 2});
    }

    @Override
    public void draw() {
        glUseProgram(ShaderManager.getInstance().SHAPE_SHADER_PROGRAM);
        glBindVertexArray(vaoID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0);
    }
}
