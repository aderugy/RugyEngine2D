package com.aderugy.rugyengine3d.core.exceptions;

import com.aderugy.rugyengine3d.core.log.Log;

public class IllegalVertexDataException extends IllegalArgumentException {
    public IllegalVertexDataException() {
        super();
        Log.error("One or more errors occurred while parsing the vertex data. " +
                "Please make sure that the vertex data is correct (each component has the same number of vertices)");
    }
}
