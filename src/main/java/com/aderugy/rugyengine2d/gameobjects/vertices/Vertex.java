package com.aderugy.rugyengine2d.gameobjects.vertices;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class Vertex {
    private VertexData[] vertexData;
    private int length;

    public Vertex(VertexData... data) {
        vertexData = data;
        this.length = vertexData.length;
    }

    public float[] get() {
        float[] data = new float[getVertexCount()];

        int count = 0;
        for (VertexData vertex : vertexData) {
            for (int i = 0; i < vertex.getData().length; i++) {
                data[count + i] = vertex.getData()[i];
            }

            count += vertex.getLength();
        }

        return data;
    }

    public int getByteLength() {
        return this.getVertexCount() * Float.BYTES;
    }

    public int getVertexCount() {
        int total = 0;

        for (VertexData data : vertexData) {
            total += data.getLength();
        }

        return total;
    }

    public int getLength() {
        return this.length;
    }

    public VertexData[] getVertexData() {
        return vertexData;
    }

    public static Vertex merge(Vertex... vertices) {
        ArrayList<VertexData> mergedVertices = new ArrayList<>();

        for (Vertex v : vertices) {
            mergedVertices.addAll(Arrays.asList(v.vertexData));
        }

        return new Vertex((VertexData[]) mergedVertices.toArray());
    }
}
