package com.aderugy.rugyengine2d.shaders;

import com.aderugy.rugyengine2d.utils.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;

public class ShaderManager {

    public int compile(String path, int type) {
        String shaderType = getShaderName(type);

        Log.log("Compiling " + shaderType + " shader '" + path + "'");
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
            Log.err("No such shader '" + path + "'");
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

    public int link(int[] shaders) {
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
}
