package com.example.alarmschedule.view.alarm.schedule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.logic.AlarmDateTimeLogic;

public class AlarmSchedule extends LinearLayout {
    private AlarmDateTimeLogic logic;
    private ViewBuilder viewBuilder;

    public AlarmSchedule(Context context) {
        super(context);
        init();
    }

    public AlarmSchedule(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlarmSchedule(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        viewBuilder = new ViewBuilder(super.getContext());
        setPropertiesToMainLinearLayout();
        addViewsToMainLinearLayout();
        createAlarmDateTimeLogic();
    }

    private void setPropertiesToMainLinearLayout() {
        setOrientation(VERTICAL);
        LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.setLayoutParams(mainParams);
    }

    private void addViewsToMainLinearLayout() {
        super.addView(viewBuilder.getFirstLineLayout());
        super.addView(viewBuilder.getSecondLineLayout());
    }

    private void createAlarmDateTimeLogic() {
        logic = new AlarmDateTimeLogic(viewBuilder.getDaysButtons(), viewBuilder.getInfoTextView(), viewBuilder.getCalendarImageButton());
    }

    public void initialize(AlarmDateTime alarmDateTime, FragmentManager supportFragmentManager) {
        logic.initialize(alarmDateTime, supportFragmentManager);
    }

    public void setTime(int hour, int minute) {
        logic.setTime(hour, minute);
    }

    public void setDate(int year, int month, int day) {
        logic.setDate(year, month, day);
    }

    public AlarmDateTime getAlarmDateTime() {
        return logic.getAlarmDateTime();
    }

}
