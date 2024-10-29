package com.niemiec.reliablealarmv10.fragment.alarm.list.helper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.niemiec.reliablealarmv10.model.custom.Alarm;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
public class AlarmListListenerHelper {
    private final AlarmListViewHelper viewHelper;

    public AlarmListListenerHelper(AlarmListViewHelper viewHelper){
        this.viewHelper = viewHelper;
    }

    public void setupListeners(AlarmListActionListener listener) {
        viewHelper.setOnMaskClick(view -> listener.onFullScreenMaskClick());
        viewHelper.setOnCancelDeleteButtonClick(view -> listener.onCancelDeleteButtonClick());
        viewHelper.setOnAlarmListItemClick((parent, view, position, id) -> listener.onAlarmListItemClick(viewHelper.getAlarmFromAlarmList(position), position));
        viewHelper.setOnAddNewAlarmButtonClick(view -> listener.onAddNewAlarmButtonClick());
        viewHelper.setOnAddGroupAlarmButtonClick(view -> listener.onCreateGroupAlarmButtonClick());
        viewHelper.setOnAddSingleAlarmButtonClick(view -> listener.onCreateSingleAlarmButtonClick());
        viewHelper.setOnDeleteButtonClick(view -> listener.onDeleteButtonClick(viewHelper.getSelectedAlarmsFromAlarmList()));
    }

    public interface AlarmListActionListener {
        void onFullScreenMaskClick();
        void onCancelDeleteButtonClick();
        void onAlarmListItemClick(Alarm alarm, int position);
        void onAddNewAlarmButtonClick();
        void onCreateGroupAlarmButtonClick();
        void onCreateSingleAlarmButtonClick();
        void onDeleteButtonClick(List<Alarm> selectedAlarms);
    }
}
