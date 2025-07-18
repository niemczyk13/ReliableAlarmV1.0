package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.globals.themes.ThemesUtils;
import com.google.android.material.button.MaterialButton;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class SafeAlarmLaunchButton extends MaterialButton {
    private final int percentageValue;
    private SafeAlarmLaunchButtonClickListener listener;

    public SafeAlarmLaunchButton(String name, int value, @NonNull @NotNull Context context) {
        super(context);
        this.percentageValue = value;
        createMaterialButton(name);
        setOnClickListener(this::onClick);
    }
    //TODO
    private void createMaterialButton(String name) {
        super.setText(name);
        LinearLayout.LayoutParams params = getDefaultLayoutParamsForDayButton();
        super.setLayoutParams(params);
        super.setPadding(0, 0, 0, 0);
        super.setCheckable(true);
        super.setId(View.generateViewId());
        setUncheckColorButton();
    }

    private LinearLayout.LayoutParams getDefaultLayoutParamsForDayButton() {
        final float scale = super.getContext().getResources().getDisplayMetrics().density;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 2;
        params.leftMargin = 1;
        params.rightMargin = 1;
        int dp = 55;
        params.height = (int) ((dp * scale) + 0.5f);

        return params;
    }

    private void setUncheckColorButton() {
        super.setBackgroundColor(Color.rgb(0, 0, 0));
        super.setTextColor(Color.rgb(255, 255, 255));
    }

    private void setCheckColorButton() {
        super.setBackgroundColor(ThemesUtils.getThemeColor(getContext(), com.niemiec.risingview.R.attr.colorPrimary, ContextCompat.getColor(getContext(), com.niemiec.risingview.R.color.main_blue)));
        super.setTextColor(Color.rgb(255,255,255));
    }

    public int getId() {
        return super.getId();
    }

    public void setChecked() {
        super.setChecked(true);
        setCheckColorButton();
    }

    public void setUnchecked() {
        super.setChecked(false);
        setUncheckColorButton();
    }

    public int getPercentageValue() {
        return percentageValue;
    }

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
