package com.example.alarmschedule.view.alarm.schedule.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alarmschedule.view.alarm.schedule.adarm.datetime.AlarmDateTime;
import com.example.alarmschedule.view.alarm.schedule.text.DateTextGenerator;

public class InfoTextView {
    private final Context context;
    private TextView info;

    public InfoTextView(Context context, int weight) {
        this.context = context;
        createView(weight);
    }

    private void createView(int weight) {
        info = new TextView(context);
        LinearLayout.LayoutParams params = createParams(weight);
        info.setLayoutParams(params);
    }

    private LinearLayout.LayoutParams createParams(int weight) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = weight;
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
