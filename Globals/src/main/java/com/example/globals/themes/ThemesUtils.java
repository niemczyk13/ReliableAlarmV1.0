package com.example.globals.themes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class ThemesUtils {
    public static int getThemeColor(Context context, int attrId, int fallbackColor) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attrId, typedValue, true)) {
            return typedValue.data;
        } else {
            return fallbackColor;
        }
    }

    public static float getThemeDimension(Context context, int attrId, float fallbackDp) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attrId, typedValue, true)) {
            if (typedValue.type == TypedValue.TYPE_DIMENSION) {
                return TypedValue.complexToDimension(typedValue.data, context.getResources().getDisplayMetrics());
            }
        }
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, fallbackDp, context.getResources().getDisplayMetrics());
    }

    public static String getThemeString(Context context, int attrId, String fallback) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attrId, typedValue, true)) {
            if (typedValue.resourceId != 0) {
                return context.getString(typedValue.resourceId);
            } else if (typedValue.string != null) {
                return typedValue.string.toString();
            }
        }
        return fallback;
    }

    public static Drawable getThemeDrawable(Context context, int attrId, @Nullable Drawable fallback) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attrId, typedValue, true)) {
            if (typedValue.resourceId != 0) {
                return ContextCompat.getDrawable(context, typedValue.resourceId);
            }
        }
        return fallback;
    }

    public static boolean getThemeBoolean(Context context, int attrId, boolean fallback) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(attrId, typedValue, true)) {
            return typedValue.data != 0;
        } else {
            return fallback;
        }
    }

}
