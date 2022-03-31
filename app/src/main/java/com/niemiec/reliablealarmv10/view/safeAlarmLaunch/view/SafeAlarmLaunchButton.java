package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view;

import android.content.Context;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;

public class SafeAlarmLaunchButton extends MaterialButton {
    private final int percentageValue;
    private SafeAlarmLaunchButtonClickListener listener;

    public SafeAlarmLaunchButton(String name, int value, @NonNull @NotNull Context context) {
        super(context);
        this.percentageValue = value;

        setOnClickListener(this::onClick);
    }
    //TODO
    
    private void onClick(View view) {
        listener.onClick(super.getId());
    }

    public interface SafeAlarmLaunchButtonClickListener {
        void onClick(int clickButtonId);
    }

    public void addClickSafeAlarmLaunchButtonClickListener(SafeAlarmLaunchButtonClickListener listener) {
        this.listener = listener;
    }
}
