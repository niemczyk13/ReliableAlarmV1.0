package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view.time;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.niemiec.reliablealarmv10.view.safeAlarmLaunch.logic.SafeAlarmLaunchLogic;

public class SafeAlarmLaunchTimeView extends LinearLayout {
    private SafeAlarmLaunchTimeLogic logic;
    private SafeAlarmLaunchTimeViewBuilder viewBuilder;

    public SafeAlarmLaunchTimeView(Context context) {
        super(context);
    }

    public SafeAlarmLaunchTimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SafeAlarmLaunchTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SafeAlarmLaunchTimeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void setProperties() {
        viewBuilder = new SafeAlarmLaunchTimeViewBuilder(super.getContext());
        addViews();
        logic = new SafeAlarmLaunchTimeLogic(viewBuilder.getSafeAlarmLaunchTimeSpinner());
    }

    private void addViews() {
        super.addView(viewBuilder.getSafeAlarmLaunchTimeDescription());
        super.addView(viewBuilder.getSafeAlarmLaunchTimeSpinner());
    }

    public void initialize(int time) {
        logic.initialize(time);
    }

    public int getTime() {
        return logic.getTime();
    }
}
