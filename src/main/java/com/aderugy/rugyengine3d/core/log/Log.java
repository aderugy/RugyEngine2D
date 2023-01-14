package com.aderugy.rugyengine3d.core.log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Handles the logs of the project.
 */
public class Log {
    private static void log(LogPriority priority, String message) {
        System.out.printf("[%s] [%s] %s%n", new SimpleDateFormat("HH:mm:ss").format(new Date()), priority.name(), message);
    }

    public static void debug(String message) {
        log(LogPriority.DEBUG, message);
    }

    public static void error(String message) {
        log(LogPriority.ERROR, message);
    }

    public static void info(String message) {
        log(LogPriority.INFO, message);
    }

    public static void warning(String message) {
        log(LogPriority.WARNING, message);
    }
}
