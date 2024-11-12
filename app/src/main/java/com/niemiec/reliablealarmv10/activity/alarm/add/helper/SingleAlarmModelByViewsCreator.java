package com.niemiec.reliablealarmv10.activity.alarm.add.helper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

@RequiresApi(api = Build.VERSION_CODES.M)
public class SingleAlarmModelByViewsCreator {
    public static SingleAlarmModel create(AddSingleAlarmViewManagements viewManagements) {
        return createSingleAlarmMode(viewManagements);
    }

    public static SingleAlarmModel create(AddSingleAlarmViewManagements viewManagements, long groupAlarmId) {
        SingleAlarmModel singleAlarmModel = createSingleAlarmMode(viewManagements);
        singleAlarmModel.setGroupAlarmId(groupAlarmId);
        return singleAlarmModel;
    }

    private static SingleAlarmModel createSingleAlarmMode(AddSingleAlarmViewManagements viewManagements) {
        viewManagements.alarmDateTimeView.calculateDateToTime();

        return SingleAlarmModel.builder()
                .alarmDateTime(viewManagements.alarmDateTimeView.getAlarmDateTime())
                .sound(viewManagements.alarmSoundView.getSound())
                .nap(viewManagements.napView.getNap())
                .risingSound(viewManagements.risingSoundView.getRisingSound())
                .safeAlarmLaunch(viewManagements.safeAlarmLaunchView.getSafeAlarmLaunch())
                .volume(viewManagements.volumeSeekBar.getProgress())
                .vibration(viewManagements.vibrationSwitch.isChecked())
                .isActive(true)
                .build();
    }
}
