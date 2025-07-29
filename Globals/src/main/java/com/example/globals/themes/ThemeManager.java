package com.example.globals.themes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {
    private static final String PREFS_NAME = "app_preferences";
    private static final String THEME_KEY = "app_theme";

    public static void applySavedTheme(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int savedMode = prefs.getInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_NO);
        AppCompatDelegate.setDefaultNightMode(savedMode);
    }

    public static void toggleTheme(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        int newMode;

        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            newMode = AppCompatDelegate.MODE_NIGHT_NO;
        } else {
            newMode = AppCompatDelegate.MODE_NIGHT_YES;
        }

        prefs.edit().putInt(THEME_KEY, newMode).apply();
        AppCompatDelegate.setDefaultNightMode(newMode);
    }

    public static boolean isDarkMode(Context context) {
        int currentNightMode = context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }
}
