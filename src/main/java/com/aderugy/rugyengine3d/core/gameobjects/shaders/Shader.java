package com.aderugy.rugyengine3d.core.gameobjects.shaders;

public class Shader {
    private int shaderProgram;

    public Shader(int program) {
        this.shaderProgram = program;
    }

    public int getShaderProgram() {
        return shaderProgram;
    }
}
