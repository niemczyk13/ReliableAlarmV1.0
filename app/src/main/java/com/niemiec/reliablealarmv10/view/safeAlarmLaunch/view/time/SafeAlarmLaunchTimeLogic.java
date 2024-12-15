package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view.time;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import lombok.Getter;

public class SafeAlarmLaunchTimeLogic {
    @Getter
    private int time;
    private Spinner spinner;
    private int[] values = {SafeAlarmLaunchTimeValue.FIRST.getValue(), SafeAlarmLaunchTimeValue.SECOND.getValue(),
            SafeAlarmLaunchTimeValue.THIRD.getValue(), SafeAlarmLaunchTimeValue.FOURTH.getValue(),
            SafeAlarmLaunchTimeValue.FIFTH.getValue(), SafeAlarmLaunchTimeValue.SIXTH.getValue(),
            SafeAlarmLaunchTimeValue.SEVENTH.getValue(), SafeAlarmLaunchTimeValue.EIGHTH.getValue(),
            SafeAlarmLaunchTimeValue.NINTH.getValue(), SafeAlarmLaunchTimeValue.TENTH.getValue()};

    public SafeAlarmLaunchTimeLogic(Spinner safeAlarmLaunchTimeSpinner) {
        this.spinner = safeAlarmLaunchTimeSpinner;

    }

    public void initialize(int time) {
        this.time = time;
        showSelectionTime();
        setOnItemSelectedListener();
    }

    private void showSelectionTime() {
        for (int i = 0; i < values.length; i++) {
            if (time == values[i]) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void setOnItemSelectedListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                time = values[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
