package com.niemiec.reliablealarmv10.activity.alarm.launch.safe;

import android.content.Context;

import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.activity.BasePresenter;
import com.niemiec.reliablealarmv10.activity.alarm.launch.audio.AlarmClockAudioManager;
import com.niemiec.reliablealarmv10.activity.alarm.launch.vibration.AlarmClockVibrationManager;
import com.niemiec.reliablealarmv10.database.alarm.AlarmDataBase;
import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.Calendar;

public class SafeAlarmPresenter extends BasePresenter<SafeAlarmContractMVP.View> implements SafeAlarmContractMVP.Presenter {
    private Context context;
    private AlarmDataBase alarmDataBase;
    private AlarmClockAudioManager alarmClockAudioManager;
    private AlarmClockVibrationManager alarmClockVibrationManager;
    private Alarm alarm;

    public SafeAlarmPresenter(Context context) {
        super();
        this.context = context;
        alarmDataBase = AlarmDataBase.getInstance(context);
        alarmClockAudioManager = new AlarmClockAudioManager(context);
        alarmClockVibrationManager = new AlarmClockVibrationManager(context);
    }

    @Override
    public void initView(long id) {
        alarm = alarmDataBase.getAlarm(id);
        showAlarmData();
        callUpAlarm();
    }

    private void showAlarmData() {
        showAlarmClock();
        showActualClock();
        showPercentageInformation();
        //TODO
    }

    private void showAlarmClock() {
        int hour = alarm.alarmDateTime.getDateTime().get(Calendar.HOUR_OF_DAY);
        int minute = alarm.alarmDateTime.getDateTime().get(Calendar.MINUTE);
        view.showAlarmClock(hour, minute);
    }

    private void showActualClock() {
        Calendar calendar = Calendar.getInstance();
        view.showActualClock(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    private void showPercentageInformation() {
        view.showBatteryPercentageInfo(R.string.left_word + " " + alarm.safeAlarmLaunch.getSafeAlarmLaunchPercentage() + "% " + R.string.battery_word);
    }

    private void callUpAlarm() {
    }
}
