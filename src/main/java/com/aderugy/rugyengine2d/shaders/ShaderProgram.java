package com.aderugy.rugyengine2d.shaders;

import com.aderugy.rugyengine2d.ResourceManager;
import com.aderugy.rugyengine2d.utils.Log;

import java.io.*;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    private int shaderProgramID;

    public ShaderProgram(String name) {
        this.shaderProgramID = createShaderProgram(name);
    }

    private int createShaderProgram(String name) {
        int vertexID = compile(ResourceManager.getShader(name, ".vert"), GL_VERTEX_SHADER);
        int fragmentID = compile(ResourceManager.getShader(name, ".frag"), GL_FRAGMENT_SHADER);

        int program = link(new int[] {vertexID, fragmentID});

        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);

        return program;
    }

    private int compile(File path, int type) {
        String shaderType = getShaderName(type);

        Log.log("Compiling " + shaderType + " shader '" + path.getPath() + "'");
        StringBuilder shaderContent = new StringBuilder();

        try {
            Log.log("Reading from source code");
            BufferedReader reader = new BufferedReader(new FileReader(path));

            while (reader.ready())
                shaderContent.append((char) reader.read());
            reader.close();
            Log.success("Reading shader source code");
        }
        catch (FileNotFoundException e) {
            Log.err("No such shader '" + path.getPath() + "'");
        }
        catch (IOException e) {
            Log.err("Reading shader source code failed");
            e.printStackTrace();
        }

        int shaderPointer = glCreateShader(type);
        glShaderSource(shaderPointer, shaderContent.toString());
        glCompileShader(shaderPointer);

        int success = glGetShaderi(shaderPointer, GL_COMPILE_STATUS);

        if (success == GL_TRUE) {
            Log.success("Compiling " + shaderType + " shader");
            return shaderPointer;
        }

        Log.err("Compiling " + shaderType + " shader");
        throw new IllegalStateException();
    }

    private int link(int[] shaders) {
        String operation = "Linking shaders";

        Log.log(operation);
        int program = glCreateProgram();
        for (int shader : shaders) {
            glAttachShader(program, shader);
            Log.success("Attaching shader " + shader);
        }
        glLinkProgram(program);

        int success = glGetProgrami(program, GL_LINK_STATUS);
        if (success != GL_TRUE) {
            Log.err(operation);
            throw new IllegalStateException();
        }

        Log.success(operation);
        return program;
    }

    private static String getShaderName(int type) {
        String shaderType;
        switch (type) {
            case GL_VERTEX_SHADER -> shaderType = "Vertex";
            case GL_FRAGMENT_SHADER -> shaderType = "Fragment";
            default -> shaderType = "";
        }
        return shaderType;
    }

    public int getShaderProgramID() {
        return shaderProgramID;
    }
}
