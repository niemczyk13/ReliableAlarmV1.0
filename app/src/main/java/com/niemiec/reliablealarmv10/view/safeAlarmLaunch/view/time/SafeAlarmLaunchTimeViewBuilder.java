package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view.time;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.niemiec.reliablealarmv10.R;

public class SafeAlarmLaunchTimeViewBuilder {
    private  TextView safeAlarmLaunchTimeDescription;
    private final Spinner safeAlarmLaunchTimeSpinner;

    public SafeAlarmLaunchTimeViewBuilder(Context context) {
        createDescription(context);
        safeAlarmLaunchTimeSpinner= createSpinner(context);
    }

    private void createDescription(Context context) {
        safeAlarmLaunchTimeDescription = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.weight = 1;
        safeAlarmLaunchTimeDescription.setLayoutParams(params);
        safeAlarmLaunchTimeDescription.setText(R.string.safe_alarm_launch_time_description);
    }

    private Spinner createSpinner(Context context) {
        Spinner spinner = new Spinner(context);
        spinner.setGravity(Gravity.RIGHT);

        String[] str = {SafeAlarmLaunchTimeValue.LACK.getName(), SafeAlarmLaunchTimeValue.FIRST.getName(),
                SafeAlarmLaunchTimeValue.SECOND.getName(), SafeAlarmLaunchTimeValue.THIRD.getName(),
                SafeAlarmLaunchTimeValue.FOURTH.getName(), SafeAlarmLaunchTimeValue.FIFTH.getName(),
                SafeAlarmLaunchTimeValue.SIXTH.getName(), SafeAlarmLaunchTimeValue.SEVENTH.getName(),
                SafeAlarmLaunchTimeValue.EIGHTH.getName(), SafeAlarmLaunchTimeValue.NINTH.getName(),
                SafeAlarmLaunchTimeValue.TENTH.getName()};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, str);
        spinner.setAdapter(adapter);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        spinner.setLayoutParams(params);

        return spinner;
    }

    public TextView getSafeAlarmLaunchTimeDescription() {
        return safeAlarmLaunchTimeDescription;
    }

    public Spinner getSafeAlarmLaunchTimeSpinner() {
        return safeAlarmLaunchTimeSpinner;
    }
}
