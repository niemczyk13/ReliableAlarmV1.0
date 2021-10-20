package com.example.alarmschedule.view.alarm.schedule.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.text.DateTextGenerator;

public class InfoTextView {
    private final Context context;
    private TextView info;

    public InfoTextView(Context context, int percentage) {
        this.context = context;
        createView(percentage);
    }

    private void createView(int percentage) {
        info = new TextView(context);
        LinearLayout.LayoutParams params = createParams(percentage);
        info.setLayoutParams(params);
    }

    private LinearLayout.LayoutParams createParams(int percentage) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        double w = ((double) percentage / 100) * width;

        params.width = (int) w;
        params.topMargin = 40;
        params.bottomMargin = 2;
        return params;
    }

    public View getView() {
        return info;
    }

    public void showInfoText(AlarmDateTime alarmDateTime) {
        String text;
        if (alarmDateTime.isSchedule()) {
            text = DateTextGenerator.generate(alarmDateTime.getWeek());
        } else {
            text = DateTextGenerator.generate(alarmDateTime.getDateTime());
        }
        info.setText(text);
    }
}
