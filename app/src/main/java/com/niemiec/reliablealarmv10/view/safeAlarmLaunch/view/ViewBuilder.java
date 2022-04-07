package com.niemiec.reliablealarmv10.view.safeAlarmLaunch.view;

import android.content.Context;
import android.widget.TextView;

import com.niemiec.reliablealarmv10.R;

public class ViewBuilder {
    private final TextView safeAlarmLaunchDescription;
    private final SafeAlarmLaunchViewButtons buttons;

    public ViewBuilder(Context context) {
        safeAlarmLaunchDescription = new TextView(context);
        safeAlarmLaunchDescription.setText(R.string.safe_alarm_launch_descrption);
        buttons = new SafeAlarmLaunchViewButtons(context);
    }

    public TextView getSafeAlarmLaunchDescription() {
        return safeAlarmLaunchDescription;
    }

    public SafeAlarmLaunchViewButtons getSafeAlarmLaunchButtons() {
        return buttons;
    }
}
