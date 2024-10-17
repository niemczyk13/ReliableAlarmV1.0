package com.niemiec.reliablealarmv10.activity.main;

import com.niemiec.reliablealarmv10.database.alarm.entity.custom.SingleAlarmEntity;

import java.util.List;

public interface MainContractMVP {
    interface View {
        void showActivity(List<SingleAlarmEntity> singleAlarms);
    }

    interface Presenter {
        void initView();
    }
}
