package com.aderugy.rugyengine3d.core.gameobjects.fragmentdata;

import com.aderugy.rugyengine3d.core.exceptions.IllegalVertexDataException;

public class VertexData {
    private float[][] data;
    private int vertexStride;
    private int vertexCount;

    /**
     * Creates a VertexData object from a 2D array of OpenGL friendly data
     * @param data a non-empty matrix of float. Each row's length must be equal.
     */
    public VertexData(float[][] data) {
        this.vertexCount = data.length;
        if (vertexCount == 0)
            throw new IllegalVertexDataException();

        this.vertexStride = data[0].length;
        if (this.vertexStride == 0)
            throw new IllegalVertexDataException();

        this.data = data;

        for (float[] dataRow : data) {
            if (dataRow.length != vertexStride)
                throw new IllegalVertexDataException();
        }
    }

    /**
     * @return the number of vertices of which it contains data (number of rows of the 'data' field)
     */
    public int getVertexCount() {
        return this.vertexCount;
    }

    /**
     * @return the length of each piece of data (number of columns in the 'data' field)
     */
    public int getVertexStride() {
        return vertexStride;
    }

    /**
     * @return the number of elements in 'data'
     */
    public int getLength() {
        return vertexCount * vertexStride;
    }

    /**
     * @return the total weight of the 'data' field as bytes
     */
    public int getByteLength() {
        return getLength() * Float.BYTES;
    }

    /**
     * @param row the index of the queried row of the 'data' field
     * @return the queried row
     */
    public float[] getData(int row) {
        return data[row];
    }
}
