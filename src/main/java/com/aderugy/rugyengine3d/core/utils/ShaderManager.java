package com.aderugy.rugyengine3d.core.utils;

import com.aderugy.rugyengine3d.core.log.Log;
import com.aderugy.rugyengine3d.core.gameobjects.shaders.Shader;

import java.io.*;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;

/**
 * Handles every operation related to shaders (reading, compiling, linking)
 * and provides the corresponding Shader instance.
 */
public final class ShaderManager
{
    /**
     * Creates a shader program by compiling all the shaders in the folder shaders/{name}
     * with the file name {name.}{shader extension} and returns the corresponding shader.
     * @param name name of the shader
     * @return Shader instance that contains the GL Shader Program ID
     */
    public static Shader createShaderProgram(String name) {
        int vertexID = compile(com.aderugy.rugyengine2d.ResourceManager.getShader(name, ".vert"), GL_VERTEX_SHADER);
        int fragmentID = compile(ResourceManager.getShader(name, ".frag"), GL_FRAGMENT_SHADER);

        Shader shader = link(new int[] {vertexID, fragmentID});

        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);

        return shader;
    }

    /**
     * Reads the content of a shader file and returns it as a StringBuilder.
     * @param path file containing the shader
     * @return the content of the file
     */
    private static StringBuilder readFromShader(File path) {
        StringBuilder shaderContent = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            Log.info("Reading from source code");

            while (reader.ready()) {
                shaderContent.append((char) reader.read());
            }
        }
        catch (FileNotFoundException e) {
            Log.error("No such shader '" + path.getPath() + "'");
            e.printStackTrace();
        }
        catch (IOException e) {
            Log.error("Reading shader source code failed");
            e.printStackTrace();
        }

        return shaderContent;
    }

    /**
     * Compiles the shader at the given path.
     * @param path shader file
     * @param type Used for debugging
     * @return Pointer to the GL Shader
     */
    private static int compile(File path, int type) {
        String shaderType = getShaderName(type);

        Log.info("Compiling " + shaderType + " shader '" + path.getPath() + "'");
        StringBuilder shaderContent = readFromShader(path);

        int shaderPointer = glCreateShader(type);
        glShaderSource(shaderPointer, shaderContent.toString());
        glCompileShader(shaderPointer);

        int success = glGetShaderi(shaderPointer, GL_COMPILE_STATUS);

        if (success == GL_TRUE) {
            Log.info("Success compiling " + shaderType + " shader");
            return shaderPointer;
        }

        Log.error("Failed compiling " + shaderType + " shader");
        throw new IllegalStateException();
    }

    /**
     * Links the given shader to a new shader program returned as a Shader instance.
     * @param shaders list of pointers to GL shaders
     * @return Shader corresponding to the newly created shader program
     */
    private static Shader link(int[] shaders) {
        Log.info("Started linking shaders");
        int program = glCreateProgram();
        for (int shader : shaders) {
            glAttachShader(program, shader);
            Log.info("Success attaching shader " + shader);
        }
        glLinkProgram(program);

        int success = glGetProgrami(program, GL_LINK_STATUS);
        if (success != GL_TRUE) {
            Log.error("Failed linking shaders...");
            throw new IllegalStateException();
        }

        Log.info("Success linking shader.");
        return new Shader(program);
    }

    /**
     * @param type Type of shader (Vertex or Fragment)
     * @return the type of shader as a String (for debugging purpose)
     */
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
