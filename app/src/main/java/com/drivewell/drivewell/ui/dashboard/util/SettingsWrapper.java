package com.drivewell.drivewell.ui.dashboard.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

public class SettingsWrapper {
    public static String FILE_PREFIX = null;
    public static long FIRST_LOGIN_TIME = 0;
    public static float G_FORCE_MAX_G = 0.0f;
    public static float G_FORCE_TRAIL_LENGTH = 0.0f;
    public static boolean G_HORIZONTAL_INVERT = false;
    public static boolean G_VERTICAL_INVERT = false;
    public static boolean IS_FIRST_LOGIN = false;
    public static boolean IS_SHOW_RATING = false;
    private static final String KEY = "g-force-meter";
    public static long LAST_LOGIN_TIME = 0;
    public static final float MAX_G_FORCE_MAX_G = 5.0f;
    public static final float MAX_G_FORCE_TRAIL_LENGTH = 30.0f;
    public static final float MAX_SENSOR_COF = 1.0f;
    public static final float MAX_SENSOR_REFRESH_RATE = 60.0f;
    public static final float MAX_SENSOR_SAMPLE_RATE = 20.0f;
    public static final float MIN_G_FORCE_MAX_G = 0.5f;
    public static final float MIN_G_FORCE_TRAIL_LENGTH = 1.0f;
    public static final float MIN_SENSOR_COF = 0.1f;
    public static final float MIN_SENSOR_REFRESH_RATE = 10.0f;
    public static final float MIN_SENSOR_SAMPLE_RATE = 1.0f;
    public static final long ONE_WEEK_MILLS = 604800000;
    public static float SENSOR_COF;
    public static float SENSOR_REFRESH_RATE;
    public static float SENSOR_SAMPLE_RATE;
    private static Context m_context;
    private static SharedPreferences m_prefs;

    public static void init(Context ctx) {
        m_context = ctx;
        m_prefs = m_context.getSharedPreferences(KEY, 0);
        getSettings();
        Editor editor = m_prefs.edit();
        if (IS_FIRST_LOGIN) {
            FIRST_LOGIN_TIME = System.currentTimeMillis();
            editor.putLong("FIRST_LOGIN_TIME", FIRST_LOGIN_TIME);
        } else if (m_prefs.contains("FIRST_LOGIN_TIME")) {
            FIRST_LOGIN_TIME = m_prefs.getLong("FIRST_LOGIN_TIME", System.currentTimeMillis());
        } else {
            FIRST_LOGIN_TIME = System.currentTimeMillis();
            editor.putLong("FIRST_LOGIN_TIME", FIRST_LOGIN_TIME);
        }
        LAST_LOGIN_TIME = System.currentTimeMillis();
        editor.putLong("LAST_LOGIN_TIME", LAST_LOGIN_TIME);
        editor.commit();
    }

    public static void getSettings() {
        if (m_prefs != null) {
            IS_FIRST_LOGIN = m_prefs.getBoolean("IS_FIRST_LOGIN", true);
            IS_SHOW_RATING = m_prefs.getBoolean("IS_SHOW_RATING", true);
            SENSOR_REFRESH_RATE = m_prefs.getFloat("SENSOR_REFRESH_RATE", MAX_G_FORCE_TRAIL_LENGTH);
            SENSOR_SAMPLE_RATE = m_prefs.getFloat("SENSOR_SAMPLE_RATE", MAX_G_FORCE_MAX_G);
            SENSOR_COF = m_prefs.getFloat("SENSOR_COF", MIN_G_FORCE_MAX_G);
            G_FORCE_TRAIL_LENGTH = m_prefs.getFloat("G_FORCE_TRAIL_LENGTH", MAX_SENSOR_SAMPLE_RATE);
            G_FORCE_MAX_G = m_prefs.getFloat("G_FORCE_MAX_G", 1.0f);
            G_VERTICAL_INVERT = m_prefs.getBoolean("G_VERTICAL_INVERT", false);
            G_HORIZONTAL_INVERT = m_prefs.getBoolean("G_HORIZONTAL_INVERT", false);
            FILE_PREFIX = m_prefs.getString("FILE_PREFIX", "g-");
        }
    }

    public static void setSettings(Bundle data) {
        if (m_prefs != null) {
            Editor editor = m_prefs.edit();
            if (data.containsKey("IS_FIRST_LOGIN")) {
                IS_FIRST_LOGIN = data.getBoolean("IS_FIRST_LOGIN");
                editor.putBoolean("IS_FIRST_LOGIN", IS_FIRST_LOGIN);
            }
            if (data.containsKey("IS_SHOW_RATING")) {
                IS_SHOW_RATING = data.getBoolean("IS_SHOW_RATING");
                editor.putBoolean("IS_SHOW_RATING", IS_SHOW_RATING);
            }
            if (data.containsKey("SENSOR_REFRESH_RATE")) {
                SENSOR_REFRESH_RATE = data.getFloat("SENSOR_REFRESH_RATE");
                editor.putFloat("SENSOR_REFRESH_RATE", SENSOR_REFRESH_RATE);
            }
            if (data.containsKey("SENSOR_SAMPLE_RATE")) {
                SENSOR_SAMPLE_RATE = data.getFloat("SENSOR_SAMPLE_RATE");
                editor.putFloat("SENSOR_SAMPLE_RATE", SENSOR_SAMPLE_RATE);
            }
            if (data.containsKey("SENSOR_COF")) {
                SENSOR_COF = data.getFloat("SENSOR_COF");
                editor.putFloat("SENSOR_COF", SENSOR_COF);
            }
            if (data.containsKey("G_FORCE_TRAIL_LENGTH")) {
                G_FORCE_TRAIL_LENGTH = data.getFloat("G_FORCE_TRAIL_LENGTH");
                editor.putFloat("G_FORCE_TRAIL_LENGTH", G_FORCE_TRAIL_LENGTH);
            }
            if (data.containsKey("G_FORCE_MAX_G")) {
                G_FORCE_MAX_G = data.getFloat("G_FORCE_MAX_G");
                editor.putFloat("G_FORCE_MAX_G", G_FORCE_MAX_G);
            }
            if (data.containsKey("G_VERTICAL_INVERT")) {
                G_VERTICAL_INVERT = data.getBoolean("G_VERTICAL_INVERT");
                editor.putBoolean("G_VERTICAL_INVERT", G_VERTICAL_INVERT);
            }
            if (data.containsKey("G_HORIZONTAL_INVERT")) {
                G_HORIZONTAL_INVERT = data.getBoolean("G_HORIZONTAL_INVERT");
                editor.putBoolean("G_HORIZONTAL_INVERT", G_HORIZONTAL_INVERT);
            }
            if (data.containsKey("FILE_PREFIX")) {
                FILE_PREFIX = data.getString("FILE_PREFIX");
                editor.putString("FILE_PREFIX", FILE_PREFIX);
            }
            editor.commit();
        }
    }
}
