package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.logic.SafeAlarmLaunchLogic;
import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.model.SafeAlarmLaunch;

import androidx.annotation.Nullable;

//TODO
public class SafeAlarmLaunchView extends LinearLayout {
    private SafeAlarmLaunchLogic logic;
    private ViewBuilder viewBuilder;

    public SafeAlarmLaunchView(Context context) {
        super(context);
        setProperties();
    }

    public SafeAlarmLaunchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setProperties();
    }

    public SafeAlarmLaunchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setProperties();
    }

    private void setProperties() {
        viewBuilder = new ViewBuilder(super.getContext());
        addViews();
        createSafeAlarmLaunchViewLogic(viewBuilder.getSafeAlarmLaunchButtons());
    }

    private void addViews() {
        addView(viewBuilder.getSafeAlarmLaunchDescription());
        addButtonsInView();
    }

    private void addButtonsInView() {
        for (SafeAlarmLaunchButton button : viewBuilder.getSafeAlarmLaunchButtons().getButtons()) {
            addView(button);
        }
    }

    private void createSafeAlarmLaunchViewLogic(SafeAlarmLaunchViewButtons buttons) {
        logic = new SafeAlarmLaunchLogic(buttons);
    }

    public void initialize(SafeAlarmLaunch safeAlarmLaunch) {
        logic.initialize(safeAlarmLaunch);
    }

    public SafeAlarmLaunch getSafeAlarmLaunch() {
        return logic.getSafeAlarmLaunch();
    }

}
