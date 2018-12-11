package com.alfainmo.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 */
public class ConfigUtils {

    protected static ConfigUtils instance = null;
    private ResourceBundle recursos = null;

    public static ConfigUtils getInstance() {
        if (instance == null) {
            instance = new ConfigUtils();
        }
        return instance;
    }

    protected ConfigUtils() {
        recursos = ResourceBundle.getBundle("ApplicationResources");
    }

    public static String getURLImagenes() {
        return getInstance().getString("urlImagenes");
    }

    public boolean getBoolean(String clave) {
        return recursos.getString(clave).equalsIgnoreCase("S");
    }

    public boolean getBoolean(String clave, boolean valorDefecto) {
        boolean result;
        try {
            result = getBoolean(clave);
        } catch (MissingResourceException ex) {
            result = valorDefecto;
        }
        return result;
    }

    public int getInteger(String clave) {
        return Integer.valueOf(recursos.getString(clave));
    }

    public int getInteger(String clave, int valorDefecto) {
        int result;
        try {
            result = getInteger(clave);
        } catch (MissingResourceException ex) {
            result = valorDefecto;
        }
        return result;
    }

    public long getLong(String clave) {
        return Long.valueOf(recursos.getString(clave));
    }

    public long getLong(String clave, long valorDefecto) {
        long result;
        try {
            result = getLong(clave);
        } catch (MissingResourceException ex) {
            result = valorDefecto;
        }
        return result;
    }

    public String getString(String clave) {
        return recursos.getString(clave);
    }

    public String getString(String clave, String valorDefecto) {
        String result;
        try {
            result = getString(clave);
        } catch (MissingResourceException ex) {
            result = valorDefecto;
        }
        return result;
    }

}
