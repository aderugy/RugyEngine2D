package com.aderugy.rugyengine2d.utils;

public class Log {
    public static void err(String s) {
        System.err.println("[FAILED] " + s);
    }

    public static void success(String s) {
        System.out.println("[OK] " + s);
    }

    public static void log(String s) {
        System.out.println("[START] " + s);
    }
}
