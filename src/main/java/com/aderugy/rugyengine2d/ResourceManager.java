package com.aderugy.rugyengine2d;

import java.io.File;
import java.net.URL;

/**
 * Manages the access to the resources.
 */
public class ResourceManager {
    /**
     * Singleton instance of the ResourceManager class.
     */
    public static ResourceManager instance = null;

    /**
     * @return Singleton instance of the ResourceManager class
     */
    private static ResourceManager getInstance() {
        if (instance == null) instance = new ResourceManager();

        return instance;
    }

    /**
     * Returns the associated shader (in resources/shaders)
     * @param filename name of the shader file
     * @return File corresponding to the associated shader
     */
    public static File getShader(String filename) {
        return getInstance().getResource("shaders/" + filename);
    }

    /**
     * Returns the associated image (in resources/image)
     * @param filename name of the image
     * @return File corresponding to the associated image
     */
    public static File getImage(String filename) {
        return getInstance().getResource("images/" + filename);
    }

    /**
     * @throws RuntimeException file not found
     * @param path path to the seeked resource.
     * @return File corresponding to the seeked resource.
     */
    private File getResource(String path) {
        URL url = getClass().getClassLoader().getResource(path);
        if (url == null)
            throw new RuntimeException("No such file: '" + path + "'");
        return new File (url.getPath());
    }
}
