package com.example.alarmschedule.view.alarm.schedule.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

public class DataFromMainThemeUtils {
    public static int getColorFromTheme(Context context, int colorInTheme)
    {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme(); // np. getContext() w View, albo this w Activity
        theme.resolveAttribute(colorInTheme, typedValue, true);
        return typedValue.data;
    }
}
