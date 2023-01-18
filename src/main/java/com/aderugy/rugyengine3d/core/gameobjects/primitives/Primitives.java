package com.aderugy.rugyengine3d.core.gameobjects.primitives;

import com.aderugy.rugyengine3d.core.gameobjects.components.Material;
import com.aderugy.rugyengine3d.core.gameobjects.fragmentdata.Vertex;
import com.aderugy.rugyengine3d.core.gameobjects.fragmentdata.VertexData;
import com.aderugy.rugyengine3d.core.gameobjects.shaders.Shader;

public final class Primitives {

    public static Triangle triangle(Shader shader, Material material, float x1, float y1, float x2, float y2, float x3, float y3, float z) {
        return new Triangle(shader,
                new Vertex(new VertexData(new float[][]{
                        { x1, y1, z },
                        { x2, y2, z },
                        { x3, y3, z }
                })),
                material);
    }
    public static Rectangle rectangle(Shader shader, Material material, float x, float y, float z, float width, float height) {
        return new Rectangle(shader,
                new Vertex(new VertexData(new float[][]{
                        { x        , y         , z },
                        { x        , y + height, z },
                        { x + width, y + height, z },
                        { x + width, y         , z },
                })),
                material);
    }
    public static Cube cube(Shader shader, Material material, float size, float x, float y, float z) {
        return new Cube(shader,
                new Vertex(new VertexData(new float[][]{
                { x       , y       , z        },
                { x + size, y       , z        },
                { x + size, y + size, z        },
                { x + size, y + size, z        },
                { x       , y + size, z        },
                { x       , y       , z        },
                { x       , y       , z + size },
                { x + size, y       , z + size },
                { x + size, y + size, z + size },
                { x + size, y + size, z + size },
                { x       , y + size, z + size },
                { x       , y       , z + size },
                { x       , y + size, z + size },
                { x       , y + size, z        },
                { x       , y       , z        },
                { x       , y       , z        },
                { x       , y       , z + size },
                { x       , y + size, z + size },
                { x + size, y + size, z + size },
                { x + size, y + size, z        },
                { x + size, y       , z        },
                { x + size, y       , z        },
                { x + size, y       , z + size },
                { x + size, y + size, z + size },
                { x       , y       , z        },
                { x + size, y       , z        },
                { x + size, y       , z + size },
                { x + size, y       , z + size },
                { x       , y       , z + size },
                { x       , y       , z        },
                { x       , y + size, z        },
                { x + size, y + size, z        },
                { x + size, y + size, z + size },
                { x + size, y + size, z + size },
                { x       , y + size, z + size },
                { x       , y + size, z        },
        })),
                material);
    }
}
