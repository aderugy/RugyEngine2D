package com.aderugy.rugyengine3d.core.gameobjects.fragmentdata;

import com.aderugy.rugyengine2d.utils.Utils;
import com.aderugy.rugyengine3d.core.exceptions.IllegalVertexDataException;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 * The Vertex class allows
 */
public final class Vertex {
    private int totalLength;
    private VertexData[] vertices;

    /**
     * Creates a Vertex instance which contains and handles the data to be passed to OpenGL.
     * @param vertices vertex buffer arrays that contain the data to be passed to GL.
     *                 They should all contain a matrix of float data.
     * @throws IllegalVertexDataException if no buffers have been passed ('vertices' is empty)
     */
    public Vertex(VertexData... vertices) {
        int vertexCount = vertices.length;

        // 'vertices' is empty
        if (vertexCount == 0)
            throw new IllegalVertexDataException();

        int vertexStride = vertices[0].getVertexStride();
        this.vertices = new VertexData[vertexCount];

        this.totalLength = 0;

        for (int i = 0; i < vertexCount; i++) {
            VertexData current = vertices[i];

            // Checking if all VertexData contain the same amount of vertices
            if (current.getVertexStride() != vertexStride)
                throw new IllegalVertexDataException();

            this.totalLength += current.getLength();
            this.vertices[i] = current;
        }
    }

    /**
     * Telling OpenGL where to find the vertex data and how to interpret it,
     * and enabling the corresponding vertex attribute array.
     */
    public void attribPointers() {
        int totalByteLength = getTotalByteLength();
        int currentPointerAsBytes = 0;

        for (int i = 0; i < vertices.length; i++) {
            VertexData currentVertexData = vertices[i];
            int currentVertexStride = currentVertexData.getVertexStride();

            glVertexAttribPointer(i, currentVertexStride, GL_FLOAT, false, totalByteLength, currentPointerAsBytes);
            glEnableVertexAttribArray(i);

            currentPointerAsBytes += currentVertexStride * Float.BYTES;
        }
    }

    /**
     * @return the data as an array of float that can be used by OpenGL
     */
    public FloatBuffer get() {
        int totalLength = getTotalLength();

        float[] data = new float[totalLength];

        int position = 0;
        for (int i = 0; i < getVertexCount(); i++) {
            for (VertexData vertex : vertices) {
                float[] toCopy = vertex.getData(i);

                for (float v : toCopy) {
                    data[position] = v;
                    position++;
                }
            }
        }

        return Utils.createFloatBuffer(data);
    }

    /**
     * @return the number of pieces of data contained by the Vertex
     */
    private int getTotalLength() {
        int totalLength = 0;

        for (VertexData vertexData : vertices) {
            totalLength += vertexData.getLength();
        }

        return totalLength;
    }

    private int getTotalStride() {
        int stride = 0;

        for (VertexData data : vertices) {
            stride += data.getVertexStride();
        }

        return stride;
    }
    /**
     * @return The weight of the data as bytes
     */
    private int getTotalByteLength() {
        return getTotalStride() * Float.BYTES;
    }

    /**
     * Merges Vertex instances. It basically merges the 'vertices' attribute
     * of each Vertex and updates the other fields.
     * @param toMerge array of Vertex instances to merge.
     * @return the merged Vertex instance.
     */
    public static Vertex merge(Vertex... toMerge) {
        int totalVertexDataCount = 0;

        for (Vertex vertex :
                toMerge) {
            totalVertexDataCount += vertex.vertices.length;
        }

        VertexData[] mergedVertexData = new VertexData[totalVertexDataCount];

        int position = 0;
        for (Vertex vertex : toMerge) {
            for (VertexData data : vertex.vertices) {
                mergedVertexData[position] = data;
                position++;
            }
        }

        return new Vertex(mergedVertexData);
    }

    public int getVertexCount() {
        return vertices[0].getVertexCount();
    }
}
