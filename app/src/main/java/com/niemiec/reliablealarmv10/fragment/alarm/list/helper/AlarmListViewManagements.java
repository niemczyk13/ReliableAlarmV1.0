package com.niemiec.reliablealarmv10.fragment.alarm.list.helper;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.niemiec.reliablealarmv10.R;
import com.niemiec.reliablealarmv10.fragment.alarm.list.AlarmListContractMVP;
import com.niemiec.reliablealarmv10.fragment.alarm.list.dialog.CreateNewGroupAlarmDialog;

public class AlarmListViewManagements {
    public ListView alarmListView;
    public FrameLayout mask;
    public FloatingActionButton addNewAlarmButton;
    public LinearLayout addSingleOrGroupAlarm;
    public MaterialButton addSingleAlarmButton;
    public MaterialButton addGroupAlarmButton;
    public LinearLayout cancelOrDelete;
    public Button cancelDeleteAlarmButton;
    public Button deleteAlarmButton;
    public CreateNewGroupAlarmDialog dialog;

    public AlarmListViewManagements(View view, AlarmListContractMVP.View fragment) {
        initView(view, fragment);
    }

    private void initView(View view, AlarmListContractMVP.View fragment) {
        alarmListView = view.findViewById(R.id.alarm_list_view);
        mask = view.findViewById(R.id.full_screen_mask);
        addNewAlarmButton = view.findViewById(R.id.add_alarm_button);
        addSingleOrGroupAlarm = view.findViewById(R.id.add_single_or_group_alarm_linear_layout);
        addSingleAlarmButton = view.findViewById(R.id.add_single_alarm_button);
        addGroupAlarmButton = view.findViewById(R.id.add_group_alarm_button);
        cancelOrDelete = view.findViewById(R.id.cancel_or_delete_linear_layout);
        cancelDeleteAlarmButton = view.findViewById(R.id.cancel_delete_alarm_button);
        deleteAlarmButton = view.findViewById(R.id.delete_alarm_button);
        dialog = new CreateNewGroupAlarmDialog(fragment, view.getContext());
    }
}
