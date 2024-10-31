package com.niemiec.reliablealarmv10.fragment.alarm.list.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.globals.enums.AddSingleAlarmType;
import com.example.globals.enums.BundleNames;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmActivity;
import com.niemiec.reliablealarmv10.activity.alarm.add.AddAlarmPresenter;
import com.niemiec.reliablealarmv10.activity.alarm.group.GroupAlarmActivity;
import com.niemiec.reliablealarmv10.model.custom.GroupAlarmModel;
import com.niemiec.reliablealarmv10.model.custom.SingleAlarmModel;

public class AlarmActivityNavigationHelper {

    private final Context context;

    public AlarmActivityNavigationHelper(Context context) {
        this.context = context;
    }

    public void navigateToAddAlarmActivity(AddSingleAlarmType addSingleAlarmType) {
        Intent intent = new Intent(context, AddAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleNames.TYPE.name(), AddAlarmPresenter.Type.CREATE);
        bundle.putSerializable(BundleNames.ADD_SINGLE_ALARM_TYPE.name(), addSingleAlarmType.name());
        intent.putExtra(BundleNames.DATA.name(), bundle);
        context.startActivity(intent);
    }

    public void navigateToUpdateAlarmActivity(SingleAlarmModel singleAlarmModel) {
        Intent intent = new Intent(context, AddAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleNames.TYPE.name(), AddAlarmPresenter.Type.UPDATE);
        bundle.putLong(BundleNames.ALARM_ID.name(), singleAlarmModel.getId());
        intent.putExtra(BundleNames.DATA.name(), bundle);
        context.startActivity(intent);
    }

    public void navigateToGroupAlarmActivity(GroupAlarmModel groupAlarmModel) {
        Intent intent = new Intent(context, GroupAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(BundleNames.GROUP_ALARM_ID.name(), groupAlarmModel.getId());
        intent.putExtra(BundleNames.DATA.name(), bundle);
        context.startActivity(intent);
    }

    public void navigateToAddAlarmActivityForGroupAlarm(long groupAlarmId, AddSingleAlarmType addSingleAlarmType) {
        Intent intent = new Intent(context, AddAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(BundleNames.TYPE.name(), AddAlarmPresenter.Type.CREATE);
        bundle.putSerializable(BundleNames.ADD_SINGLE_ALARM_TYPE.name(), addSingleAlarmType.name());
        bundle.putSerializable(BundleNames.GROUP_ALARM_ID.name(), groupAlarmId);
        intent.putExtra(BundleNames.DATA.name(), bundle);
        context.startActivity(intent);
    }
}
