package com.example.config;

import java.util.Objects;

public class KeyHelper {

    private final static String defaultPrefix = "app";

    private static String prefix = null;

    public static void setPrefix(String keyPrefix) {
        prefix = keyPrefix;
    }

    public static String geyPrefix() {
        return Objects.requireNonNullElse(prefix, defaultPrefix);
    }

    public static String getKey(String key) {
        return geyPrefix() + ":" + key;
    }

}
