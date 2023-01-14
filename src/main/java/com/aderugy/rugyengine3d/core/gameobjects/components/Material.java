package com.aderugy.rugyengine3d.core.gameobjects.components;

import com.aderugy.rugyengine3d.core.gameobjects.fragmentdata.Vertex;
import com.aderugy.rugyengine3d.core.gameobjects.fragmentdata.VertexData;

public abstract class Material {
    protected float[][] data;

    public Vertex toVertex(int vertexCount) {
        float[][] vertexData = new float[vertexCount][];

        for (int i = 0; i < vertexCount; i++) {
            int dataRowLength = this.data[0].length;
            vertexData[i] = new float[dataRowLength];

            float[] currentDataRow = this.data[i % data.length];
            System.arraycopy(currentDataRow, 0, vertexData[i], 0, dataRowLength);
        }

        return new Vertex(new VertexData(vertexData));
    }

    public float[][] getData() {
        return this.data;
    }
}
